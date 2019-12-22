package com.ulan.app.munduz.developer

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import kotlinx.android.synthetic.main.add_product_layout.*
import java.io.IOException

class ItemsDetailsFragment : Fragment() {

    private lateinit var intent: Intent
    private lateinit var product: Product
    private lateinit var newProduct: Product
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRef: DatabaseReference = database.getReference(AddProductActivity.GOODS)
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference

    private var filePath: Uri? = null
    private var emptyField = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            product = arguments!!.getParcelable(ARG_PARAM)
        }

        newProduct = Product()
        emptyField = resources.getString(R.string.edit_text_empty)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_product_layout, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.product_toolbar)
        toolbar.visibility = View.GONE
        val product_category = view.findViewById<Spinner>(R.id.product_category)
        val product_name = view.findViewById<EditText>(R.id.product_name)
        val product_desc = view.findViewById<EditText>(R.id.product_desc)
        val product_cost = view.findViewById<EditText>(R.id.product_cost)
        val product_image = view.findViewById<ImageView>(R.id.product_image)
        val choose_image = view.findViewById<ImageButton>(R.id.choose_product_image)

        // Image
        choose_image.setOnClickListener{
            chooseImage()
        }

        // Category
        val categoryList = resources.getStringArray(R.array.category)
        val categoryAdapter =
            ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        product_category.adapter = categoryAdapter
        product_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                newProduct.category = categoryList[p2]
            }
        }

        //Visibility
        val switch = view.findViewById<Switch>(R.id.visibility)
        switch.visibility = View.VISIBLE


        // Visible needed views
        val add_button = view.findViewById<Button>(R.id.add_button_database)
        val update_button = view.findViewById<Button>(R.id.update_button_database)
        val delete_button = view.findViewById<Button>(R.id.delete_button_database)
        delete_button.visibility = View.VISIBLE
        update_button.visibility = View.VISIBLE
        add_button.visibility = View.GONE


        // Set values for product views
        if (product != null) {
            for((i,item) in categoryList.withIndex()) {
                if (product.category == item){
                    product_category.setSelection(i)
                    break
                }
            }
            product_name.setText(product.name)
            product_desc.setText(product.desc)
            product_cost.setText(product.cost.toString())
            switch.setChecked(product.isVisible)
            Picasso.get().load(product.image).into(product_image)
        }

        update_button.setOnClickListener {
            // Date and Time
            newProduct.date = System.currentTimeMillis()

            // Name
            if (product_name.text.toString() == "") {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.product_name) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            newProduct.name = product_name.text.toString()

            // Description
            if (product_desc.text.toString() == "") {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.product_desc) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            newProduct.desc = product_desc.text.toString()

            // Cost
            if (product_cost.text.toString() == "") {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.product_cost) + " " + emptyField,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            newProduct.cost = Integer.parseInt(product_cost.text.toString())


            if (product_image.drawable == null && filePath == null) {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.product_image) + " не выбрана ",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //Visibility
            newProduct.isVisible = switch.isChecked
            newProduct.id= product.id
            uploadImage()
        }

        delete_button.setOnClickListener{
            val key = product.id
            if (key == null) {
                Log.d(AddProductActivity.TAG, "Couldn't get push key for products")
            }
            product.id = key
            databaseRef.child(key).removeValue()
        }

        return view
    }

    private fun uploadImage() {
        if(filePath != null){
            val progress: ProgressDialog= ProgressDialog(activity)
            progress.setTitle("Загрузка")
            progress.show()

            val storageRef: StorageReference = storageRef.child("images/" + product.name + 11)
            var uploadTask: UploadTask = storageRef.putFile(filePath!!)

            uploadTask.continueWithTask{ task ->
                if(!task.isSuccessful){
                    task.exception?.let {
                        throw it
                    }
                }
                storageRef.downloadUrl
            }
                .addOnSuccessListener {
                    progress.dismiss()
                }
                .addOnFailureListener{
                    progress.dismiss()
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.loading_failed) + " " + it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        val downloadImageUri = task.result
                        newProduct.image = downloadImageUri.toString()
                        //Write to Database
                        writeToDatabase(newProduct)
                        clearField()
                    }
                }
        }
    }

    private fun writeToDatabase(product: Product) {
        val key = product.id
        databaseRef.child(key).setValue(newProduct)
        Toast.makeText(activity,
            "Updated",
            Toast.LENGTH_SHORT).show()
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
            AddProductActivity.PICK_IMAGE_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddProductActivity.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            filePath = data.data
            try {
                var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)
                product_image.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {

        private val ARG_PARAM = "details_bundle"

        fun newInstance(product: Product?): ItemsDetailsFragment {
            val fragment = ItemsDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM, product)
            fragment.arguments = args
            return fragment
        }

    }
}