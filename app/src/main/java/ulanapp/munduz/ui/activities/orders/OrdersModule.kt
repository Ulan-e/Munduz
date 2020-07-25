package ulanapp.munduz.ui.activities.orders

import dagger.Module
import dagger.Provides
import ulanapp.munduz.dagger.RoomModule
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.data.room.repository.PurchasesRepository

@Module(includes = [RoomModule::class])
class OrdersModule {

    @OrdersScope
    @Provides
    fun ordersActivity(activity: OrdersActivity): OrdersView {
        return activity
    }

    @OrdersScope
    @Provides
    fun orderPresenter(repository: PurchasesRepository): OrdersPresenter {
        return OrdersPresenterImpl(repository)
    }

}