package com.example.core.base

import android.os.Bundle
import android.view.View
import com.example.core.R
import com.example.core.databinding.LoadingLayoutBinding
import com.example.core.viewmodel.BaseViewModel
import com.example.core.viewmodel.Interactor
import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.utils.network.NetworkStatusFlow
import com.example.utils.network.NetworkStatusSnackbar
import com.example.utils.ui.AlertDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity

private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

abstract class BaseActivity<T : AppState, I : Interactor<T>> : ScopeActivity() {

    private lateinit var binding: LoadingLayoutBinding

    abstract val model: BaseViewModel<T>

    private lateinit var networkStatusSnackbar: NetworkStatusSnackbar

    private lateinit var networkStatus: NetworkStatusFlow

    protected var isNetworkAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkStatusSnackbar = NetworkStatusSnackbar(findViewById(android.R.id.content))

        initNetworkStatus()
    }

    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)

        if (!isNetworkAvailable && isDialogNull()) {
            networkStatusSnackbar.showNoInternetConnectionMessage()
        } else {
            networkStatusSnackbar.hideNoInternetConnectionMessage()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_tittle_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(
                    getString(R.string.error_stub),
                    appState.error.message
                )
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        binding.loadingFrameLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingFrameLayout.visibility = View.VISIBLE
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    private fun initNetworkStatus() {
        networkStatus = NetworkStatusFlow(this@BaseActivity)

        CoroutineScope(Dispatchers.Main).launch {
            networkStatus.observeState().collect { isAvailable ->
                this@BaseActivity.isNetworkAvailable = isAvailable
                if (!isAvailable) {
                    networkStatusSnackbar.showNoInternetConnectionMessage()
                } else {
                    networkStatusSnackbar.hideNoInternetConnectionMessage()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        networkStatus.unregisterNetworkCallback()
    }
}