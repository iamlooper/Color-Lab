package com.looper.seeker.service

import android.annotation.SuppressLint
import android.content.Intent
import android.content.om.OverlayIdentifier
import android.os.IBinder
import com.looper.seeker.model.Overlay
import com.looper.seeker.model.Resource
import com.looper.seeker.util.ColorUtils
import com.looper.seeker.util.FabricatedOverlayUtils
import com.topjohnwu.superuser.ipc.RootService

class RootConnection : RootService() {

    private lateinit var fabricatedOverlayUtils: FabricatedOverlayUtils

    inner class RootIPC : IRootConnection.Stub() {
        @SuppressLint("NewApi")
        override fun changeTheme(
            primaryAccentColor: Int,
            secondaryAccentColor: Int,
            tertiaryAccentColor: Int,
            primaryNeutralColor: Int,
            secondaryNeutralColor: Int,
            style: String,
            usePreciseColors: Boolean
        ) {
            val resources = mutableListOf<Resource>()

            if (primaryAccentColor != 0) {
                val newColor = ColorUtils.applyStyle(primaryAccentColor, style)
                resources.addAll(
                    createAccentResources(
                        newColor,
                        "system_accent1",
                        usePreciseColors
                    )
                )
            }
            if (secondaryAccentColor != 0) {
                val newColor = ColorUtils.applyStyle(secondaryAccentColor, style)
                resources.addAll(
                    createAccentResources(
                        newColor,
                        "system_accent2",
                        usePreciseColors
                    )
                )
            }
            if (tertiaryAccentColor != 0) {
                val newColor = ColorUtils.applyStyle(tertiaryAccentColor, style)
                resources.addAll(
                    createAccentResources(
                        newColor,
                        "system_accent3",
                        usePreciseColors
                    )
                )
            }
            if (primaryNeutralColor != 0) {
                val newColor = ColorUtils.applyStyle(primaryNeutralColor, style)
                resources.addAll(
                    createNeutralResources(
                        newColor,
                        "system_neutral1",
                        usePreciseColors
                    )
                )
            }
            if (secondaryNeutralColor != 0) {
                val newColor = ColorUtils.applyStyle(secondaryNeutralColor, style)
                resources.addAll(
                    createNeutralResources(
                        newColor,
                        "system_neutral2",
                        usePreciseColors
                    )
                )
            }

            if (resources.isNotEmpty()) {
                val overlay = Overlay("android", "com.looper.seeker.overlay", "android", resources)
                val overlayIdentifier = fabricatedOverlayUtils.registerFabricatedOverlay(overlay)
                fabricatedOverlayUtils.setEnabled(overlayIdentifier!!, true)
            } else {
                setEnabled("android", "com.looper.seeker.overlay", false)
            }
        }

        @SuppressLint("NewApi")
        override fun setEnabled(packageName: String, overlayName: String, enable: Boolean) {
            val overlayIdentifier = OverlayIdentifier(packageName, overlayName)
            fabricatedOverlayUtils.setEnabled(overlayIdentifier, enable)
        }

        private fun createAccentResources(
            color: Int,
            prefix: String,
            usePreciseColors: Boolean
        ): List<Resource> {
            val resources = listOf(
                Resource(
                    "android:color/${prefix}_10",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 99.0)
                ),
                Resource(
                    "android:color/${prefix}_50",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 95.0)
                ),
                Resource(
                    "android:color/${prefix}_100",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 90.0)
                ),
                Resource(
                    "android:color/${prefix}_200",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 80.0)
                ),
                Resource(
                    "android:color/${prefix}_300",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 70.0)
                ),
                Resource(
                    "android:color/${prefix}_400",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 60.0)
                ),
                Resource(
                    "android:color/${prefix}_500",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 49.6)
                ),
                Resource(
                    "android:color/${prefix}_600",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 40.0)
                ),
                Resource(
                    "android:color/${prefix}_700",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 30.0)
                ),
                Resource(
                    "android:color/${prefix}_800",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 20.0)
                ),
                Resource(
                    "android:color/${prefix}_900",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 10.0)
                )
            )

            if (usePreciseColors) {
                resources[3].value = color
                resources[9].value = color
            }

            return resources
        }

        private fun createNeutralResources(
            color: Int,
            prefix: String,
            usePreciseColors: Boolean
        ): List<Resource> {
            val resources = listOf(
                Resource(
                    "android:color/${prefix}_10",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 99.0)
                ),
                Resource(
                    "android:color/${prefix}_50",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 95.0)
                ),
                Resource(
                    "android:color/${prefix}_100",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 90.0)
                ),
                Resource(
                    "android:color/${prefix}_200",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 80.0)
                ),
                Resource(
                    "android:color/${prefix}_300",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 70.0)
                ),
                Resource(
                    "android:color/${prefix}_400",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 60.0)
                ),
                Resource(
                    "android:color/${prefix}_500",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 49.0)
                ),
                Resource(
                    "android:color/${prefix}_600",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 40.0)
                ),
                Resource(
                    "android:color/${prefix}_700",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 30.0)
                ),
                Resource(
                    "android:color/${prefix}_800",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 20.0)
                ),
                Resource(
                    "android:color/${prefix}_900",
                    0x1c,
                    ColorUtils.adjustLuminance(color, 10.0)
                )
            )

            if (usePreciseColors) {
                if (prefix == "system_neutral1") {
                    resources[7].value = color
                } else if (prefix == "system_neutral2") {
                    resources[6].value = color
                    resources[9].value = color
                }
            }

            return resources
        }
    }

    override fun onCreate() {
        super.onCreate()
        fabricatedOverlayUtils = FabricatedOverlayUtils()
    }

    override fun onBind(intent: Intent): IBinder {
        return RootIPC()
    }
}