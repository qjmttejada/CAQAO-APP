package com.example.caqao

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.caqao.databinding.FragmentLoginBinding
import com.example.caqao.models.CacaoDetectionViewModel
import com.example.caqao.models.USER_TOKEN
import com.example.caqao.models.USER_TOKEN_STATUS


class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val sharedViewModel: CacaoDetectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.lifecycleOwner = this
        binding?.viewModel = sharedViewModel

        binding!!.signInBtn.setOnClickListener {
            val username = binding!!.loginUsername.text.toString()
            val password = binding!!.loginPassword.text.toString()

            // TODO: verify if fields contain valid values
            loginUser(username, password)
        }

        binding?.backBtn?.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity2::class.java)
            startActivity(intent)
        }
    }

    fun loginUser(username: String, password: String) {

        var authenticated = sharedViewModel.loginUser(username, password)

        Log.d("Authenticated?", "${authenticated}")

        if (authenticated) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(
                requireContext(),
                "Invalid Username or Password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}