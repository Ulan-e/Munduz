package ulanapp.munduz.ui.fragments.purchase

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.purchase_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Message
import ulanapp.munduz.data.models.Order
import ulanapp.munduz.helpers.Constants.Companion.ARGS_ORDER
import ulanapp.munduz.helpers.SendEmailHelper
import ulanapp.munduz.ui.base.BaseDialogFragment
import javax.inject.Inject
import javax.inject.Provider

class PurchaseFragment : BaseDialogFragment(), PurchaseView {

    private lateinit var order: Order
    private val PHONE_NUMBER = "79771734250"
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

        order = arguments!!.getParcelable(ARGS_ORDER)
        presenter.putOrderToMessage(order)

        via_email.setOnClickListener {
            presenter.sendViaEmail()
        }

        via_whatsapp.setOnClickListener {
            presenter.sendViaWhatsApp()
        }

        cancel_purchase.setOnClickListener {
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
        showSnackBar(root_purchase_layout, message)
        Handler().postDelayed({
            dismiss()
        }, 2200)
    }

    override fun sendOrderToWhatsApp(message: Message) {
        try {
            val number: String = PHONE_NUMBER
            val waIntent = Intent(Intent.ACTION_VIEW)
            waIntent.data = Uri.parse(
                WHATSAPP + number +
                        WHATSAPPTEXT + message.body
            )
            startActivity(waIntent)

        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(activity!!, "WhatsApp не установлен", Toast.LENGTH_SHORT)
                .show()
            Toast.makeText(activity!!, "Выберите другой способ покупки", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        fun newInstance(order: Order): PurchaseFragment {
            val fragment = PurchaseFragment()
            val args = Bundle()
            args.putParcelable(ARGS_ORDER, order)
            fragment.arguments = args
            return fragment
        }
    }
}