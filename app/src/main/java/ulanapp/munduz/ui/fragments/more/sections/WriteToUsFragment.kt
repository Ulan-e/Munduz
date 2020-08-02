package ulanapp.munduz.ui.fragments.more.sections

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.writetous_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Message
import ulanapp.munduz.helpers.SendEmailAsync
import ulanapp.munduz.ui.base.BaseDialogFragment

class WriteToUsFragment : BaseDialogFragment() {

    private lateinit var sendEmailHelper: SendEmailAsync

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.writetous_layout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendEmailHelper = SendEmailAsync()

        send.setOnClickListener {
            sendMessage()
        }

        cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun sendMessage() {
        val message = Message()
        message.email = "uulanerkinbaev@gmail.com"
        message.subject = "Отзыв Munduz"
        val from = writer_name.text.toString()
        val text = writer_text.text.toString()
        if (from != "" && text != "") {
            message.body = "*** От кого $from\n*** Сообщение$message"
            sendEmailHelper.setMessage(message)
            sendEmailHelper.execute()
            showSnackBar(rootView, "Спасибо за ваш отзыв")
            Handler().postDelayed({
                dismiss()
            }, 2500)
        } else {
            val message = activity!!.resources.getString(R.string.empty_fields)
            showSnackBar(rootView, message)
        }
    }

}