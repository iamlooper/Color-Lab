package com.looper.seeker.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.looper.android.support.preference.PreferenceFragment
import com.looper.seeker.MyApp
import com.looper.seeker.R
import io.noties.markwon.Markwon

class MainFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener,
    MenuProvider {

    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_main, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup MenuProvider.
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Initialize variables.
        navController = view.findNavController()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Register the listener.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun displayInfoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_monet_info, null)
        val dialogTextView = dialogView.findViewById<TextView>(R.id.dialog_text)

        val markdownText = getString(R.string.info_colors_text)
        val markwon = Markwon.builder(requireContext()).build()
        markwon.setMarkdown(dialogTextView, markdownText)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.info_colors_title))
            .setView(dialogView)
            .setPositiveButton(com.looper.android.support.R.string.okay, null)
            .show()
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

        val disableTheming = prefs.getStringSet("pref_disable_theming", emptySet())
        if (disableTheming.size == 5) {
            Toast.makeText(
                requireContext(),
                getString(R.string.theming_is_disabled), Toast.LENGTH_LONG
            ).show()
            return
        }

        when (key) {
            "pref_primary_accent_changer" -> {
                val isDisabled = disableTheming.contains("primary_accent")
                if (isDisabled) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.primary_accent_color_is_disabled), Toast.LENGTH_LONG
                    ).show()
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

            "pref_secondary_accent_changer" -> {
                val isDisabled = disableTheming.contains("secondary_accent")
                if (isDisabled) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.secondary_accent_color_is_disabled), Toast.LENGTH_LONG
                    ).show()
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

            "pref_tertiary_accent_changer" -> {
                val isDisabled = disableTheming.contains("tertiary_accent")
                if (isDisabled) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.tertiary_accent_color_is_disabled), Toast.LENGTH_LONG
                    ).show()
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

            "pref_primary_neutral_changer" -> {
                val isDisabled = disableTheming.contains("primary_neutral")
                if (isDisabled) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.primary_neutral_color_is_disabled), Toast.LENGTH_LONG
                    ).show()
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

            "pref_secondary_neutral_changer" -> {
                val isDisabled = disableTheming.contains("secondary_neutral")
                if (isDisabled) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.secondary_neutral_color_is_disabled), Toast.LENGTH_LONG
                    ).show()
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

            "pref_style_selector" -> {
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Unregister the listener.
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_info -> {
                displayInfoDialog()
                true
            }

            R.id.action_fragment_settings -> {
                navController.navigate(R.id.fragment_settings)
                true
            }

            R.id.action_fragment_about -> {
                navController.navigate(R.id.fragment_about)
                true
            }

            else -> false
        }
    }
}