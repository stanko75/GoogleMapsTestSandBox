package com.milosev.googlemapstestsandbox

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.net.URL

class LoadKml {
    fun execute(activity: Context, googleMap: GoogleMap) {

        val kmlClient = CreateRetrofitBuilder().createRetrofitBuilder("https://www.milosev.com/")
            .create(IGetKml::class.java)

        CoroutineScope(Dispatchers.Main).launch {

            try {
                val webApiRequest =
                    kmlClient.getKml("https://www.milosev.com/gallery/allWithPics/travelBuddies/tunis/kml/kml.kml");

                if (webApiRequest.isSuccessful) {
                    val bytes = webApiRequest.body()?.bytes()
                    if (bytes != null) {
                        val input = ByteArrayInputStream(bytes)
                        val kmlLayer = KmlLayer(googleMap, input, activity)
                        kmlLayer.addLayerToMap()
                        Log.i("KML", "Loaded successfully")
                    }
                } else {
                    Log.e("KML", "Error: ${webApiRequest.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}