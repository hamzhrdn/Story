package com.example.story.detailstory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.story.R
import com.example.story.databinding.FragmentDetailStoryBinding
import com.example.story.databinding.FragmentHomeBinding
import com.example.story.network.response.StoryItem
import com.squareup.picasso.Picasso

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    private val args: DetailStoryFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        Picasso.get().load(args.photoUrl).into(binding.ivImage)
        binding.tvTitle.text = args.name
        binding.tvDescription.text = args.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}