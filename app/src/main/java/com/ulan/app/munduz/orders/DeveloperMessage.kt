package com.ulan.app.munduz.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.SendEmailHelper

class DeveloperMessage : DialogFragment(){

    private lateinit var messageSubject: EditText
    private lateinit var messageBody: EditText
    private lateinit var sendMail: SendEmailHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_message_layout, container, false)

        messageSubject= view.findViewById<EditText>(R.id.message_subject)
        messageBody = view.findViewById<EditText>(R.id.message_body)
        val sendButton = view.findViewById<Button>(R.id.send)
        val cancelButton = view.findViewById<Button>(R.id.cancel)

        cancelButton.setOnClickListener{
            dialog!!.dismiss()
        }

        sendButton.setOnClickListener{
            sendMessage()
        }
        return view
    }

    private fun sendMessage() {
        val email = "uulanerkinbaev@gmail.com"
        val subject = messageSubject.text.toString()
        val body = messageBody.text.toString()
        if(body.isNotEmpty() && subject.isNotEmpty()) {
            sendMail = SendEmailHelper(activity!!.applicationContext, email, subject, body)
            sendMail.execute()
            dialog!!.dismiss()
        }else{
            Toast.makeText(activity!!.applicationContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
        }
    }


}