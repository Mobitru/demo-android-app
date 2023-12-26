package com.epam.mobitru.screens.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentLoginBinding
import com.epam.mobitru.extentions.requireEditText
import com.epam.mobitru.util.viewBinding
import com.epam.mobitru.views.TextWatcherAfterChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.Executor

/**
 * Login page
 */
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        private const val SUPPORTED_BIO = BIOMETRIC_STRONG or BIOMETRIC_WEAK
    }

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override val viewModel: LoginViewModel by viewModel()

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                handleState(it)
            }
        }
        createBiometricPrompt()
    }

    private fun createBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = createBiometricPromptInstance()
        promptInfo =
            BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.biometric_login_title))
                .setSubtitle(getString(R.string.biometric_login_subtitle))
                .setNegativeButtonText(getString(R.string.biometric_login_cancel)).build()

    }

    private fun createBiometricPromptInstance() =
        BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(
                errorCode: Int, errString: CharSequence
            ) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    requireContext(), "Authentication error: $errString", Toast.LENGTH_SHORT
                ).show()
                Timber.w("Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                viewModel.onBioAuth()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(
                    requireContext(), "Authentication failed", Toast.LENGTH_SHORT
                ).show()
                Timber.w("Authentication failed")
            }
        })

    private fun handleState(it: LoginViewModel.State) {
        Timber.d("State collected $it")
        when (it) {
            LoginViewModel.State.Default -> {
                binding?.apply {
                    loginError.isVisible = false
                    progressContainer.progressRoot.isVisible = false
                }
            }
            LoginViewModel.State.Error -> {
                binding?.apply {
                    progressContainer.progressRoot.isVisible = false
                    loginError.isVisible = true
                }
            }
            LoginViewModel.State.Progress -> {
                binding?.apply {
                    progressContainer.progressRoot.isVisible = true
                }
            }
            LoginViewModel.State.Success -> {
                binding?.apply {
                    progressContainer.progressRoot.isVisible = false
                    loginError.isVisible = false
                }

                navigator.navigate(NavCommand.Direction(LoginFragmentDirections.actionLoginFragmentToMainFragment()))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreState()
        subscribeViews()
        subscribeBiometric()
    }

    override fun onResume() {
        super.onResume()
        showLogo()
    }

    private fun restoreState() {
        binding?.apply {
            loginEmail.editText?.setText(viewModel.email.value)
            loginPassword.editText?.setText(viewModel.password.value)
        }
    }

    private fun typeDefaultLogin() {
        binding?.apply {
            loginEmail.editText?.setText(getString(R.string.testuser_mobitru))
            loginPassword.editText?.setText(getString(R.string.testpassword_mobitru))
        }
    }

    private fun subscribeViews() {
        binding?.apply {
            loginSignin.setOnClickListener {
                viewModel.login()
            }
            typeAndLogin.setOnClickListener {
                typeDefaultLogin()
                viewModel.login()
            }
            loginError.setCloseClickListener {
                viewModel.onCloseError()
            }
            loginEmail.requireEditText().addTextChangedListener(TextWatcherAfterChanged { s ->
                viewModel.onEmailUpdated(s)
            })
            loginPassword.requireEditText().addTextChangedListener(TextWatcherAfterChanged { s ->
                viewModel.onPasswordUpdated(s)
            })
        }
    }

    private fun subscribeBiometric() {
        val biometricManager = BiometricManager.from(requireContext())
        binding?.apply {
            when (biometricManager.canAuthenticate(SUPPORTED_BIO)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    Timber.d("App can authenticate using biometrics.")
                    loginBio.isVisible = true
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Timber.e("No biometric features available on this device.")
                    loginBio.isVisible = false
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Timber.e("Biometric features are currently unavailable.")
                    loginBio.isVisible = false
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    // We will prompt user to set bio on button click
                    loginBio.isVisible = true
                }
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                    Timber.e("Biometric security update required.")
                    loginBio.isVisible = false
                }
                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                    Timber.e("Biometric error unsupported.")
                    loginBio.isVisible = false
                }
                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                    Timber.e("Biometric unknown state.")
                    loginBio.isVisible = false
                }
            }
            loginBio.setOnClickListener {
                when (biometricManager.canAuthenticate(SUPPORTED_BIO)) {
                    BiometricManager.BIOMETRIC_SUCCESS -> {
                        Timber.d("App requesting authenticate using biometrics.")
                        biometricPrompt.authenticate(promptInfo)
                    }
                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        enrollBio()
                    }
                    else -> {
                        Timber.d("Unexpected biometric state.")
                    }
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun enrollBio() {
        val intent: Intent = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                Intent(Settings.ACTION_BIOMETRIC_ENROLL).putExtra(
                    EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, SUPPORTED_BIO
                )
            }
            else -> {
                @Suppress("DEPRECATION") Intent(Settings.ACTION_FINGERPRINT_ENROLL)
            }
        }

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }

}