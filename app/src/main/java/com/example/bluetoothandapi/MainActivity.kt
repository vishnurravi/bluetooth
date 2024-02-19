package com.example.bluetoothandapi

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.bluetoothandapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var binding: ActivityMainBinding
    private val Request_code_enable: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.pairWithNewDevices.setOnClickListener {
            pairWithNewDevices()
        }
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            binding.bluetoth.setText("Bluetooth is not available")

        } else {
            binding.bluetoth.setText("Bluetooth is available")

        }
        if (bluetoothAdapter.isEnabled) {
            binding.newimage.setImageResource(R.drawable.baseline_bluetooth_24)
        } else {
            binding.newimage.setImageResource(R.drawable.baseline_bluetooth_disabled_24)
        }
        binding.turnon.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                Toast.makeText(this, "Bluetooth is already  on", Toast.LENGTH_SHORT).show()
            } else {
                var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    startActivityForResult(intent, Request_code_enable)


            }
        }
        binding.turnoff.setOnClickListener {
            if (!bluetoothAdapter.isEnabled) {
                Toast.makeText(this, "Bluetooth is already off", Toast.LENGTH_SHORT).show()

            } else {
                bluetoothAdapter.disable()
                Toast.makeText(this, "Bluetooth is off", Toast.LENGTH_SHORT).show()

            }
        }
        binding.discoveralabe.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                )
                    startActivityForResult(intent, Request_code_enable)


            }


        }
        binding.getpaired.setOnClickListener {
            if (bluetoothAdapter.isEnabled) {
                val device = bluetoothAdapter.bondedDevices
                for (dev in device) {
                    val devname = dev.name
                    val devadd = device
                    binding.getpairedtext.append("\n devname:$devname,add:$devadd")

                }
            } else {


            }


        }
    }



    private fun pairWithNewDevices() {
        if (bluetoothAdapter.isEnabled) {
            startBluetoothDiscovery()
        } else {
            showToast("Bluetooth is not enabled")
        }
    }

    private fun startBluetoothDiscovery() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        )
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }
        bluetoothAdapter.startDiscovery()
        showToast("Scanning for nearby devices...")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    }
//override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    when(requestCode){
//        Request_code_enable->
//            if (resultCode==Activity.RESULT_OK)
//                Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT).show()
//
//    }
//    super.onActivityResult(requestCode, resultCode, data)
//
//}
