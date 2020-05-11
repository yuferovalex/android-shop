package edu.yuferov.shop.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.util.loadImage

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var data = listOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickHandler: OnClickListener? = null

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.of(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickHandler)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.list_item_category_tv_name)
        private val icon = itemView.findViewById<ImageView>(R.id.list_item_category_iv_icon)

        fun bind(
            model: Category,
            clickHandler: OnClickListener?
        ) {
            name.text = model.name
            icon.loadImage(model.icon)
            itemView.setOnClickListener { clickHandler?.onClick(model) }
        }

        companion object {
            @JvmStatic
            fun of(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.list_item_category, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class OnClickListener(private val impl: (category: Category) -> Unit) {
        fun onClick(category: Category) = impl(category)
    }
}
