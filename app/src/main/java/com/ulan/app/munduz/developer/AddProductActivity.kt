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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ulan.app.munduz.R
import java.io.IOException

import kotlinx.android.synthetic.main.add_product_layout.*

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
        val emptyField = resources.getString(R.string.edit_text_empty)

        setSupportActionBar(product_toolbar)
        val actionBar = supportActionBar
        actionBar!!.setTitle("Add Product")

        // Category
        val categoryList = resources.getStringArray(R.array.category)
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        product_category.adapter = categoryAdapter
        product_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                product.category = categoryList[p2]
            }
        }

        // Choose image from phone's storage
        choose_product_image.setOnClickListener {
            chooseImage()
        }

        // Add to Firebase RealtimeDatabase
        add_button_database.setOnClickListener {
            // Date and Time
            product.date = System.currentTimeMillis()

            //Visibility
            product.isVisible = true

            // Name
            if (product_name.text.toString() == "") {
                Toast.makeText(
                    this@AddProductActivity,
                    resources.getString(R.string.product_name) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            product.name = product_name.text.toString()

            // Description
            if (product_desc.text.toString() == "") {
                Toast.makeText(
                    this@AddProductActivity,
                    resources.getString(R.string.product_desc) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            product.desc = product_desc.text.toString()

            // Cost
            if (product_cost.text.toString() == "") {
                Toast.makeText(
                    this@AddProductActivity,
                    resources.getString(R.string.product_cost) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            product.cost = Integer.parseInt(product_cost.text.toString())

            // Image
            if (product_image.drawable == null && filePath == null) {
                Toast.makeText(
                    this@AddProductActivity,
                    resources.getString(R.string.product_image) + " не выбрана ",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            uploadImage()
        }
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

    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog: ProgressDialog = ProgressDialog(this)
            progressDialog.setTitle(resources.getString(R.string.loading))
            progressDialog.show()

            val storageRef: StorageReference = storageRef.child("images/" + product.name + 22)
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
                    Toast.makeText(
                        this@AddProductActivity,
                        resources.getString(R.string.loading_success),
                        Toast.LENGTH_LONG
                    ).show()
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
                        //Write to Database
                        writeToDatabase(product)
                        clearField()
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

    private fun clearField(){
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
            Companion.PICK_IMAGE_REQUEST
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
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}
