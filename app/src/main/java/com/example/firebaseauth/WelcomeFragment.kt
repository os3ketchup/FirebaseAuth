package com.example.firebaseauth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.firebaseauth.databinding.FragmentWelcomeBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSendNumberNextWindow.setOnClickListener {
            if (binding.etPhoneNumber.text.toString().isNotEmpty()) {
                MyInfo.phoneNumber = binding.etPhoneNumber.text.toString().trim()


                findNavController().navigate(
                    R.id.sendingCodeFragment,
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Iltimos telefon raqamingizni kiriting",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}