package edu.yuferov.shop.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.Product
import edu.yuferov.shop.util.formatPrice
import edu.yuferov.shop.util.loadImage

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    var data = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onAddToCardClickedListener: OnAddToCardClickedListener? = null
    var onClickedListener: OnClickedListener? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.of(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onAddToCardClickedListener, onClickedListener)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.list_item_product_tv_title)
        private val thumbnail =
            itemView.findViewById<ImageView>(R.id.list_item_product_iv_thumbnail)
        private val price = itemView.findViewById<TextView>(R.id.list_item_product_tv_price)
        private val discountPrice =
            itemView.findViewById<TextView>(R.id.list_item_product_tv_discount_price)
        private val addToCartBtn = itemView.findViewById<Button>(R.id.list_item_product_btn_to_cart)

        fun bind(
            model: Product,
            onAddToCardClickedListener: OnAddToCardClickedListener?,
            onClickedListener: OnClickedListener?
        ) {
            title.text = model.title
            thumbnail.loadImage(model.thumbnail)

            formatPrice(price, discountPrice, model)

            itemView.setOnClickListener { onClickedListener?.onClick(model) }
            addToCartBtn.setOnClickListener { onAddToCardClickedListener?.onClick(model) }
        }

        companion object {
            @JvmStatic
            fun of(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.list_item_product, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class OnAddToCardClickedListener(private val listener: (model: Product) -> Unit) {
        fun onClick(model: Product) = listener(model)
    }

    class OnClickedListener(private val listener: (model: Product) -> Unit) {
        fun onClick(model: Product) = listener(model)
    }
}
