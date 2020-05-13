package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.IHasPrice
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.formatPrice

class TotalSumFragment : Fragment() {

    private lateinit var price: TextView
    private lateinit var discountPrice: TextView
    private lateinit var totalPrice: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_total_sum, container, false)
        price = root.findViewById(R.id.fragment_total_sum_tv_price_value)
        discountPrice = root.findViewById(R.id.fragment_total_sum_tv_price_discount_value)
        totalPrice = root.findViewById(R.id.fragment_total_sum_tv_total_price_value)
        return root
    }

    fun setValues(model: IHasPrice) {
        bindPrice(price, discountPrice, model)
        totalPrice.formatPrice(model.totalPrice)
    }
}