package com.ulan.app.munduz.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.ulan.app.munduz.R
import kotlinx.android.synthetic.main.manager_activity_layout.view.*

class DeveloperLetter : DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.developer_letter_layout, container, false)
        return view
    }
}