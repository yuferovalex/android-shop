package edu.yuferov.shop.app.fragment

import android.view.View
import androidx.constraintlayout.widget.Group
import edu.yuferov.shop.app.presenter.IBaseView
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment(), IBaseView {
    protected lateinit var mainViewGroup: Group
    protected lateinit var networkStatus: NetworkStatusFragment

    override fun showLoadingStatus() {
        mainViewGroup.visibility = View.GONE
        networkStatus.status = NetworkStatusFragment.Status.Loading
    }

    override fun hideLoadingStatus() {
        mainViewGroup.visibility = View.VISIBLE
        networkStatus.status = NetworkStatusFragment.Status.Loaded
    }

    override fun showLoadErrorStatus() {
        mainViewGroup.visibility = View.GONE
        networkStatus.status = NetworkStatusFragment.Status.Error
    }
}