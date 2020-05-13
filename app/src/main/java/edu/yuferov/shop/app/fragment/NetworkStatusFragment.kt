package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.yuferov.shop.R

class NetworkStatusFragment : Fragment() {

    enum class Status {
        Loading,
        Loaded,
        Error
    }

    var status: Status? = null
        set(value) {
            if (value == field) {
                return
            }
            field = value
            when (value) {
                Status.Loading -> {
                    view?.visibility = View.VISIBLE
                    icon.setImageResource(R.drawable.loading_animation)
                    text.setText(R.string.loading_status)
                }
                Status.Loaded -> {
                    view?.visibility = View.GONE
                }
                Status.Error -> {
                    view?.visibility = View.VISIBLE
                    icon.setImageResource(R.drawable.ic_error_outline_black_24dp)
                    text.setText(R.string.loading_error_status)
                }
            }
        }

    private lateinit var icon: ImageView
    private lateinit var text: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_status, container, false)
        icon = root.findViewById(R.id.fragment_status_iv_icon)
        text = root.findViewById(R.id.fragment_status_tv_description)
        root.visibility = View.GONE
        return root
    }
}
