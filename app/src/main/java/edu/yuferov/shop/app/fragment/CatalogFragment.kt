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

class CatalogFragment : MvpAppCompatFragment(), ICatalogView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: Provider<CatalogPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override lateinit var status: NetworkStatusFragment
    private lateinit var itemList: RecyclerView
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
        status = childFragmentManager.findFragmentById(R.id.fragment_catalog_status) as NetworkStatusFragment

        listItemAdapter = CategoryAdapter()
        listItemAdapter.clickHandler = CategoryAdapter.OnClickListener {
            presenter.categoryClicked(it)
        }

        itemList = root.findViewById<RecyclerView>(R.id.fragment_catalog_rv_items)
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = listItemAdapter

        return root
    }

    override fun bind(categories: List<Category>) {
        status.status = NetworkStatusFragment.Status.Loaded
        itemList.visibility = View.VISIBLE
        listItemAdapter.data = categories
    }

    override fun navigateToCategory(categoryId: Int) {
        findNavController().navigate(
            CatalogFragmentDirections.actionCatalogFragmentToProductListFragment(
                categoryId
            )
        )
    }
}
