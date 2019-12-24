package com.ulan.app.munduz.developer

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ulan.app.munduz.R
import java.io.IOException

import kotlinx.android.synthetic.main.add_product_layout.*
import java.util.*
import android.widget.ArrayAdapter as ArrayAdapter1

class AddProductActivity : AppCompatActivity() {

    companion object {
        const val PICK_IMAGE_REQUEST = 22
        const val TAG = "munduz.ru"
        const val GOODS = "goods"
    }

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRef: DatabaseReference = database.getReference(GOODS)
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    private var filePath: Uri? = null
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_layout)
        product = Product()

        setUpToolbar()
        setCategoryPref()

        // Choose image from phone's storage
        choose_product_image.setOnClickListener {
            chooseImage()
        }

        // Add to Firebase RealtimeDatabase
        add_button_database.setOnClickListener {
            addProduct()
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(product_toolbar)
        supportActionBar?.title = "Добавить продукт"
        product_toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        product_toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setCategoryPref(){
        val categoryList = resources.getStringArray(R.array.category)
        val categoryAdapter = ArrayAdapter1(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        product_category.adapter = categoryAdapter
        product_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                product.category = categoryList[p2]
            }
        }
    }

    private fun addProduct(){
        // Default value for Time and Visibility
        product.date = System.currentTimeMillis()
        product.isVisible = true

        // Name
        if (product_name.text.toString() == "") {
            fillAllFields()
            return
        }
        product.name = product_name.text.toString()

        // Description
        if (product_desc.text.toString() == "") {
            fillAllFields()
            return
        }
        product.desc = product_desc.text.toString()

        // Cost
        if (product_cost.text.toString() == "") {
            fillAllFields()
            return
        }
        product.cost = Integer.parseInt(product_cost.text.toString())

        // Image
        if (product_image.drawable == null && filePath == null) {
            Toast.makeText(
                this@AddProductActivity,
                resources.getString(R.string.product_image) + " не выбрана ",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        //Write to Database
        writeToDatabase(product)
        clearField()
    }

    private fun writeToDatabase(product: Product) {
        val key = databaseRef.push().key
        if (key == null) {
            Log.d(TAG, "Couldn't get push key for products")
            return
        }
        product.id = key
        databaseRef.child(key).setValue(product)
    }

    private fun fillAllFields() {
        Toast.makeText(this@AddProductActivity, resources.getString(R.string.edit_text_empty), Toast.LENGTH_SHORT)
            .show()
    }

    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setTitle(resources.getString(R.string.loading))
            progressDialog.show()

            val random = Random()
            val randomInt = random.nextInt(100) + 1
            val storageRef: StorageReference = storageRef.child("images/" + product.name + randomInt)
            var uploadTask: UploadTask = storageRef.putFile(filePath!!)
            uploadTask
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    storageRef.downloadUrl
                }
                .addOnSuccessListener {
                    progressDialog.dismiss()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@AddProductActivity,
                        resources.getString(R.string.loading_failed) + " " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        product.image = downloadUri.toString()
                        Toast.makeText(
                            this@AddProductActivity,
                            resources.getString(R.string.loading_success),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddProductActivity,
                            resources.getString(R.string.loading_not_completed) + " " + task.exception.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun clearField() {
        product_category.setSelection(0)
        product_name.text.clear()
        product_desc.text.clear()
        product_cost.text.clear()
        product_image.setImageResource(android.R.color.transparent)
    }

    private fun chooseImage() {
        intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(intent, resources.getString(R.string.select_image)),
            PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                product_image.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            uploadImage()
        }
    }

}