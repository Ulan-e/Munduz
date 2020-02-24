package com.ulan.app.munduz.data.repository

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.model.Order
import com.ulan.app.munduz.data.model.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.ORDERS_DATA
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCTS_DATA
import com.ulan.app.munduz.helpers.Constants.Companion.TAG
import com.ulan.app.munduz.listeners.*

class RepositoryImpl : Repository {


    private val context: Context
    private val ref: DatabaseReference


    constructor(context: Context) {
        this.context = context
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        ref = database.reference
    }

    override fun insertOrder(order: Order) {
        val key = ref!!.child(ORDERS_DATA).push().key
        if(key == null){
            Log.d(TAG, "Couldn't get push key for products")
            return
        }
        order.id = key
        ref!!.child(ORDERS_DATA).child(key).setValue(order)
    }

    override fun loadProducts(callback: ProductListCallback){
        val products = mutableListOf<Product>()
        val productsRef = ref!!.child(PRODUCTS_DATA)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadSearchedProducts(callback: ProductsCallback){
        val products = ArrayList<Product>()
        val productsRef = ref!!.child(PRODUCTS_DATA)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadNewProducts(callback: ProductListCallback) { val products = mutableListOf<Product>()
        val queryRef = ref!!.child(PRODUCTS_DATA).orderByKey().limitToLast(4)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadFilterProducts(category: String, callback: ProductListCallback) {
        val products = mutableListOf<Product>()
        val queryRef = ref!!.child(PRODUCTS_DATA).orderByChild("category").equalTo(category)
        queryRef.keepSynced(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadSliderPhotos(callback: SliderImagesCallback) {
        val sliderImages = ArrayList<SliderImage>()
        val productsRef = ref!!.child(Constants.SLIDER_DATA)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val image = item.getValue(SliderImage::class.java)
                    sliderImages.add(image!!)
                }
                callback.onCallback(sliderImages)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadLikedProduct(key: String, callback: ProductCallback){
       var product = Product()
        val queryRef = ref!!.child(PRODUCTS_DATA).orderByChild("id").equalTo(key)
        queryRef.keepSynced(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item : DataSnapshot in p0.children){
                    val likedProduct: Product? = item.getValue(Product::class.java)
                    if (likedProduct != null) {
                        product = likedProduct
                    }
                }
                callback.onCallback(product)
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadCatalogs(): MutableList<String>{
        val catalogs = context.applicationContext.resources.getStringArray(R.array.category)
        return catalogs.toMutableList()
    }
}