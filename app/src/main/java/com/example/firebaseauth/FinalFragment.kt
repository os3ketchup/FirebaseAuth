package com.example.firebaseauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebaseauth.databinding.FragmentFinalBinding
import com.example.firebaseauth.databinding.FragmentWelcomeBinding


class FinalFragment : Fragment() {
    private var _binding: FragmentFinalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNumber.text = MyInfo.phoneNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}