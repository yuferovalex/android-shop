package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.CategoryAdapter
import edu.yuferov.shop.app.presenter.CatalogPresenter
import edu.yuferov.shop.app.presenter.ICatalogView
import edu.yuferov.shop.databinding.FragmentCartBinding
import edu.yuferov.shop.databinding.FragmentCatalogBinding
import edu.yuferov.shop.domain.Category
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class CatalogFragment : BaseFragment(), ICatalogView {
    @Inject
    lateinit var presenterProvider: Provider<CatalogPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private lateinit var binding: FragmentCatalogBinding
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
        binding = FragmentCatalogBinding.inflate(inflater, container, false)

        networkStatus =
            childFragmentManager.findFragmentById(R.id.fragment_catalog_status) as NetworkStatusFragment
        mainViewGroup = binding.fragmentCatalogMainViewGroup

        listItemAdapter = CategoryAdapter()
        listItemAdapter.clickHandler = CategoryAdapter.OnClickListener {
            presenter.categoryClicked(it)
        }

        binding.fragmentCatalogRvItems.layoutManager = LinearLayoutManager(context)
        binding.fragmentCatalogRvItems.adapter = listItemAdapter

        return binding.root
    }

    override fun bind(categories: List<Category>) {
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
