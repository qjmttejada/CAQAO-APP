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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.caqao.databinding.FragmentRegisterBinding
import com.example.caqao.models.CacaoDetectionViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null
    private val sharedViewModel: CacaoDetectionViewModel by activityViewModels()

//    private var validatefName () {
//        String val firstName = binding.registerFirstname.text.get().toString()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.viewModel = sharedViewModel
        binding!!.lifecycleOwner = viewLifecycleOwner


        binding?.signUpBtn?.setOnClickListener {


            val firstName = binding?.registerFirstname?.text.toString()
            val lastName = binding?.registerLastname?.text.toString()
            val email = binding?.registerEmail?.text.toString()
            val username = binding?.registerUsername?.text.toString()
            val password = binding?.registerPassword?.text.toString()
            val cpassword = binding?.registerConfirmpassword?.text.toString()


            // TODO: check if all fields contain valid values
            when {
                TextUtils.isEmpty(firstName.trim()) -> {
                    binding?.registerFirstname?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(lastName.trim()) -> {
                    binding?.registerLastname?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(email.trim()) -> {
                    binding?.registerEmail?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(username.trim()) -> {
                    binding?.registerUsername?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(password.trim()) -> {
                    binding?.registerPassword?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(cpassword.trim()) -> {
                    binding?.registerConfirmpassword?.error = "Required field is empty"
                    Toast.makeText(
                        requireContext(),
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty()) {
                        binding!!.registerFirstname.error = null // Clear the error
                        binding!!.registerLastname.error = null // Clear the error
                        binding!!.registerEmail.error = null // Clear the error
                        binding!!.registerUsername.error = null // Clear the error
                        binding!!.registerPassword.error = null // Clear the error
                        binding!!.registerConfirmpassword.error = null // Clear the error

                        Toast.makeText(
                            requireContext(),
                            "Account Successfully Created!",
                            Toast.LENGTH_SHORT
                        ).show()
                        sharedViewModel.registerUser(firstName, lastName, email, username, password)
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
                    }
                }
            }
        }

        binding?.backBtn?.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity2::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}