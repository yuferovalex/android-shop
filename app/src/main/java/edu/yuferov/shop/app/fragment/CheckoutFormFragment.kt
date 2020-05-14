package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.presenter.CheckoutFormPresenter
import edu.yuferov.shop.app.presenter.ICheckoutFormView
import edu.yuferov.shop.databinding.FragmentCheckoutFormBinding
import edu.yuferov.shop.domain.IHasPrice
import edu.yuferov.shop.domain.PaymentType
import edu.yuferov.shop.domain.UserInfo
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.formatPrice
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CheckoutFormFragment : BaseFragment(), ICheckoutFormView {

    @Inject
    lateinit var presenterProvider: Provider<CheckoutFormPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var binding: FragmentCheckoutFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutFormBinding.inflate(inflater, container, false)

        networkStatus =
            childFragmentManager.findFragmentById(R.id.fragment_checkout_form_status) as NetworkStatusFragment
        mainViewGroup = binding.fragmentCheckoutFormMainViewGroup

        binding.fragmentCheckoutFormTvLastName.addTextChangedListener(OnTextChanged {
            presenter.onLastNameChanged(it)
        })
        binding.fragmentCheckoutFormTvFirstName.addTextChangedListener(OnTextChanged {
            presenter.onFirstNameChanged(it)
        })
        binding.fragmentCheckoutFormTvMiddleName.addTextChangedListener(OnTextChanged {
            presenter.onMiddleNameChanged(it)
        })
        binding.fragmentCheckoutFormTvPhone.addTextChangedListener(OnTextChanged {
            presenter.onPhoneChanged(it)
        })
        binding.fragmentCheckoutFormTvPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.fragmentCheckoutFormBgPaymentType.setOnCheckedChangeListener { _, index ->
            presenter.onPaymentTypeChanged(when (index) {
                R.id.fragment_checkout_form_rbtn_cash -> PaymentType.CASH
                R.id.fragment_checkout_form_rbtn_card -> PaymentType.CARD
                else -> TODO("unknown payment type")
            })
        }
        binding.fragmentCheckoutFormBtnSubmit.setOnClickListener { presenter.onSubmitBtnClicked() }

        return binding.root
    }

    override fun setPrices(model: IHasPrice) {
        binding.fragmentCheckoutFormTvPriceValue.formatPrice(model.price)
        binding.fragmentCheckoutFormTvPriceDiscountValue.formatPrice(model.discountValue)
        binding.fragmentCheckoutFormTvTotalPriceValue.formatPrice(model.totalPrice)
    }

    override fun setLastNameError(msgId: Int) {
        binding.fragmentCheckoutFormLastNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setFirstNameError(msgId: Int) {
        binding.fragmentCheckoutFormFirstNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setMiddleNameError(msgId: Int) {
        binding.fragmentCheckoutFormMiddleNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setPhoneError(msgId: Int) {
        binding.fragmentCheckoutFormPhoneLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setUserInfo(userInfo: UserInfo) {
        binding.fragmentCheckoutFormTvLastName.setText(userInfo.lastName)
        binding.fragmentCheckoutFormTvFirstName.setText(userInfo.firstName)
        binding.fragmentCheckoutFormTvMiddleName.setText(userInfo.middleName)
        binding.fragmentCheckoutFormTvPhone.setText(userInfo.phone)

        when (userInfo.paymentType) {
            PaymentType.CARD ->
                binding.fragmentCheckoutFormBgPaymentType.check(R.id.fragment_checkout_form_rbtn_card)
            PaymentType.CASH ->
                binding.fragmentCheckoutFormBgPaymentType.check(R.id.fragment_checkout_form_rbtn_cash)
        }
    }

    override fun navigateToSuccess(orderNumber: Int) {
        findNavController().navigate(
            CheckoutFormFragmentDirections
                .actionCheckoutFormFragmentToCheckoutSuccessFragment(orderNumber)
        )
    }

    private class OnTextChanged(val listener: (text: String) -> Unit) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }
    }
}
