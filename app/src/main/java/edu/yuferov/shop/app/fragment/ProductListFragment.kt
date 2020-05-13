package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.ProductListAdapter
import edu.yuferov.shop.app.presenter.IProductListView
import edu.yuferov.shop.app.presenter.ProductListPresenter
import edu.yuferov.shop.domain.Product
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class ProductListFragment : MvpAppCompatFragment(), IProductListView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: ProductListPresenter.Fabric
    private val presenter by moxyPresenter {
        val args = ProductListFragmentArgs.fromBundle(requireArguments())
        presenterProvider.create(args.categoryId)
    }

    override lateinit var status: NetworkStatusFragment
    private lateinit var itemListAdapter: ProductListAdapter
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
        val root = inflater.inflate(R.layout.fragment_product_list, container, false)
        status =
            childFragmentManager.findFragmentById(R.id.fragment_product_list_status) as NetworkStatusFragment

        scrollView = root.findViewById(R.id.fragment_product_list_sv_scroll)

        val itemList: RecyclerView = scrollView.findViewById(R.id.fragment_product_list_rv_items)
        val itemListLayoutManager = LinearLayoutManager(context)

        itemListAdapter = ProductListAdapter()
        itemList.adapter = itemListAdapter
        itemList.layoutManager = itemListLayoutManager

        itemListAdapter.onAddToCardClickedListener = ProductListAdapter.OnAddToCardClickedListener {
            presenter.onAddToCardClicked(it)
        }

        itemListAdapter.onClickedListener = ProductListAdapter.OnClickedListener {
            presenter.onProductDetailsRequested(it)
        }

        return root
    }

    override fun bind(data: List<Product>) {
        itemListAdapter.data = data
        status.status = NetworkStatusFragment.Status.Loaded
        scrollView.visibility = View.VISIBLE
    }

    override fun navigateToProductDetails(productId: Int) {
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(
                productId
            )
        )
    }
}
