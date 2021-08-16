package com.example.assessmentproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assessmentproject.R
import com.example.assessmentproject.model.Data
import com.example.assessmentproject.utility.Config
import com.example.assessmentproject.utility.Globals
import com.example.assessmentproject.utility.Util
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MapViewActivity : AppCompatActivity() , OnMapReadyCallback{
    private var mMap: GoogleMap? = null
    private var dateList: ArrayList<Data>? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)
        supportActionBar?.hide()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setupMapView()
        getIntentData()
    }
    private fun setupMapView(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapViewFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }
    //get data from MainActivity from Gson
    private fun getIntentData(){
        val g = application as Globals
        dateList = g.getData()
        /*if (intent.hasExtra(Config.MAPDATE)){
            val content = intent.extras
            val type: Type = object : TypeToken<ArrayList<Data>?>() {}.type
            dateList = Gson().fromJson<ArrayList<Data>?>(
                content?.getString(Config.MAPDATE),
                type
            )
        }*/
    }
    override fun onMapReady(p0: GoogleMap) {
        addMarker(p0)
    }

    private fun addMarker(p0: GoogleMap){
        mMap = p0
        if (dateList?.isNotEmpty() == true){
            for (data in dateList!!){
                mMap?.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            data.job.report_at_address.geo.lat,
                            data.job.report_at_address.geo.lon
                        )
                    ).title(
                        data.job.title + "\n" + Util.getFormatDate(data.starts_at) + " - " + Util.getFormatDate(
                            data.ends_at
                        )
                    )
                )
                mMap?.animateCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            data.job.report_at_address.geo.lat,
                            data.job.report_at_address.geo.lon
                        )
                    )
                )
            }
        }
        //mMap?.setMaxZoomPreference(12f)
    }

}