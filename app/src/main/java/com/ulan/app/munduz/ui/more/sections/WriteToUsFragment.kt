package com.ulan.app.munduz.ui.more.sections

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.writetous_layout.*

class WriteToUsFragment : BaseDialogFragment() {

    private lateinit var mSendEmailHelper: SendEmailHelper

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.writetous_layout, container, false)
        mSendEmailHelper = SendEmailHelper(activity!!.applicationContext)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        send.setOnClickListener {
            sendMessage()
        }

        cancel.setOnClickListener{
            dismiss()
        }
    }

    private fun sendMessage() {
        val email = "uulanerkinbaev@gmail.com"
        val subject = "Отзыв Munduz"
        val from = writer_name.text.toString()
        val message = writer_text.text.toString()
        if (from != "" && message != "") {
            val body = "*** От кого " + from + "\n" + "*** Сообщение" + message
            mSendEmailHelper.setMessage(email, subject, body)
            mSendEmailHelper.execute()
            showSnack("Спасибо за ваш отзыв")
            Handler().postDelayed({
                dismiss()
            }, 2500)
        }else{
            var message = activity!!.resources.getString(R.string.empty_fields)
            showSnack(message)
        }
    }

    private fun showSnack(text: String){
        val snack = Snackbar.make(mView, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }
}