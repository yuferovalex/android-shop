package edu.yuferov.shop.app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.presenter.CheckoutFormPresenter
import edu.yuferov.shop.app.presenter.ICheckoutFormView
import moxy.MvpAppCompatFragment
import moxy.MvpFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CheckoutFormFragment : MvpAppCompatFragment(), ICheckoutFormView {
    @Inject
    lateinit var presenterProvider: Provider<CheckoutFormPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout_form, container, false)
    }
}
