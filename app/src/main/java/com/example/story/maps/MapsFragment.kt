package com.example.story.maps

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.story.R
import com.example.story.databinding.FragmentMapsBinding
import com.example.story.network.response.StoryItem
import com.example.story.utils.Result
import com.example.story.utils.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private val mapsViewModel: MapsViewModel by viewModels {
        ViewModelFactory(requireActivity())
    }

    override fun onMapReady(googleMap: GoogleMap){
        Log.d("MapsFragment", "Map is ready")
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isCompassEnabled = true

        this.googleMap = googleMap

        mapsViewModel.getStoriesWithLocation().observe(this){result->
            if (result != null) {
                when(result){
                    is Result.Error -> {
                        showLoading(false)
                        Log.d("MapsFragment", "Error")
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_LONG).show()
                    }
                    is Result.Loading -> {
                        Log.d("MapsFragment", "On Loading")
                        showLoading(true)
                    }
                    is Result.Success ->{
                        showLoading(false)
                        Log.d("MapsFragment", "Get Story Data Location")
                        val stories = result.data.listStory
                        addManyMarker(stories)
                    }
                }
            }
        }
        Log.d("MapsFragment", "View MapsFragment")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        Log.d("MapsFragment", "Map fragment initialized")
    }

    private fun addManyMarker(stories: List<StoryItem>) {
        val boundsBuilder = LatLngBounds.Builder()
        Log.d("MapsFragment", "boundsBuilder: $boundsBuilder")

        stories.forEach { story ->
            val latLng = LatLng(story.lat, story.lon)
            googleMap.addMarker(MarkerOptions().position(latLng).title(story.name))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private fun showLoading(bool : Boolean){
        binding.progressBar.isVisible = bool
    }
}