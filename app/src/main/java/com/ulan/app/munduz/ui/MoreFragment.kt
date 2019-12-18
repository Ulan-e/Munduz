package com.ulan.app.munduz.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.ManagerActivity

class MoreFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.more_layout, container, false)
        var buttonManager: Button = view.findViewById(R.id.manager)
        buttonManager.setOnClickListener{
            val intent = Intent(activity, ManagerActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    companion object{

        fun newInstance(): MoreFragment{
            val args: Bundle = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()

    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }

}