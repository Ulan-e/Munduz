package ulanapp.munduz.ui.fragments.purchase

import android.content.Context
import dagger.Module
import dagger.Provides
import ulanapp.munduz.helpers.SendEmailAsync
import ulanapp.munduz.ui.activities.orders.OrdersScope

@Module
class PurchaseModule {

    @OrdersScope
    @Provides
    fun purchaseFragment(fragment: PurchaseFragment): PurchaseView {
        return fragment
    }

    @Provides
    fun sendEmail(context: Context): SendEmailAsync {
        return SendEmailAsync()
    }

    @OrdersScope
    @Provides
    fun purchasePresenter(view: PurchaseView): PurchasePresenter {
        return PurchasePresenterImpl(view)
    }

}