package com.effective.login.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.effective.login.R
import com.effective.login.databinding.FragmentVerifyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!

    private val args: VerifyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sentTo.text = resources.getString(R.string.sent_to, args.email)
        binding.pinView.addTextChangedListener {
            binding.verifyButton.isEnabled = binding.pinView.length() == 4
        }

        binding.verifyButton.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.effective.main/mainFragment".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}