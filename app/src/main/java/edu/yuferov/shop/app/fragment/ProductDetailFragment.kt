package edu.yuferov.shop.app.fragment

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.ProductAttributeAdapter
import edu.yuferov.shop.app.presenter.IProductDetailView
import edu.yuferov.shop.app.presenter.ProductDetailPresenter
import edu.yuferov.shop.databinding.FragmentProductDetailBinding
import edu.yuferov.shop.databinding.FragmentProductDetailInnerBinding
import edu.yuferov.shop.domain.Product
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.loadImage
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class ProductDetailFragment : BaseFragment(), IProductDetailView {

    @Inject
    lateinit var presenterProvider: ProductDetailPresenter.Fabric
    private val presenter by moxyPresenter {
        val args = ProductDetailFragmentArgs.fromBundle(requireArguments())
        presenterProvider.create(args.productId)
    }

    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var innerBinding: FragmentProductDetailInnerBinding
    private lateinit var productAttributesAdapter: ProductAttributeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        innerBinding = FragmentProductDetailInnerBinding.bind(binding.root)

        networkStatus =
            childFragmentManager.findFragmentById(R.id.fragment_product_detail_status) as NetworkStatusFragment
        mainViewGroup = binding.fragmentProductDetailMainViewGroup

        val attributes: RecyclerView = innerBinding.fragmentProductDetailRvAttributes
        productAttributesAdapter = ProductAttributeAdapter()
        attributes.adapter = productAttributesAdapter
        attributes.layoutManager = LinearLayoutManager(context)
        attributes.isNestedScrollingEnabled = false

        binding.fragmentProductDetailBtnAddToCart.setOnClickListener {
            presenter.onAddToCartClicked()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_deteil_menu_share -> {
                presenter.onShareButtonClicked()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun bind(product: Product) {
        innerBinding.fragmentProductDetailIvImage.loadImage(product.image)
        bindPrice(
            innerBinding.fragmentProductDetailTvPrice,
            innerBinding.fragmentProductDetailTvDiscountPrice,
            product
        )
        innerBinding.fragmentProductDetailTvTitle.text = product.title
        innerBinding.fragmentProductDetailTvDescription.text = product.description
        productAttributesAdapter.data = product.attributes
    }
}
