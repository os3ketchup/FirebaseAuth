package com.example.firebaseauth

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseauth.MyInfo.phoneNumber
import com.example.firebaseauth.databinding.FragmentSendingCodeBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class SendingCodeFragment : Fragment() {

    private var state = ""
    private var code = ""
    private var mVerificationId = ""
    private var _binding: FragmentSendingCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendingCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvReceiveNumber.text = "Bir martalik kod $phoneNumber raqamiga yuborildi"

        sendSMS()



          object : CountDownTimer(60000,1000) {
                override fun onTick(p0: Long) {
                    binding.tvTimer.text = "00:${p0/1000}"
                    if (p0<10000){
                        binding.tvTimer.text = "00:0${p0/1000}"
                    }
                }

                override fun onFinish() {
                    Toast.makeText(requireContext(), "please resend", Toast.LENGTH_SHORT).show()
                    binding.ivResend.setOnClickListener {
                        sendSMS()

                    }
                }
            }.start()


        binding.etSendingCode.addTextChangedListener {
            if (binding.etSendingCode.text.toString().length == 6) {
                MyInfo.code = binding.etSendingCode.text.toString().trim()
                val credential =
                    PhoneAuthProvider.getCredential(MyInfo.mVerificationId, MyInfo.code)
                signInWithPhoneAuthCredential(credential)
            }
        }
        if (state == "success") {
            findNavController().navigate(R.id.finalFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun sendSMS() {
        val options = PhoneAuthOptions.newBuilder(MyInfo.mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            /** it works when app installed into play market**/

            /*  Log.d(TAG, "onVerificationCompleted:$credential")*/
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

            /*Log.w(TAG, "onVerificationFailed", e)*/

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {


            MyInfo.mVerificationId = verificationId

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        MyInfo.mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    state = "success"
                    // Sign in success, update UI with the signed-in user's information
                    findNavController().navigate(R.id.finalFragment)
                    Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "failed ",
                        Toast.LENGTH_LONG
                    ).show()

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            requireContext(),
                            "The verification code entered was invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    sendSMS()
                    // Update UI
                }
            }
    }


}