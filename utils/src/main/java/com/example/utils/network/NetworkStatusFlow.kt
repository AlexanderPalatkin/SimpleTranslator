package com.example.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkStatusFlow(context: Context) {
    private val availableNetworks = mutableSetOf<Network>()

    private val isOnlineFlow = MutableStateFlow(false)

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as
                ConnectivityManager

    private val request: NetworkRequest = NetworkRequest.Builder().build()

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            availableNetworks.remove(network)
            update(availableNetworks.isNotEmpty())
        }

        override fun onAvailable(network: Network) {
            availableNetworks.add(network)
            update(availableNetworks.isNotEmpty())
        }
    }

    init {
        connectivityManager.registerNetworkCallback(request, callback)
    }

    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private fun update(online: Boolean) {
        isOnlineFlow.value = online
    }

    fun observeState(): Flow<Boolean> = isOnlineFlow
}