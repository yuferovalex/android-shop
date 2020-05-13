package edu.yuferov.shop.app.fragment

import edu.yuferov.shop.app.presenter.IHasNetworkStatusView

interface IHasNetworkStatusMixin : IHasNetworkStatusView {
    val status: NetworkStatusFragment

    override fun showLoadingStatus() {
        status.status =
            NetworkStatusFragment.Status.Loading
    }

    override fun showLoadErrorStatus() {
        status.status =
            NetworkStatusFragment.Status.Error
    }
}