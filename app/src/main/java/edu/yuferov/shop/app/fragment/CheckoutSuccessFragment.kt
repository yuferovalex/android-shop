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
import edu.yuferov.shop.databinding.FragmentCheckoutSuccessBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CheckoutSuccessFragment : MvpAppCompatFragment(), ICheckoutSuccessView {
    @Inject
    lateinit var presenterProvider: CheckoutSuccessPresenter.Fabric
    private val presenter by moxyPresenter {
        val args = CheckoutSuccessFragmentArgs.fromBundle(requireArguments())
        presenterProvider.create(args.orderNumber)
    }

    private lateinit var binding: FragmentCheckoutSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setOrderNumber(orderNumber: Int) {
        binding.fragmentCheckoutSuccessTvOrderNumber.text = String
            .format("%06d", orderNumber)
            .split(Regex("(?<=\\G.{3})"))
            .joinToString("-")
    }
}
