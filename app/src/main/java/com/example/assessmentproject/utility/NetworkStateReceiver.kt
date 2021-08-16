package com.example.assessmentproject.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
class NetworkStateReceiver : BroadcastReceiver() {

    protected var listenersList: HashSet<NetworkStateReceiverListener>? =null
    protected var connected: Boolean? = null

    //Interface creation
    interface NetworkStateReceiverListener {
        fun networkAvailable()
        fun networkUnavailable()
    }
    init {
        listenersList = HashSet()
        connected = null
    }
    //Listening network status
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || intent.extras == null) return
        val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = manager.activeNetworkInfo
        if (ni != null && ni.state == NetworkInfo.State.CONNECTED) {
            connected = true
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)) {
            connected = false
        }
        notifyStateToAll()
    }

    private fun notifyStateToAll() {
        for (listener in listenersList!!) notifyState(listener)
    }

    private fun notifyState(listener: NetworkStateReceiverListener?) {
        if (connected == null || listener == null) return
        if (connected == true) listener.networkAvailable() else listener.networkUnavailable()
    }
    // added to Listener list
    fun addListener(l: NetworkStateReceiverListener?) {
        l?.let { listenersList?.add(it) }
        notifyState(l)
    }
    // remove to Listener list
    fun removeListener(l: NetworkStateReceiverListener?) {
        listenersList?.remove(l)
    }

}