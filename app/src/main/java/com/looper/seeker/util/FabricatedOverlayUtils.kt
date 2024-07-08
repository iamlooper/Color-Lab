package com.looper.seeker.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.om.FabricatedOverlay
import android.content.om.IOverlayManager
import android.content.om.OverlayIdentifier
import android.content.om.OverlayManagerTransaction
import android.os.IBinder
import android.os.ServiceManager
import com.looper.seeker.model.Overlay

@SuppressLint("InlinedApi")
class FabricatedOverlayUtils {

    private lateinit var overlayManager: IOverlayManager

    init {
        try {
            val binder: IBinder = ServiceManager.getService(Context.OVERLAY_SERVICE)
            overlayManager = IOverlayManager.Stub.asInterface(binder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("NewApi")
    fun registerFabricatedOverlay(overlay: Overlay): OverlayIdentifier? {
        val overlayBuilder =
            FabricatedOverlay.Builder(overlay.owningPackage, overlay.name, overlay.targetPackage)

        for (resource in overlay.resources) {
            @Suppress("DEPRECATION")
            overlayBuilder.setResourceValue(resource.name, resource.type, resource.value)
        }

        val fabricatedOverlay = overlayBuilder.build() as FabricatedOverlay
        try {
            overlayManager.commit(
                OverlayManagerTransaction.Builder()
                    .registerFabricatedOverlay(fabricatedOverlay)
                    .build()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return fabricatedOverlay.identifier
    }

    fun unregisterFabricatedOverlay(overlayIdentifier: OverlayIdentifier) {
        try {
            overlayManager.commit(
                OverlayManagerTransaction.Builder()
                    .unregisterFabricatedOverlay(overlayIdentifier)
                    .build()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setEnabled(overlayIdentifier: OverlayIdentifier, enable: Boolean) {
        try {
            overlayManager.commit(
                OverlayManagerTransaction.Builder()
                    .setEnabled(overlayIdentifier, enable)
                    .build()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
