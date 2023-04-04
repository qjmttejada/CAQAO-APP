package com.example.caqao.caqaodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.caqao.R
import com.example.caqao.databinding.FragmentCacaoDetailBinding



class CacaoDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentCacaoDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cacao_detail, container, false)

        val viewModelFactory = arguments?.getInt("cacaoDetectionId")
            ?.let { CacaoDetailViewModelFactory(it) }

        // Get a reference to the ViewModel associated with this fragment.
        val cacaoDetailViewModel =
            viewModelFactory?.let {
                ViewModelProvider(
                    this, it
                ).get(CacaoDetailViewModel::class.java)
            }

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.viewModel = cacaoDetailViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }

}