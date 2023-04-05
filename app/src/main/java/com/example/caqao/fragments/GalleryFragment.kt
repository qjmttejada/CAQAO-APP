package com.example.caqao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.caqao.CacaoDetectionListener
import com.example.caqao.CacaoGridAdapter
import com.example.caqao.R
import com.example.caqao.caqaodetail.CacaoDetailFragment
import com.example.caqao.databinding.FragmentGalleryBinding
import com.example.caqao.models.CacaoDetectionViewModel


class GalleryFragment : Fragment() {

    private var binding: FragmentGalleryBinding? = null
    private val sharedViewModel: CacaoDetectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gallery, container, false)
        val binding = FragmentGalleryBinding.inflate(inflater)

        binding.viewModel = sharedViewModel
        sharedViewModel.getCacaoDetections()
        binding.lifecycleOwner = this

        binding.photosGrid.adapter = CacaoGridAdapter(CacaoDetectionListener { cacaoDetectionId ->
            // Toast.makeText(context, "${cacaoDetectionId}", Toast.LENGTH_SHORT).show()

            val args = Bundle()
            args.putInt("cacaoDetectionId", cacaoDetectionId)

            val fragment = CacaoDetailFragment()
            fragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment).commit()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }
        })
    }

    companion object {
        fun newInstance(): GalleryFragment{
            return GalleryFragment()
        }
    }

}