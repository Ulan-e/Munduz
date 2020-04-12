package com.ulan.app.munduz.data.firebase

import android.util.Log
import com.google.firebase.database.*
import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.FIREBASE_ERROR_TITLE
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCTS_TABLE
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_CATEGORY
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ID
import com.ulan.app.munduz.helpers.Constants.Companion.TAG
import com.ulan.app.munduz.listeners.ProductCallback
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback

class FirebaseRepositoryImpl: FirebaseRepository {

    private val mRef: DatabaseReference

    init {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        mRef = database.reference
    }

    override fun loadProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = mRef.child(PRODUCTS_TABLE)
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

    override fun loadSearchedProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = mRef.child(PRODUCTS_TABLE)
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

    override fun loadRecommendedProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = mRef.child(PRODUCTS_TABLE).orderByKey().limitToLast(8)
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

    override fun loadFilteredProducts(category: String, callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = mRef.child(PRODUCTS_TABLE).orderByChild(PRODUCT_CATEGORY).equalTo(category)
        queryRef.keepSynced(true)
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

    override fun loadSliderPhotos(callback: SliderImagesCallback) {
        val sliderImages = ArrayList<SliderImage>()
        val productsRef = mRef.child(Constants.SLIDER_TABLE)
        productsRef.keepSynced(true)
        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item: DataSnapshot in p0.children) {
                    val image = item.getValue(SliderImage::class.java)
                    sliderImages.add(image!!)
                }
                callback.onCallback(sliderImages)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, FIREBASE_ERROR_TITLE + p0.message)
            }
        })
    }

    override fun loadLikedProduct(key: String, callback: ProductCallback) {
        var product = Product()
        val queryRef = mRef.child(PRODUCTS_TABLE).orderByChild(PRODUCT_ID).equalTo(key)
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

}