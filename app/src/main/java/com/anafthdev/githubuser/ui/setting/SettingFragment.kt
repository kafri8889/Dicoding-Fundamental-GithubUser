package com.anafthdev.githubuser.ui.setting

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.anafthdev.githubuser.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : PreferenceFragmentCompat() {

    private lateinit var darkModePref: SwitchPreferenceCompat

    private val viewModel: SettingViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        darkModePref = preferenceManager.findPreference("darkMode")!!

        darkModePref.setOnPreferenceChangeListener { _, newValue ->
            viewModel.setDarkTheme(newValue.toString().toBoolean())
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::updateUi)
    }

    private fun updateUi(state: SettingState) {
        darkModePref.isChecked = state.isDarkTheme
        darkModePref.icon = if (state.isDarkTheme) ResourcesCompat.getDrawable(resources, R.drawable.ic_moon, requireActivity().theme)
        else ResourcesCompat.getDrawable(resources, R.drawable.ic_sun, requireActivity().theme)
    }
}