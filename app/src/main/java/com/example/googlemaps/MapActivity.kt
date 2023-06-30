package com.example.googlemaps

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapActivity : AppCompatActivity() {

    private var mLocationGranted= false
    lateinit private var mMap:GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        getLocationPermission()
    }
    fun getLocationPermission(){
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        if(ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mLocationGranted = true
            }else{
                ActivityCompat.requestPermissions(this@MapActivity,permissions,1234)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mLocationGranted = false
        when(requestCode){
            1234->{
                if(grantResults.size>0 ){
                    for (i in 0..grantResults.size){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationGranted = false
                            return
                        }
                    }
                    mLocationGranted = true
                    initMap()
                }
            }
        }
    }
    private fun initMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(p0: GoogleMap?) {
                Toast.makeText(this@MapActivity, "Map is ready", Toast.LENGTH_SHORT).show()
                mMap = p0!!
            }
        })
    }
}