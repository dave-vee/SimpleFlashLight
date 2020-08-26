package com.example.flashlightapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var btn: ImageButton
    private lateinit var torchStatus: TextView
    var isOn: Boolean = false
    private val PERMISSION_CODE = 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.torch_btn)
        torchStatus = findViewById(R.id.tvStatus)

        btn.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                setupPermissions()
//                val toast = Toast.makeText(this, "Permissions not setup", Toast.LENGTH_LONG)
//                toast.show()
            } else {
                turnOn()
            }
        }
    }

    private fun turnOn() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        if (!isOn) {
            try {
                cameraManager.setTorchMode(cameraId, true)
//                btn!!.setImageDrawable(getDrawable(R.drawable.on_icon))
                tvStatus!!.text = "ON"
                isOn = true

            } catch (e: CameraAccessException) {
            }
        } else {
            try {
                cameraManager.setTorchMode(cameraId, false)
//                btn!!.setImageDrawable(getDrawable(R.drawable.off_icon))
                tvStatus!!.text = "OFF"
                isOn = false
            } catch (e: CameraAccessException) {
            }
        }

    }


    private fun setupPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_CODE
        )
    }


}