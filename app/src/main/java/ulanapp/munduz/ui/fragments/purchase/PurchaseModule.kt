package ulanapp.munduz.ui.fragments.purchase

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.helpers.SendEmailHelper
import ulanapp.munduz.ui.activities.orders.OrdersScope

@Module
class PurchaseModule {

    @OrdersScope
    @Provides
    fun purchaseFragment(fragment: PurchaseFragment): PurchaseView {
        return fragment
    }

    @Provides
    fun sendEmail(context: Context): SendEmailHelper {
        return SendEmailHelper(context)
    }

    @OrdersScope
    @Provides
    fun purchasePresenter(view: PurchaseView): PurchasePresenter {
        return PurchasePresenterImpl(view)
    }

}