package edu.yuferov.shop.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.Product

class ProductAttributeAdapter : RecyclerView.Adapter<ProductAttributeAdapter.ViewHolder>() {
    var data = listOf<Product.Attribute>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.of(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.list_item_product_attribute_tv_name)
        private val value: TextView =
            itemView.findViewById(R.id.list_item_product_attribute_tv_value)

        fun bind(model: Product.Attribute) {
            name.text = model.name
            value.text = model.value
        }

        companion object {
            @JvmStatic
            fun of(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.list_item_product_attribute, parent, false)
                return ViewHolder(view)
            }
        }
    }

}
