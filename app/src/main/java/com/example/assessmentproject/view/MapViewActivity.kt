package com.example.assessmentproject.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assessmentproject.R
import com.example.assessmentproject.utility.Globals
import com.example.assessmentproject.utility.Util
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapViewActivity : AppCompatActivity() , OnMapReadyCallback{
    private var mMap: GoogleMap? = null
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
    private fun getIntentData(){

    }
    override fun onMapReady(p0: GoogleMap) {
        addMarker(p0)
    }

    private fun addMarker(p0: GoogleMap){
        val g = application as Globals
        mMap = p0
        if (g.getData()?.isNotEmpty() == true && g.getData() != null){
            for (data in g.getData()!!){
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