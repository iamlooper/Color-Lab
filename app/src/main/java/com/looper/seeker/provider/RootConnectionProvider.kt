package com.looper.seeker.provider

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.looper.seeker.service.IRootConnection
import com.looper.seeker.service.RootConnection
import com.topjohnwu.superuser.ipc.RootService

class RootConnectionProvider(
    private val context: Context,
    private val onSuccess: ((IRootConnection?) -> Unit)? = null,
    private val onFailure: (() -> Unit)? = null
) : ServiceConnection {

    var serviceProviderIPC: IRootConnection? = null
    var isServiceConnected = false

    init {
        bindServiceConnection()
    }

    private fun bindServiceConnection() {
        if (isServiceConnected) return

        RootService.bind(
            Intent(context, RootConnection::class.java),
            this
        )
    }

    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
        serviceProviderIPC = IRootConnection.Stub.asInterface(binder)
        isServiceConnected = true
        onSuccess?.invoke(serviceProviderIPC)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        serviceProviderIPC = null
        isServiceConnected = false
        onFailure?.invoke()
    }
}
