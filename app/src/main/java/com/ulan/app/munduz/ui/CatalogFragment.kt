package com.ulan.app.munduz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ulan.app.munduz.R

class CatalogFragment: Fragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.catalog_layout, container, false)
        return view
    }


    companion object{
        private val FRAG1 = "frag1"

        fun newInstance(): CatalogFragment{
            val args: Bundle = Bundle()
            val fragment = CatalogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
