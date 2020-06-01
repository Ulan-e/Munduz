package com.ulan.app.munduz.data.repository

import android.util.Log
import com.google.firebase.database.*
import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.FIREBASE_ERROR_TITLE
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCTS_TABLE
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_CATEGORY
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ID
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_RECOMMEND
import com.ulan.app.munduz.helpers.Constants.Companion.TAG
import com.ulan.app.munduz.interfaces.ProductCallback
import com.ulan.app.munduz.interfaces.ProductsCallback
import com.ulan.app.munduz.interfaces.SliderImagesCallback

class FirebaseRepositoryImpl: FirebaseRepository {

    private val database: DatabaseReference

    init {
        val firebase: FirebaseDatabase = FirebaseDatabase.getInstance()
        this.database = firebase.reference
    }

    override fun loadProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = database.child(PRODUCTS_TABLE)
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
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductsByRecommendation(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_RECOMMEND).equalTo(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductsByCategory(category: String, callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_CATEGORY).equalTo(category)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    products.add(product!!)
                }
                callback.onCallback(products)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadProductByKey(key: String, callback: ProductCallback) {
        var product = Product()
        val queryRef = database.child(PRODUCTS_TABLE).orderByChild(PRODUCT_ID).equalTo(key)
        queryRef.keepSynced(true)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val likedProduct: Product? = item.getValue(Product::class.java)
                    if (likedProduct != null) {
                        product = likedProduct
                    }
                }
                callback.onCallback(product)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadSliderPhotos(callback: SliderImagesCallback) {
        val sliderImages = ArrayList<SliderImage>()
        val productsRef = database.child(Constants.SLIDER_TABLE)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val image = item.getValue(SliderImage::class.java)
                    sliderImages.add(image!!)
                }
                callback.onCallback(sliderImages.toList())
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

}