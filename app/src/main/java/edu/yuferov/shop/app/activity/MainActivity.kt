package edu.yuferov.shop.app.activity

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.presenter.IMainView
import edu.yuferov.shop.app.presenter.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : MvpAppCompatActivity(), IMainView {
    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)

        setupNavigationBars()
    }

    private fun setupNavigationBars() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, dest, _ ->
            presenter.onDestinationChanged(dest.id)
        }
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.catalog_fragment,
                R.id.cart_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun setCartItemCount(count: Int) {
        if (count == 0) {
            navView.removeBadge(R.id.cart_fragment)
        } else {
            navView.getOrCreateBadge(R.id.cart_fragment).number = count
        }
    }

    override fun setBottomBarVisible(visible: Boolean) {
        navView.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showShareProduct(productId: Int) {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.product_share_link, productId))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }
}
