package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.CartItemAdapter
import edu.yuferov.shop.app.presenter.CartPresenter
import edu.yuferov.shop.app.presenter.ICartView
import edu.yuferov.shop.domain.Cart
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CartFragment : MvpAppCompatFragment(), ICartView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: Provider<CartPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override lateinit var status: NetworkStatusFragment
    private lateinit var totalSumFragment: TotalSumFragment
    private lateinit var itemList: RecyclerView
    private lateinit var checkoutBtn: Button
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cart, container, false)
        status =
            childFragmentManager.findFragmentById(R.id.fragment_cart_status) as NetworkStatusFragment

        totalSumFragment =
            childFragmentManager.findFragmentById(R.id.fragment_cart_total_sum) as TotalSumFragment

        itemList = root.findViewById(R.id.fragment_cart_rv_item_list)
        itemList.layoutManager = LinearLayoutManager(context)

        checkoutBtn = root.findViewById(R.id.fragment_cart_btn_checkout)
        scrollView = root.findViewById(R.id.fragment_cart_sv_scroll)

        return root
    }

    override fun bind(cart: Cart) {
        val itemListAdapter = CartItemAdapter(cart)
        itemList.adapter = itemListAdapter
        totalSumFragment.setValues(cart)
        checkoutBtn.text = getString(R.string.cart_do_checkout_text, cart.totalPrice.value)

        status.status = NetworkStatusFragment.Status.Loaded
        scrollView.visibility = View.VISIBLE
        checkoutBtn.visibility = View.VISIBLE
    }
}
