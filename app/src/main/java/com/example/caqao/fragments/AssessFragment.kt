package com.example.caqao.fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.R
import com.example.caqao.databinding.FragmentAssessBinding
import com.example.caqao.models.CacaoDetectionViewModel


class AssessFragment : Fragment() {

    private var binding: FragmentAssessBinding? = null
    private val sharedViewModel: CacaoDetectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentAssessBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            assessBtn.setOnClickListener {
                assessCacaoBeans()
            }
        }

        sharedViewModel.validateImage(requireContext(), requireContext().contentResolver)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })

        val botnav = requireActivity().findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        botnav.visibility = View.GONE
    }



    fun assessCacaoBeans() {
        when {
            TextUtils.isEmpty(binding?.beanCountInputEditText?.text.toString()) -> {
                binding?.beanCountInputEditText?.error = "Required field is empty"
                Toast.makeText(
                    requireContext(),
                    "Please input bean count",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                val beanSize = binding?.beanCountInputEditText?.text.toString().toInt()
                sharedViewModel.assessCacaoBeans(requireContext(), requireContext().contentResolver,
                    beanSize)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, ResultsFragment()).commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}