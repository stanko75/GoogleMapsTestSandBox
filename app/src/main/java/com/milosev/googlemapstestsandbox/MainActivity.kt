package com.milosev.googlemapstestsandbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.milosev.googlemapstestsandbox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnLoadKml.setOnClickListener {
            val loadKml = LoadKml()
            loadKml.execute(this, map)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val tunis = LatLng(36.8065, 10.1815)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tunis, 8f))
    }
}