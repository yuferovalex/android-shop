package edu.yuferov.shop.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.CartItem
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.loadImage
import edu.yuferov.shop.util.formatPrice

class CartItemAdapter(val cart: Cart) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    var clickListener: OnClickListener? = null
    var removeClickListener: OnRemoveClickListener? = null
    var quantityClickListener: OnQuantityClickListener? = null

    override fun getItemCount() = cart.items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.of(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cart.items[position]
        holder.bind(item, clickListener, removeClickListener, quantityClickListener)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val price: TextView = itemView.findViewById(R.id.list_item_cart_item_tv_price)
        private val discountPrice: TextView = itemView.findViewById(R.id.list_item_cart_item_tv_discount_price)
        private val thumbnail: ImageView = itemView.findViewById(R.id.list_item_cart_item_iv_thumbnail)
        private val title: TextView = itemView.findViewById(R.id.list_item_cart_item_tv_title)
        private val priceForOne: TextView = itemView.findViewById(R.id.list_item_cart_item_tv_price_for_one)
        private val removeBtn: Button = itemView.findViewById(R.id.list_item_cart_item_btn_remove_item)
        private val quantityBtn: Button = itemView.findViewById(R.id.list_item_cart_item_btn_count)

        fun bind(
            model: CartItem,
            clickHandler: OnClickListener?,
            removeClickListener: OnRemoveClickListener?,
            quantityClickHandler: OnQuantityClickListener?
        ) {
            thumbnail.loadImage(model.thumbnail)
            bindPrice(price, discountPrice, model)
            title.text = model.title
            priceForOne.formatPrice(model.priceForOne)
            quantityBtn.text = itemView.context.getString(R.string.template_quantity, model.quantity)
            removeBtn.setOnClickListener { removeClickListener?.onClick(model) }
            quantityBtn.setOnClickListener { quantityClickHandler?.onClick(model) }
            itemView.setOnClickListener { clickHandler?.onClick(model) }
        }

        companion object {
            @JvmStatic
            fun of(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.list_item_cart_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class OnClickListener(private val impl: (cartItem: CartItem) -> Unit) {
        fun onClick(cartItem: CartItem) = impl(cartItem)
    }

    class OnRemoveClickListener(private val impl: (cartItem: CartItem) -> Unit) {
        fun onClick(cartItem: CartItem) = impl(cartItem)
    }

    class OnQuantityClickListener(private val impl: (cartItem: CartItem) -> Unit) {
        fun onClick(cartItem: CartItem) = impl(cartItem)
    }
}
