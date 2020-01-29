package com.ulan.app.munduz.ui.more.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.helpers.convertLongToTime
import com.ulan.app.munduz.helpers.showEmptyFields
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.writetous_layout.*

class WtiteToUsFragment : BaseDialogFragment() {

    private lateinit var mSendEmailHelper: SendEmailHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.writetous_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        writer_button.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val email = "uulanerkinbaev@gmail.com"
        val subject = "Отзыв Munduz"
        val from = writer_name.text.toString()
        val message = writer_text.text.toString()
        val currentTime = System.currentTimeMillis()
        val time = currentTime.convertLongToTime(currentTime)
        if (from != "" || message != "") {
            val body = "*** От кого " + from + "\n" + "*** Сообщение" + message
            mSendEmailHelper.setMessage(email, subject, body, time)
            mSendEmailHelper.execute()
        }else{
            showEmptyFields(activity!!)
        }
    }
}