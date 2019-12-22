package com.ulan.app.munduz.developer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ulan.app.munduz.R

class HandleFragment : Fragment(), OnItemClickListener {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRef: DatabaseReference = database.getReference(AddProductActivity.GOODS)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ItemsAdapter
    private var goods: MutableList<Product?> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.handle_products_layout, container, false)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                goods.clear()
                for (item: DataSnapshot in p0.children) {
                    val product: Product? = item.getValue(Product::class.java)
                    goods.add(product)
                }
                linearLayoutManager = LinearLayoutManager(activity)
                val recyclerView = view.findViewById<RecyclerView>(R.id.handle_recycler_view)
                recyclerView.layoutManager = linearLayoutManager
                adapter = ItemsAdapter(activity!!.applicationContext, goods, this@HandleFragment)
                recyclerView.adapter = adapter
                recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        })
        return view
    }

    //Click item of recycler view
    override fun onClickItem(product: Product?) {
        var fragment = ItemsDetailsFragment.newInstance(product)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_frag, fragment)
            ?.addToBackStack(null)?.commit()
    }

}