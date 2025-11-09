package com.milosev.googlemapstestsandbox

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.net.URL

class LoadKml {
    fun execute(activity: Context, map: GoogleMap) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url =
                    URL("https://www.milosev.com/gallery/allWithPics/travelBuddies/tunis/kml/kml.kml")
                val connection = url.openConnection()
                connection.connectTimeout = 15000
                connection.readTimeout = 60000

                val tempFile = File(activity.cacheDir, "temp.kml")
                connection.getInputStream().use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                withContext(Dispatchers.Main) {
                    val layer = KmlLayer(map, FileInputStream(tempFile), activity)
                    layer.addLayerToMap()
                    Toast.makeText(activity, "KML Loaded", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}