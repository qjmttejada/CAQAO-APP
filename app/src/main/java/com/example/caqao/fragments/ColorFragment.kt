package com.example.caqao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.R
import com.example.caqao.databinding.FragmentColorBinding

class ColorFragment : Fragment() {
    private lateinit var binding: FragmentColorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentColorBinding.inflate(layoutInflater)

        binding.brownImgButton.setOnClickListener {
            val showBrownDialog = DialogFragment()
            val args = Bundle()
            args.putInt("layoutId", 5)
            showBrownDialog.arguments = args
            showBrownDialog.show((activity as AppCompatActivity).supportFragmentManager,"showBrownPopUp")

            Toast.makeText(activity, "Swipe left to view the next page", Toast.LENGTH_SHORT).show()
        }

        binding.partlypurpleImgButton.setOnClickListener {
            val showInsectDialog = DialogFragment()
            val args = Bundle()
            args.putInt("layoutId", 6)
            showInsectDialog.arguments = args
            showInsectDialog.show((activity as AppCompatActivity).supportFragmentManager,"showPartlyPurplePopUp")
        }

        binding.totalpurpleImgButton.setOnClickListener {
            val showSlateDialog = DialogFragment()
            val args = Bundle()
            args.putInt("layoutId", 7)
            showSlateDialog.arguments = args
            showSlateDialog.show((activity as AppCompatActivity).supportFragmentManager,"showTotalPurplePopUp")
        }

        binding.verydarkbrownImgButton.setOnClickListener {
            val showGerminatedDialog = DialogFragment()
            val args = Bundle()
            args.putInt("layoutId", 8)
            showGerminatedDialog.arguments = args
            showGerminatedDialog.show((activity as AppCompatActivity).supportFragmentManager,"showVeryDarkBrownPopUp")
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomeFragment()).commit()
                (activity as AppCompatActivity).supportActionBar?.title = "Home"
            }

        })
        val view = requireActivity().findViewById<MeowBottomNavigation>(R.id.bottomNavigation)

        view.visibility = View.GONE

        (activity as AppCompatActivity).supportActionBar?.title = "Color"
    }



}