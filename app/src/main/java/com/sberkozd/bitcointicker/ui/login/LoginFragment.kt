package com.sberkozd.bitcointicker.ui.login

import android.app.Activity
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.R
import com.sberkozd.bitcointicker.MainViewModel
import com.sberkozd.bitcointicker.databinding.FragmentLoginBinding
import com.sberkozd.bitcointicker.observeInLifecycle
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private var videoView: VideoView? = null
    private var mediaPlayer: MediaPlayer? = null
    protected var currentVideoPosition: Int = 0

    private val loginViewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentComponentManager.findActivity(view.context) as Activity

        videoView = binding.videoView

        val uri = Uri.parse("android.resource://" + context?.packageName + "/" + R.raw.intro)

        videoView?.apply {
            setVideoURI(uri)
            setOnPreparedListener { mp ->
                mediaPlayer = mp?.apply {
                    isLooping = true

                    if (currentVideoPosition != 0) {
                        seekTo(currentVideoPosition)
                        start()
                    }
                }

            }
            start()
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.onLoginButtonClicked(
                binding.edtUser.toString(),
                binding.edtPassword.toString()
            )
        }

        binding.tvSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.edtUser.setText("berk@berk.com")
        binding.edtPassword.setText("berk123")

        loginViewModel.toastString.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.eventsFlow.onEach {
            when (it) {
                is Event.Login.ShowEmailEmptyToast -> {
                    findNavController().popBackStack()
                    FancyToast.makeText(
                        requireContext(),
                        "Username is empty!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Login.ShowPasswordEmptyToast -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Password is empty!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Login.PromptLogin -> {

                    val email : String = binding.edtUser.text.toString().trim{ it <= ' ' }
                    val password : String = binding.edtPassword.text.toString().trim{ it <= ' ' }

                    loginViewModel.login(email,password)


                }
                Event.Login.FailedLogin -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Login failed!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Login.SuccessfulLogin -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Logged in as ${it.email}", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false
                    ).show()

                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToHomeFragment(true)
                    )
                }
                Event.HideLoading -> mainViewModel.hideLoading()
                Event.ShowLoading -> mainViewModel.showLoading()
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    override fun onPause() {
        super.onPause()

        currentVideoPosition = mediaPlayer?.currentPosition ?: 0
        videoView?.pause()
    }

    override fun onResume() {
        super.onResume()

        videoView?.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer?.release()
    }

}