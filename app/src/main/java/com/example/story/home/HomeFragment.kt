package com.example.story.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story.R
import com.example.story.databinding.FragmentHomeBinding
import com.example.story.network.response.StoryItem
import com.example.story.utils.ViewModelFactory
import kotlinx.coroutines.launch

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
            // Handle item click
        }

        val recyclerView = binding.rvHome
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun submitDataToAdapter(data: PagingData<StoryItem>) {
        lifecycleScope.launch {
            adapter.submitData(data)
        }
    }
}