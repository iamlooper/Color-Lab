package com.looper.seeker.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.looper.android.support.preference.PreferenceFragment
import com.looper.android.support.util.SharedPreferencesUtils
import com.looper.seeker.MyApp
import com.looper.seeker.R
import com.looper.seeker.worker.TippingWorker

class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize variables.
        sharedPreferencesUtils = SharedPreferencesUtils(requireContext())
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Register the listener.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences, key: String) {
        val rootService = MyApp.rootConnectionProvider?.serviceProviderIPC
        if (rootService == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.root_not_found), Toast.LENGTH_LONG
            ).show()
            return
        }

        when (key) {
            "pref_apply_theme_on_boot" -> {
                val disableTheming = prefs.getStringSet("pref_disable_theming", emptySet())
                if (disableTheming.size == 5) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.theming_is_disabled), Toast.LENGTH_SHORT
                    ).show()
                    findPreference<SwitchPreferenceCompat>("pref_apply_theme_on_boot")?.isChecked =
                        false
                    return
                }
            }

            "pref_use_precise_colors" -> {
                val disableTheming = prefs.getStringSet("pref_disable_theming", emptySet())
                if (disableTheming.size == 5) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.theming_is_disabled), Toast.LENGTH_SHORT
                    ).show()
                    findPreference<SwitchPreferenceCompat>("pref_use_precise_colors")?.isChecked =
                        false
                    return
                }

                rootService.changeTheme(
                    if (!disableTheming.contains("primary_accent")) prefs.getInt(
                        "pref_primary_accent_changer",
                        0
                    ) else 0,
                    if (!disableTheming.contains("secondary_accent")) prefs.getInt(
                        "pref_secondary_accent_changer",
                        0
                    ) else 0,
                    if (!disableTheming.contains("tertiary_accent")) prefs.getInt(
                        "pref_tertiary_accent_changer",
                        0
                    ) else 0,
                    if (!disableTheming.contains("primary_neutral")) prefs.getInt(
                        "pref_primary_neutral_changer",
                        0
                    ) else 0,
                    if (!disableTheming.contains("secondary_neutral")) prefs.getInt(
                        "pref_secondary_neutral_changer",
                        0
                    ) else 0,
                    prefs.getString("pref_style_selector", "none"),
                    prefs.getBoolean("pref_use_precise_colors", false)
                )
            }

            "pref_tipping_interval" -> {
                TippingWorker.scheduleTipping(
                    requireContext(),
                    prefs.getBoolean("pref_disable_tipping", false),
                    prefs.getString("pref_tipping_interval", "1").toInt()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Unregister the listener.
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun customizePreferenceDialog(
        preference: Preference,
        dialogBuilder: MaterialAlertDialogBuilder,
        dialogView: View?
    ) {
        when (preference.key) {
            "pref_disable_theming" -> {
                dialogBuilder.setOnDismissListener {
                    isDialogVisible = true
                    disableTheming()
                }
            }
        }
    }

    private fun disableTheming() {
        val rootService = MyApp.rootConnectionProvider?.serviceProviderIPC
        if (rootService == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.root_not_found), Toast.LENGTH_LONG
            ).show()
            return
        }

        val options = sharedPreferencesUtils.get("pref_disable_theming", emptySet<String>())

        rootService.changeTheme(
            if (!options.contains("primary_accent")) sharedPreferencesUtils.get(
                "pref_primary_accent_changer",
                0
            ) else 0,
            if (!options.contains("secondary_accent")) sharedPreferencesUtils.get(
                "pref_secondary_accent_changer",
                0
            ) else 0,
            if (!options.contains("tertiary_accent")) sharedPreferencesUtils.get(
                "pref_tertiary_accent_changer",
                0
            ) else 0,
            if (!options.contains("primary_neutral")) sharedPreferencesUtils.get(
                "pref_primary_neutral_changer",
                0
            ) else 0,
            if (!options.contains("secondary_neutral")) sharedPreferencesUtils.get(
                "pref_secondary_neutral_changer",
                0
            ) else 0,
            sharedPreferencesUtils.get("pref_style_selector", "none"),
            sharedPreferencesUtils.get("pref_use_precise_colors", false)
        )
    }
}