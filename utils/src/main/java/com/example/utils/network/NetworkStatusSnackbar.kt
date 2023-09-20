package com.example.utils.network

import android.view.View
import com.example.utils.R
import com.google.android.material.snackbar.Snackbar

class NetworkStatusSnackbar(private val rootView: View) {
    private var snackbar: Snackbar? = null

    fun showNoInternetConnectionMessage() {
        snackbar = Snackbar.make(rootView, R.string.dialog_title_device_is_offline, Snackbar.LENGTH_INDEFINITE)
        snackbar?.setAction("Close") {
            snackbar?.dismiss()
        }
        snackbar?.show()
    }

    fun hideNoInternetConnectionMessage() {
        snackbar?.dismiss()
    }
}