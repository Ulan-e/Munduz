package com.ulan.app.munduz.data.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCTS_DATA
import com.ulan.app.munduz.helpers.Constants.Companion.TAG
import com.ulan.app.munduz.listeners.ProductCallback
import com.ulan.app.munduz.listeners.ProductsCallback
import com.ulan.app.munduz.listeners.SliderImagesCallback

class FirebaseRepositoryImpl : FirebaseRepository {

    private val mContext: Context
    private val mRef: DatabaseReference

    constructor(context: Context) {
        this.mContext = context
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        mRef = database.reference
    }

    override fun loadProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = mRef.child(PRODUCTS_DATA)
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

    override fun loadSearchedProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val productsRef = mRef.child(PRODUCTS_DATA)
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

    override fun loadNewProducts(callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = mRef.child(PRODUCTS_DATA).orderByKey().limitToLast(8)
        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
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

    override fun loadFilterProducts(category: String, callback: ProductsCallback) {
        val products = mutableListOf<Product>()
        val queryRef = mRef.child(PRODUCTS_DATA).orderByChild("category").equalTo(category)
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
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun updateProduct(product: Product) {
        val key = product.id
        mRef.child(PRODUCTS_DATA).child(key).setValue(product)
    }

    override fun loadSliderPhotos(callback: SliderImagesCallback) {
        val sliderImages = ArrayList<SliderImage>()
        val productsRef = mRef.child(Constants.SLIDER_DATA)
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
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadLikedProduct(key: String, callback: ProductCallback) {
        var product = Product()
        val queryRef = mRef.child(PRODUCTS_DATA).orderByChild("id").equalTo(key)
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
                Log.d(TAG, "Firebase Database Error " + p0.message)
            }
        })
    }

    override fun loadCatalogs(): MutableList<String> {
        val catalogs = mContext.applicationContext.resources.getStringArray(R.array.category)
        return catalogs.toMutableList()
    }
}