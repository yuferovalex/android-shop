package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.CategoryAdapter
import edu.yuferov.shop.app.presenter.CatalogPresenter
import edu.yuferov.shop.app.presenter.ICatalogView
import edu.yuferov.shop.domain.Category
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CatalogFragment : MvpAppCompatFragment(), ICatalogView {
    @Inject
    lateinit var presenterProvider: Provider<CatalogPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var scrollView: ScrollView
    private lateinit var status: View
    private lateinit var statusIcon: ImageView
    private lateinit var statusText: TextView
    private lateinit var listItemAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_catalog, container, false)
        status = root.findViewById(R.id.fragment_catalog_status)
        statusIcon = status.findViewById(R.id.fragment_status_iv_icon)
        statusText = status.findViewById(R.id.fragment_status_tv_description)
        scrollView = root.findViewById(R.id.fragment_catalog_sv_scrol)

        listItemAdapter = CategoryAdapter()
        listItemAdapter.clickHandler = CategoryAdapter.OnClickListener {
            presenter.categoryClicked(it)
        }

        val itemList = scrollView.findViewById<RecyclerView>(R.id.fragment_catalog_rv_items)
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = listItemAdapter

        return root
    }

    override fun bind(categories: List<Category>) {
        status.visibility = View.GONE
        scrollView.visibility = View.VISIBLE
        listItemAdapter.data = categories
    }

    override fun showLoadingStatus() {
        statusIcon.setImageResource(R.drawable.loading_animation)
        statusText.text = getString(R.string.loading_status)
    }

    override fun showLoadErrorStatus() {
        statusIcon.setImageResource(R.drawable.ic_error_outline_black_24dp)
        statusText.text = getString(R.string.loading_error_status)
    }

    override fun navigateToCategory(categoryId: Int) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToProductListFragment(
                categoryId
            )
        )
    }
}
