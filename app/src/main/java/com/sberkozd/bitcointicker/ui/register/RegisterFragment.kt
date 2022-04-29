package com.sberkozd.bitcointicker.ui.register

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sberkozd.bitcointicker.Event
import com.sberkozd.bitcointicker.MainViewModel
import com.sberkozd.bitcointicker.R
import com.sberkozd.bitcointicker.databinding.FragmentRegisterBinding
import com.sberkozd.bitcointicker.observeInLifecycle
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null
    protected var currentVideoPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val uri = Uri.parse("android.resource://" + context?.packageName + "/" + R.raw.intro)

        binding.videoView.apply {
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

        binding.btnRegister.setOnClickListener {

            registerViewModel.onRegisterButtonClicked(
                binding.edtUser.text.toString(),
                binding.edtPassword.text.toString()
            )
        }

        registerViewModel.eventsFlow.onEach {
            when (it) {
                is Event.Register.ShowEmailEmptyToast -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Email is empty!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Register.ShowNoEmailError -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Email is not valid!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Register.ShowPasswordEmptyToast -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Password is empty!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Register.ShowPasswordTooShortToast -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Password must be longer than 6 digits!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.WARNING,
                        false
                    ).show()
                }
                is Event.Register.PromptRegister -> {

                    val email: String = binding.edtUser.text.toString().trim { it <= ' ' }
                    val password: String = binding.edtPassword.text.toString().trim { it <= ' ' }

                    registerViewModel.register(email, password)


                }
                Event.Register.FailRegister -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Signup failed!", FancyToast.LENGTH_LONG, FancyToast.WARNING, false
                    ).show()
                }
                is Event.Register.SuccessfulRegister -> {
                    FancyToast.makeText(
                        requireContext(),
                        "Registered as ${it.email}",
                        FancyToast.LENGTH_LONG,
                        FancyToast.SUCCESS,
                        false
                    ).show()

                    findNavController().navigate(
                        RegisterFragmentDirections.actionRegisterFragmentToHomeFragment(true)
                    )
                }
                Event.HideLoading -> mainViewModel.hideLoading()
                Event.ShowLoading -> mainViewModel.showLoading()
            }
        }.observeInLifecycle(viewLifecycleOwner)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}