package com.example.caqao

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.database.RegisterDatabase
import com.example.caqao.database.RegisterEntity
import com.example.caqao.databinding.FragmentRegisterBinding
import com.example.caqao.models.CacaoDetectionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private var checkExistingUser = MutableLiveData<Int>()
    private var any : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        checkExistingUser.observe(viewLifecycleOwner,{
            any = it
            Log.e(String(),it.toString()+"string")
            if(any == 0){
                checkPassword()
            }else{
                Toast.makeText(requireContext(), "Username already exists.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.signUpBtn.setOnClickListener {view: View-> view
            addUser ()

        }

        binding.backBtn.setOnClickListener{
            val intent = Intent(requireActivity(), MainActivity2::class.java)
            startActivity(intent)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.option_menu, menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun addUser() {

        val id = UUID.randomUUID().toString()
        val firstname = binding.registerFirstname.text.toString()
        val lastname = binding.registerLastname.text.toString()
        val email = binding.registerEmail.text.toString()
        val username = binding.registerUsername.text.toString()
        val password = binding.registerPassword.text.toString()
        val confirmpass = binding.registerConfirmpassword.text.toString()

        lifecycleScope.launch {
            val user = RegisterEntity(
                id = id,
                firstname = firstname,
                lastname = lastname,
                email = email,
                username = username,
                password = password,
                confirmpass = confirmpass
            )
            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmpass.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill out all the fields.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
//                val newUser = RegisterEntity(id = id, firstname = firstname, lastname = lastname, email = email, username = username, password = password, confirmpass = confirmpass
//                )
//                val userDao = RegisterDatabase(requireActivity()).getRegisterDatabaseDao()
//                lifecycleScope.launch {
//                    userDao.insert(newUser)
                checkUser()
                }
            }
        }




    private fun checkUser():Boolean{
        val username = binding.registerUsername.text.toString()
        var check = 2
        val jobCheckUser = lifecycleScope.launch(Dispatchers.IO){
            check = RegisterDatabase(requireActivity()).getRegisterDatabaseDao().checkUser(username)
            checkExistingUser.postValue(check)
            Log.e(String(),check.toString())
        }
        lifecycleScope.launch(){
            jobCheckUser.join()
        }
        Log.e(String(),check.toString())
        return (check==1)
    }

    private fun checkPassword(){
        //hashpass
//        val salt = BCrypt.gensalt()
//        val hashedPassword1 = BCrypt.hashpw(password, salt)
//        val hashedPassword2 = BCrypt.hashpw(confirmpass, salt)

        val id = UUID.randomUUID().toString()
        val firstname = binding.registerFirstname.text.toString()
        val lastname = binding.registerLastname.text.toString()
        val email = binding.registerEmail.text.toString()
        Log.e(String(),binding.registerUsername.text.toString())
        val username = binding.registerUsername.text.toString()
        Log.e(String(),username)
        val password = binding.registerPassword.text.toString()
        val confirmpass = binding.registerConfirmpassword.text.toString()


        val user = RegisterEntity(id = id,
            firstname = firstname,
            lastname = lastname,
            email = email,
            username = username,
            password = password,
            confirmpass = confirmpass)

        if (confirmpass != password){
            Toast.makeText(requireContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show()
        }else{
            lifecycleScope.launch{
//                RegisterDatabase(requireActivity()).getRegisterDatabaseDao().insert(user)
                if (!isPasswordValid(binding.registerPassword.text) && !isPasswordValid(binding.registerConfirmpassword.text) ) {
                    binding.registerPassword.error = "Password must contain at least 8 characters."
                    binding.registerConfirmpassword.error = "Password must contain at least 8 characters."
                } else {
                    RegisterDatabase(requireActivity()).getRegisterDatabaseDao().insert(user)
                    binding.registerPassword.error = null // Clear the error
                    binding.registerConfirmpassword.error = null // Clear the error
                    Toast.makeText(requireContext(), "Account successfully created!!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
                }
            }
        }
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }
}