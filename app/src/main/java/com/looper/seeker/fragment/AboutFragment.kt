package com.looper.seeker.fragment

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import com.looper.android.support.preference.PreferenceFragment
import com.looper.android.support.util.AppUtils
import com.looper.seeker.R

class AboutFragment : PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_about, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefVersionInfo = findPreference<Preference>("pref_version_info")
        prefVersionInfo!!.summary = AppUtils.getVersionName(requireContext())
    }
}