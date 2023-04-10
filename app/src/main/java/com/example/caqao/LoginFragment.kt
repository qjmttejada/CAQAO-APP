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
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.caqao.database.RegisterDatabase
import com.example.caqao.database.RegisterEntity
import com.example.caqao.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var checkExistingAccount = MutableLiveData<List<RegisterEntity>>()
    private var userExist = emptyList<RegisterEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        binding.signInBtn.setOnClickListener {
            checkAccount()
        }

        binding.backBtn.setOnClickListener{
            val intent = Intent(requireActivity(), MainActivity2::class.java)
            startActivity(intent)
        }


        checkExistingAccount.observe(viewLifecycleOwner) {
            userExist = it
            when{
                TextUtils.isEmpty(binding.loginUsername.text.toString().trim()) ->{
                    binding.loginUsername.error = "Required field is empty"
                }
                TextUtils.isEmpty(binding.loginPassword.text.toString().trim()) ->{
                    binding.loginPassword.error = "Required field is empty"
                }
                else->{
                    if (userExist.isNotEmpty()) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

        return binding.root
    }

    private fun checkAccount():List<RegisterEntity>{
        val username = binding.loginUsername.text.toString()
        val password = binding.loginPassword.text.toString()

        //hashpass
//        val salt = user.password.substring(0,16)
//        val hashedPassword = BCrypt.hashpw(password, salt)

        var check = emptyList<RegisterEntity>()
        val jobCheckUser = lifecycleScope.launch(Dispatchers.IO){
            check = RegisterDatabase(requireActivity()).getRegisterDatabaseDao().checkAccount(username , password)
            checkExistingAccount.postValue(check)
            Log.e(String(),check.toString())
        }
        lifecycleScope.launch(){
            jobCheckUser.join()
        }
        Log.e(String(),check.toString())
        return (check)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }
}