package com.ulan.app.munduz.developer

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ulan.app.munduz.R

class ListFragment : Fragment(), OnItemClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRef: DatabaseReference = database.getReference(AddProductActivity.GOODS)
    private var goods: ArrayList<Product?> = ArrayList()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: ProductAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        setUpToolbar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(R.layout.handle_products_layout, container, false)

        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView = view.findViewById<RecyclerView>(R.id.handle_recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity!!.applicationContext, DividerItemDecoration.VERTICAL))

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, "Error to read data from database", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                goods.clear()
                if (goods.size >= 0 ) {
                    for (item: DataSnapshot in p0.children) {
                        val product: Product? = item.getValue(Product::class.java)
                        goods.add(product)
                    }
                    adapter = ProductAdapter(activity!!.applicationContext, goods, this@ListFragment)
                    recyclerView.adapter = adapter
                }
            }
        })
        return view
    }

    private fun setUpToolbar() {
        (activity as HandleActivity).supportActionBar?.title = "List"
        toolbar = (activity as HandleActivity).findViewById<Toolbar>(R.id.handle_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            (activity as HandleActivity).finish()
        }
    }

    override fun onClickItem(product: Product?) {
        var fragment = ProductDetailsFragment.newInstance(product)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container_frag, fragment)
            ?.addToBackStack(null)?.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.manager_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.search)
        searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Serach"

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText == null || newText.trim().isEmpty()){
            resetSearch()
            return false
        }

        var filtereadList = ArrayList<Product?>(goods)
        for(product: Product? in goods){
            if(!product!!.name.toLowerCase().contains(newText.toLowerCase())){
                filtereadList.remove(product)
            }
        }

        adapter = ProductAdapter(activity!!.applicationContext, filtereadList, this@ListFragment)
        recyclerView.adapter = adapter
        return false
    }

    private fun resetSearch() {
        adapter = ProductAdapter(activity!!.applicationContext, goods, this@ListFragment)
        recyclerView.adapter = adapter
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        return true
    }

}