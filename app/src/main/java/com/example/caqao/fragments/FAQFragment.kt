package com.example.caqao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.caqao.R


class FAQFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<RecyclerViewData>()
    private lateinit var adapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_f_a_q, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        addDataToList()
        adapter = RecyclerViewAdapter(mList)
        recyclerView.adapter = adapter

        val margin = resources.getDimensionPixelSize(R.dimen.fab_margin)
        val decorator = LastItemMarginDecorator(margin)
        recyclerView.addItemDecoration(decorator)



        return view
    }

    private fun addDataToList() {
        mList.add(
            RecyclerViewData(
                "What is CAQAO?",
                context?.getString(R.string.answer1) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How are cacao beans classified?",
                context?.getString(R.string.answer2) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How are cacao beans graded?",
                context?.getString(R.string.answer3) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How to capture images?",
                context?.getString(R.string.answer4) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How to upload images?",
                context?.getString(R.string.answer5) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How to sign up?",
                context?.getString(R.string.answer6) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How to sign in?",
                context?.getString(R.string.answer7) ?: String()
            )
        )
        mList.add(
            RecyclerViewData(
                "How to view captured images?",
                context?.getString(R.string.answer8) ?: String()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
            }

        })
        val view = requireActivity().findViewById<MeowBottomNavigation>(R.id.bottomNavigation)

        view.visibility = View.GONE
    }
}