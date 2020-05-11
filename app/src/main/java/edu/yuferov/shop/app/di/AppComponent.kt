package edu.yuferov.shop.app.di

import dagger.Component
import edu.yuferov.shop.app.fragment.*
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        // fun context(context: Context): Builder
        fun build(): AppComponent
    }

    // fun inject(mainActivity: MainActivity)
    fun inject(catalogFragment: CatalogFragment)
    fun inject(catalogFragment: ProductListFragment)
    fun inject(productDetailFragment: ProductDetailFragment)
    fun inject(checkoutFormFragment: CheckoutFormFragment)
    fun inject(cartFragment: CartFragment)
    fun inject(checkoutSuccessFragment: CheckoutSuccessFragment)
}