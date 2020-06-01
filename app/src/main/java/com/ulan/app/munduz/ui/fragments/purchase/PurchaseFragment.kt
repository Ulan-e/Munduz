package com.ulan.app.munduz.ui.fragments.purchase

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Order
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.SendEmailHelper
import com.ulan.app.munduz.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.purchase_layout.*
import javax.inject.Inject
import javax.inject.Provider

class PurchaseFragment : BaseDialogFragment(), PurchaseView {

    private lateinit var order: Order
    private val PHONE = "79771734250"
    private val WHATSAPP = "http://api.whatsapp.com/send?phone="
    private val WHATSAPPTEXT = "&text="

    @Inject
    lateinit var presenter: PurchasePresenter

    @Inject
    lateinit var sendEmailHelper: Provider<SendEmailHelper>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.purchase_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailHelper = sendEmailHelper.get()
        presenter.setEmailHelper(emailHelper)

        order = arguments!!.getParcelable(Constants.ARGS_ORDER)
        presenter.putOrderToMessage(order)

        via_email.setOnClickListener {
            presenter.sendViaEmail()
        }

        via_whatsapp.setOnClickListener {
            presenter.sendViaWhatsApp()
        }

        cancel_purchase.setOnClickListener{
            dismiss()
        }
    }

    override fun closeDialog() {
        dismiss()
    }

    override fun sendViaWhatsAppClicked() {
        presenter.sendViaWhatsApp()
    }

    override fun sendViaEmailClicked() {
        presenter.sendViaEmail()
    }

    override fun showMessage(message: String) {
        showSnackBar(message)
        Handler().postDelayed({
            dismiss()
        }, 2200)
    }

    override fun sendOrderToWhatsApp(message: com.ulan.app.munduz.data.models.Message) {
        val pm = activity!!.getPackageManager()
        try {
            val number: String = PHONE
            val waIntent = Intent(Intent.ACTION_VIEW)
            waIntent.setData(
                Uri.parse(
                    WHATSAPP + number +
                            WHATSAPPTEXT + message.body
                            + "Нажмите отправить --->>>>>"
                )
            )
            startActivity(waIntent)

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(activity!!, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(root_purchase_layout, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }



    companion object {
        fun newInstance(order: Order): PurchaseFragment {
            val fragment = PurchaseFragment()
            val args = Bundle()
            args.putParcelable(Constants.ARGS_ORDER, order)
            fragment.arguments = args
            return fragment
        }
    }
}