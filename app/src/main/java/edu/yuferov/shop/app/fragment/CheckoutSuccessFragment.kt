package edu.yuferov.shop.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.presenter.CheckoutSuccessPresenter
import edu.yuferov.shop.app.presenter.ICheckoutSuccessView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CheckoutSuccessFragment : MvpAppCompatFragment(), ICheckoutSuccessView {
    @Inject
    lateinit var presenterProvider: Provider<CheckoutSuccessPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout_success, container, false)
    }

}
