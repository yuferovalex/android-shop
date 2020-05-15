package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.ProductListAdapter
import edu.yuferov.shop.app.presenter.IProductListView
import edu.yuferov.shop.app.presenter.ProductListPresenter
import edu.yuferov.shop.databinding.FragmentProductListBinding
import edu.yuferov.shop.domain.Product
import edu.yuferov.shop.util.EndlessRecyclerViewScrollListener
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class ProductListFragment : BaseFragment(), IProductListView {

    @Inject
    lateinit var presenterProvider: ProductListPresenter.Fabric
    private val presenter by moxyPresenter {
        val args = ProductListFragmentArgs.fromBundle(requireArguments())
        presenterProvider.create(args.categoryId)
    }

    private lateinit var binding: FragmentProductListBinding
    private lateinit var itemListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)

        networkStatus =
            childFragmentManager.findFragmentById(R.id.fragment_product_list_status) as NetworkStatusFragment
        mainViewGroup =
            binding.fragmentProductListMainViewGroup

        itemListAdapter = ProductListAdapter()
        itemListAdapter.onAddToCardClickedListener = ProductListAdapter.OnAddToCardClickedListener {
            presenter.onAddToCardClicked(it)
        }
        itemListAdapter.onClickedListener = ProductListAdapter.OnClickedListener {
            presenter.onProductDetailsRequested(it)
        }

        val layoutManager = LinearLayoutManager(context)
        binding.fragmentProductListRvItems.adapter = itemListAdapter
        binding.fragmentProductListRvItems.layoutManager = layoutManager
        binding.fragmentProductListRvItems.addOnScrollListener(
            EndlessRecyclerViewScrollListener(layoutManager) { page, _, _ ->
                presenter.onLoadNextPage(page)
            }
        )

        return binding.root
    }

    override fun bind(data: List<Product>) {
        itemListAdapter.data = data
    }

    override fun itemsAppended(from: Int, count: Int) {
        itemListAdapter.notifyItemRangeInserted(from, count)
    }

    override fun navigateToProductDetails(productId: Int) {
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment(
                productId
            )
        )
    }
}
