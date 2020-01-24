package com.ulan.app.munduz.data.repository

import android.content.Context
import com.google.firebase.database.*
import com.ulan.app.munduz.R
import com.ulan.app.munduz.listeners.ProductListCallback
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCTS_DATA

class RepositoryImpl : Repository {

    private val database: FirebaseDatabase
    private val ref: DatabaseReference
    private val context: Context

    constructor(context: Context) {
        this.context = context
        database = FirebaseDatabase.getInstance()
        ref = database.reference
    }

    override fun loadProducts(callback: ProductListCallback){
        val products = mutableListOf<Product>()
        ref.child(PRODUCTS_DATA).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO()
            }
        })
    }

    override fun loadNewProducts(callback: ProductListCallback) {
        val products = mutableListOf<Product>()
        val query = ref.child(PRODUCTS_DATA).orderByKey().limitToLast(4)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO()
            }
        })
    }

    override fun loadFilterProducts(category: String, callback: ProductListCallback) {
        val products = mutableListOf<Product>()
        val query = ref.child(PRODUCTS_DATA).orderByChild("category").equalTo(category)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO()
            }
        })
    }

    override fun loadCatalogs(): MutableList<String>{
        val catalogs = context.applicationContext.resources.getStringArray(R.array.category)
        return catalogs.toMutableList()
    }
}