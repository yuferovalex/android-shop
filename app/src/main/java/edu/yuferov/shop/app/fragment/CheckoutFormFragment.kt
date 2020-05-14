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
import edu.yuferov.shop.domain.IHasPrice
import edu.yuferov.shop.domain.PaymentType
import edu.yuferov.shop.domain.UserInfo
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.formatPrice
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CheckoutFormFragment : MvpAppCompatFragment(), ICheckoutFormView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: Provider<CheckoutFormPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override lateinit var status: NetworkStatusFragment
    private lateinit var scrollView: ScrollView
    private lateinit var lastName: TextView
    private lateinit var lastNameLayout: TextInputLayout
    private lateinit var firstName: TextView
    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var middleName: TextView
    private lateinit var middleNameLayout: TextInputLayout
    private lateinit var phone: TextView
    private lateinit var phoneLayout: TextInputLayout
    private lateinit var paymentType: RadioGroup
    private lateinit var priceValue: TextView
    private lateinit var priceDiscountValue: TextView
    private lateinit var totalPriceValue: TextView
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout_form, container, false)

        status = childFragmentManager.findFragmentById(R.id.fragment_checkout_form_status) as NetworkStatusFragment
        scrollView = view.findViewById(R.id.fragment_checkout_form_sv_scroll)
        lastName = view.findViewById(R.id.fragment_checkout_form_tv_last_name)
        lastNameLayout = view.findViewById(R.id.fragment_checkout_form_last_name_layout)
        firstName = view.findViewById(R.id.fragment_checkout_form_tv_first_name)
        firstNameLayout = view.findViewById(R.id.fragment_checkout_form_first_name_layout)
        middleName = view.findViewById(R.id.fragment_checkout_form_tv_middle_name)
        middleNameLayout = view.findViewById(R.id.fragment_checkout_form_middle_name_layout)
        phone = view.findViewById(R.id.fragment_checkout_form_tv_phone)
        phoneLayout = view.findViewById(R.id.fragment_checkout_form_phone_layout)

        paymentType = view.findViewById(R.id.fragment_checkout_form_bg_payment_type)
        priceValue = view.findViewById(R.id.fragment_checkout_form_tv_price_value)
        priceDiscountValue = view.findViewById(R.id.fragment_checkout_form_tv_price_discount_value)
        totalPriceValue = view.findViewById(R.id.fragment_checkout_form_tv_total_price_value)
        submitBtn = view.findViewById(R.id.fragment_checkout_form_btn_submit)

        lastName.addTextChangedListener(OnTextChanged {
            presenter.onLastNameChanged(it)
        })
        firstName.addTextChangedListener(OnTextChanged {
            presenter.onFirstNameChanged(it)
        })
        middleName.addTextChangedListener(OnTextChanged {
            presenter.onMiddleNameChanged(it)
        })
        phone.addTextChangedListener(OnTextChanged {
            presenter.onPhoneChanged(it)
        })
        phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        paymentType.setOnCheckedChangeListener { _, index ->
            presenter.onPaymentTypeChanged(when (index) {
                R.id.fragment_checkout_form_rbtn_cash -> PaymentType.CASH
                R.id.fragment_checkout_form_rbtn_card -> PaymentType.CARD
                else -> TODO("unknown payment type")
            })
        }
        submitBtn.setOnClickListener { presenter.onSubmitBtnClicked() }

        return view
    }

    override fun hideNetworkStatus() {
        status.status = NetworkStatusFragment.Status.Loaded
        scrollView.visibility = View.VISIBLE
    }

    override fun setPrices(model: IHasPrice) {
        priceValue.formatPrice(model.price)
        priceDiscountValue.formatPrice(model.discountValue)
        totalPriceValue.formatPrice(model.totalPrice)
    }

    override fun setLastNameError(msgId: Int) {
        lastNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setFirstNameError(msgId: Int) {
        firstNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setMiddleNameError(msgId: Int) {
        middleNameLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setPhoneError(msgId: Int) {
        phoneLayout.error = if (msgId == 0) null else getString(msgId)
    }

    override fun setUserInfo(userInfo: UserInfo) {
        lastName.text = userInfo.lastName
        firstName.text = userInfo.firstName
        middleName.text = userInfo.middleName
        phone.text = userInfo.phone

        when (userInfo.paymentType) {
            PaymentType.CARD -> paymentType.check(R.id.fragment_checkout_form_rbtn_card)
            PaymentType.CASH -> paymentType.check(R.id.fragment_checkout_form_rbtn_cash)
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

    private class OnFocusLostListener(val onFocusLost: (v: View?) -> Unit) :
        View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (!hasFocus) {
                onFocusLost(v)
            }
        }
    }
}
