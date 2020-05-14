package edu.yuferov.shop.app.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.CartItemAdapter
import edu.yuferov.shop.app.presenter.CartPresenter
import edu.yuferov.shop.app.presenter.ICartView
import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.CartItem
import edu.yuferov.shop.domain.Price
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CartFragment : MvpAppCompatFragment(), ICartView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: Provider<CartPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override lateinit var status: NetworkStatusFragment
    private var quantityDialog: AlertDialog? = null
    private lateinit var itemList: RecyclerView
    private lateinit var checkoutBtn: Button
    private lateinit var itemListAdapter: CartItemAdapter

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

        itemListAdapter = CartItemAdapter()
        itemListAdapter.removeClickListener = CartItemAdapter.OnRemoveClickListener {
            presenter.onRemoveItemClicked(it)
        }
        itemListAdapter.clickListener = CartItemAdapter.OnClickListener {
            presenter.onItemClicked(it)
        }
        itemListAdapter.quantityClickListener = CartItemAdapter.OnQuantityClickListener {
            presenter.onQuantityClick(it)
        }

        itemList = root.findViewById(R.id.fragment_cart_rv_item_list)
        itemList.adapter = itemListAdapter
        itemList.layoutManager = LinearLayoutManager(context)

        checkoutBtn = root.findViewById(R.id.fragment_cart_btn_checkout)
        checkoutBtn.setOnClickListener { presenter.onCheckoutBtnClicked() }

        return root
    }

    override fun setItems(cart: Cart) {
        itemListAdapter.data = cart.items

        status.status = NetworkStatusFragment.Status.Loaded
        itemList.visibility = View.VISIBLE
        checkoutBtn.visibility = View.VISIBLE
    }

    override fun updateCheckoutBtnState(active: Boolean, price: Price) {
        checkoutBtn.isEnabled = active
        checkoutBtn.text = getString(R.string.cart_do_checkout_text, price.value)
    }

    override fun insertItem(index: Int) {
        itemListAdapter.notifyItemInserted(index)
    }

    override fun updateItem(index: Int) {
        itemListAdapter.notifyItemChanged(index)
    }

    override fun removeItem(index: Int) {
        itemListAdapter.notifyItemRemoved(index)
    }

    override fun showItemRemovedMessage(index: Int, cartItem: CartItem) {
        val message = getString(R.string.fragment_cart_fragment_item_removed_msg, cartItem.title)
        val snack = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snack.setAction(android.R.string.cancel) {
            presenter.onRestoreItemClicked(index, cartItem)
        }
        snack.show()
    }

    override fun navigateToCheckout() {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToCheckoutFormFragment()
        )
    }

    override fun navigateToProduct(id: Int) {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToProductDetailsFragment(id)
        )
    }

    override fun showQuantityDialog(cartItem: CartItem, initialQuantity: Int) {
        val numberPicker = NumberPicker(context)
        numberPicker.minValue = 1
        numberPicker.maxValue = 100
        numberPicker.value = cartItem.quantity
        numberPicker.setOnValueChangedListener { _, _, value ->
            presenter.onQuantityChanged(cartItem, value)
        }

        quantityDialog = AlertDialog.Builder(context)
            .setTitle("Количество ${cartItem.title}")
            .setMessage("Выберите количество ${cartItem.title} для покупки")
            .setView(numberPicker)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                presenter.onQuantityDialogOkClicked(cartItem)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                presenter.onQuantityDialogCancelClicked(cartItem, initialQuantity)
            }
            .setOnDismissListener {
                presenter.onQuantityDialogCancelClicked(
                    cartItem,
                    initialQuantity
                )
            }
            .show()
    }

    override fun hideQuantityDialog() {
        quantityDialog?.setOnDismissListener(null)
        quantityDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        hideQuantityDialog()
    }
}
