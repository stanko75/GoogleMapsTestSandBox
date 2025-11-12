package com.milosev.googlemapstestsandbox

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.net.URL

class LoadKml {

    private var kmlLayer: KmlLayer? = null

    suspend fun execute(activity: Context, googleMap: GoogleMap, strUrl: String) {

        val url = URL(strUrl)
        val kmlClient = CreateRetrofitBuilder().createRetrofitBuilder("${url.protocol}://${url.host}/")
            .create(IGetKml::class.java)

        withContext(Dispatchers.IO) {
            while (isActive)
            {
                try {
                    val webApiRequest =
                        kmlClient.getKml(strUrl);

                    if (webApiRequest.isSuccessful) {
                        val bytes = webApiRequest.body()?.bytes()
                        if (bytes != null) {

                            val input = ByteArrayInputStream(bytes)

                            withContext(Dispatchers.Main) {
                                kmlLayer?.removeLayerFromMap()
                                Log.i("KML", "Removed successfully")

                                kmlLayer = KmlLayer(googleMap, input, activity)
                                kmlLayer?.addLayerToMap()
                                Log.i("KML", "Loaded successfully")
                            }
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
}