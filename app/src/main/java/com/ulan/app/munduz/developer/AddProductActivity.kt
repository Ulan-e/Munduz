package com.ulan.app.munduz.developer

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ulan.app.munduz.R

class AddProductActivity : AppCompatActivity() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = database.getReference("goods")
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageReference: StorageReference = storage.reference

    lateinit var product: Product

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_layout)

        product = Product()
        writeToDatabase(product)
    }

    private fun writeToDatabase(product: Product) {
        val key = databaseReference.push().key
        if(key == null){
            Log.d("ulanbek", "Couldn't get push key for products")
            return
        }
        databaseReference.child(key).setValue(product)
    }



}
