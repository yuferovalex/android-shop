package edu.yuferov.shop.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.yuferov.shop.R
import edu.yuferov.shop.app.App
import edu.yuferov.shop.app.adapter.ProductAttributeAdapter
import edu.yuferov.shop.app.presenter.IHasNetworkStatusView
import edu.yuferov.shop.app.presenter.IProductDetailView
import edu.yuferov.shop.app.presenter.ProductDetailPresenter
import edu.yuferov.shop.domain.Product
import edu.yuferov.shop.util.bindPrice
import edu.yuferov.shop.util.loadImage
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class ProductDetailFragment : MvpAppCompatFragment(), IProductDetailView, IHasNetworkStatusMixin {
    @Inject
    lateinit var presenterProvider: ProductDetailPresenter.Fabric
    private val presenter by moxyPresenter {
        val args = ProductDetailFragmentArgs.fromBundle(requireArguments())
        presenterProvider.create(args.productId)
    }

    override lateinit var status: NetworkStatusFragment
    private lateinit var image: ImageView
    private lateinit var price: TextView
    private lateinit var discountPrice: TextView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var productAttributesAdapter: ProductAttributeAdapter
    private lateinit var scrollView: ScrollView
    private lateinit var addToCartBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_product_detail, container, false)

        status =
            childFragmentManager.findFragmentById(R.id.fragment_product_detail_status) as NetworkStatusFragment
        scrollView = root.findViewById(R.id.fragment_product_detail_sv_scroll)
        image = root.findViewById(R.id.fragment_product_detail_iv_image)
        price = root.findViewById(R.id.fragment_product_detail_tv_price)
        discountPrice = root.findViewById(R.id.fragment_product_detail_tv_discount_price)
        title = root.findViewById(R.id.fragment_product_detail_tv_title)
        description = root.findViewById(R.id.fragment_product_detail_tv_description)

        val attributes: RecyclerView = root.findViewById(R.id.fragment_product_detail_rv_attributes)
        productAttributesAdapter = ProductAttributeAdapter()
        attributes.adapter = productAttributesAdapter
        attributes.layoutManager = LinearLayoutManager(context)
        attributes.isNestedScrollingEnabled = false

        addToCartBtn = root.findViewById(R.id.fragment_product_detail_btn_add_to_cart)
        addToCartBtn.setOnClickListener { presenter.onAddToCartClicked() }

        return root
    }

    override fun bind(product: Product) {
        image.loadImage(product.image)
        bindPrice(price, discountPrice, product)
        title.text = product.title
        description.text = product.description
        productAttributesAdapter.data = product.attributes

        status.status = NetworkStatusFragment.Status.Loaded
        scrollView.visibility = View.VISIBLE
        addToCartBtn.visibility = View.VISIBLE
    }
}
