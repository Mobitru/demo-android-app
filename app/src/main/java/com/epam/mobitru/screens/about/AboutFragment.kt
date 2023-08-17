package com.epam.mobitru.screens.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.epam.mobitru.R
import com.epam.mobitru.databinding.FragmentAboutBinding
import com.epam.mobitru.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * About screen for show html content
 */
class AboutFragment : Fragment(R.layout.fragment_about) {

    private val binding by viewBinding(FragmentAboutBinding::bind)

    private val viewModel: AboutViewModel by viewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.webContent?.apply {
            settings.javaScriptEnabled = true
            loadUrl("https://mobitru.com/")
        }
    }
}