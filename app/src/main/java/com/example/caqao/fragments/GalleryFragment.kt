package com.example.caqao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.CacaoDetectionListener
import com.example.caqao.CacaoGridAdapter
import com.example.caqao.R
import com.example.caqao.caqaodetail.CacaoDetailFragment
import com.example.caqao.databinding.FragmentGalleryBinding
import com.example.caqao.models.CacaoDetectionViewModel


class GalleryFragment : Fragment() {

    private val sharedViewModel: CacaoDetectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGalleryBinding.inflate(inflater)

        binding.viewModel = sharedViewModel
        sharedViewModel.getCacaoDetections()
        binding.lifecycleOwner = this

        binding.photosGrid.adapter = CacaoGridAdapter(CacaoDetectionListener { cacaoDetectionId ->
            val args = Bundle()
            args.putInt("cacaoDetectionId", cacaoDetectionId)

            val fragment = CacaoDetailFragment()
            fragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment).commit()
        })

        val numberOfSavedImages = sharedViewModel.savedImagesCount.value
        if (numberOfSavedImages != null) {
            // do something with the number of saved images
            if (numberOfSavedImages % 2 == 0) {
                // number of saved images is even
                binding.photosGrid.addItemDecoration(LastTwoItemBottomMarginDecorator(resources.getDimensionPixelSize(R.dimen.fab_margin2)))

            } else {
                // number of saved images is odd
                binding.photosGrid.addItemDecoration(LastItemBottomMarginDecorator(resources.getDimensionPixelSize(R.dimen.fab_margin2)))
            }
        } else {
            // handle the case when the number of saved images is null
        }



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedViewModel.savedImagesCount.observe(viewLifecycleOwner) { count ->
            // Update UI with the count of saved images
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomeFragment()).commit()
                (activity as AppCompatActivity).supportActionBar?.title = "Home"
                val botnav = requireActivity().findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
                botnav.show(R.id.homeFragment, true)
            }
        })
        val view = requireActivity().findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        view.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): GalleryFragment{
            return GalleryFragment()
        }
    }

}