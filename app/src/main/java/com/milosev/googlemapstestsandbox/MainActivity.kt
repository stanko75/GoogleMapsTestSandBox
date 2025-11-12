package com.milosev.googlemapstestsandbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.milosev.googlemapstestsandbox.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var kmlUpdateJob: Job? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mainActivity = this;
        var isStarted = false;

        binding.btnLoadKml.setOnClickListener {
            isStarted = !isStarted

            if (isStarted) {
                binding.btnLoadKml.text = "Stop"
                val loadKml = LoadKml()
                kmlUpdateJob = lifecycleScope.launch {
                    loadKml.execute(mainActivity, map, binding.etKmlUrl.text.toString())
                }
            }
            else {
                kmlUpdateJob?.cancel()
                kmlUpdateJob = null
                binding.btnLoadKml.text = "Start"
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val tunis = LatLng(35.7607919, 10.7537573)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tunis, 8f))
    }
}