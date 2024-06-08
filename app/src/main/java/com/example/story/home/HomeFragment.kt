package com.example.story.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story.MainActivity
import com.example.story.databinding.FragmentHomeBinding
import com.example.story.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdapter

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        postponeEnterTransition()

        adapter = HomeAdapter { story, imageView, nameView, descView ->

            val id = story.id
            val name = story.name
            val description = story.description
            val photoUrl = story.photoUrl

            val action = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment(
                id, name, description, photoUrl
            )

            findNavController().navigate(action)
        }

        val recyclerView = binding.rvHome
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.rvHome.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.stories.observe(viewLifecycleOwner){ data ->
            if (data != null) {
                adapter.submitData(viewLifecycleOwner.lifecycle , data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navBar.visibility = View.VISIBLE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}