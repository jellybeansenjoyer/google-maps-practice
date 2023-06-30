package com.example.googlemaps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.googlemaps.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    lateinit private var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        if(isServiceOK()){
            mBinding.button2.setOnClickListener {
                startActivity(Intent(this,MapActivity::class.java))
            }
        }
    }
    fun isServiceOK():Boolean{
        Log.e(TAG,"isServicesOk:checking google services version")
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOk: Google Play Services is Working")
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServicesOk: Error Occured but we can fix it")
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,9001)
            dialog.show()
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show()
        }
        return false
    }

}