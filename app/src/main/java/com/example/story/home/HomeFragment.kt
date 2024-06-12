package com.example.story.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.story.MainActivity
import com.example.story.R
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
//        postponeEnterTransition()

        adapter = HomeAdapter { story, imageView, nameView, descView ->
            val id = story.id
            val name = story.name
            val description = story.description
            val photoUrl = story.photoUrl

            val extras = FragmentNavigatorExtras(
                imageView to imageView.transitionName,
                nameView to nameView.transitionName,
                descView to descView.transitionName
            )

            val action = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment(
                id, name, description, photoUrl
            )
            Log.d("HomeFragment", "Navigating to DetailStoryFragment with id: $id")
//            findNavController().navigate(action)

            findNavController()
                .navigate(action,
                    FragmentNavigator.Extras.Builder()
                        .addSharedElements(
                            mapOf(
                                imageView to imageView.transitionName,
                                nameView to nameView.transitionName,
                                descView to descView.transitionName,
                            )
                        ).build()
                )
        }

        val recyclerView = binding.rvHome
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.rvHome.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_top)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_bottom)

        homeViewModel.stories.observe(viewLifecycleOwner){ data ->
            if (data != null) {
                adapter.submitData(viewLifecycleOwner.lifecycle , data)
                Log.d("HomeFragment", "Data loaded: ${data} items")
            }else{
                Log.d("HomeFragment", "No data loaded")
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "Resuming HomeFragment")
        (activity as MainActivity).navBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HomeFragment", "Destroying HomeFragment view")
        _binding = null
    }

    override fun onPause() {
        super.onPause()
    }
}
