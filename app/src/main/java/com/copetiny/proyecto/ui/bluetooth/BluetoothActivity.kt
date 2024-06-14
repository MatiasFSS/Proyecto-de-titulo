package com.copetiny.proyecto.ui.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.navArgs
import com.copetiny.proyecto.R
import com.copetiny.proyecto.databinding.ActivityBluetoothBinding
import com.copetiny.proyecto.ui.detailscan.ScanDetailActivity
import com.copetiny.proyecto.ui.profile.SharedViewModel
import com.copetiny.proyecto.ui.register.PrefsUsers
import com.copetiny.proyecto.ui.scan.ScanFragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import java.io.IOException
import java.util.*

const val REQUEST_ENABLE_BT = 1 // Constante para identificar la solicitud de habilitación de Bluetooth
@AndroidEntryPoint
class BluetoothActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBluetoothBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    lateinit var mBtAdapter: BluetoothAdapter // Adaptador Bluetooth
    var mAddressDevice: ArrayAdapter<String>? = null // Adapter para almacenar las direcciones de los dispositivos
    var mNameDevices: ArrayAdapter<String>? = null // Adapter para almacenar los nombres de los dispositivos
    var flagDialog = false
    private lateinit var prefsUsers: PrefsUsers

    companion object {
        private var m_bluetoothSocket: BluetoothSocket? = null // Socket Bluetooth para la conexión
        var m_isConnected: Boolean = false // Bandera para el estado de la conexión
        lateinit var m_address: String // Dirección del dispositivo Bluetooth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefsUsers = PrefsUsers(this)

        if (arePermissionsGranted()) {
            initBluetooth()
        } else {
            requestBluetoothPermissions()
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun requestBluetoothPermissions() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        ActivityCompat.requestPermissions(this, permissions, REQUEST_ENABLE_BT)
    }

    private fun arePermissionsGranted(): Boolean {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, getString(R.string.Bluetooth_permissions), Toast.LENGTH_SHORT).show()
                initBluetooth()
            } else {
                Toast.makeText(this, getString(R.string.Bluetooth_noPermissions), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun initBluetooth() {
        mBtAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

        if (mBtAdapter == null) {
            Toast.makeText(this, getString(R.string.Bluetooth_BTnodisponible), Toast.LENGTH_LONG).show()
            binding.tvStateBluetooth.text = getString(R.string.Bluetooth_BTnodisponible)
            binding.pbBluetooth.isVisible = false
            finish()
            return
        }

        setupUI()

        if (!mBtAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            updateBluetoothState()
        }
    }

    private fun setupUI() {
        ivBackBT()

        val type = intent.getStringExtra("type")
        val json = JSONObject(type)
        val title = json.getString("nombre_material")
        val description = json.getString("descripcion")
        val imagen = json.getString("imagen")

        binding.tvTitleScanBluetooth.text = title
        binding.tvDesciptionScanBluetooth.text = description
        Picasso.get().load(imagen).into(binding.ivScanBluetooth)

        mAddressDevice = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        mNameDevices = ArrayAdapter(this, android.R.layout.simple_list_item_1)

        binding.btnActivarBluetooth.setOnClickListener {
            if (mBtAdapter.isEnabled) {
                Toast.makeText(this, getString(R.string.Bluetooth_Activado), Toast.LENGTH_LONG).show()
                updateBluetoothState()
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
        }

        binding.btnDispositivosBT.setOnClickListener {
            if (mBtAdapter.isEnabled) {
                mAddressDevice!!.clear()
                mNameDevices!!.clear()

                val pairedDevices: Set<BluetoothDevice>? = mBtAdapter.bondedDevices
                pairedDevices?.forEach { device ->
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address
                    mAddressDevice!!.add(deviceHardwareAddress)
                    mNameDevices!!.add(deviceName)
                }

                binding.spListaBluetooth.adapter = mNameDevices
                //mBtAdapter.startDiscovery()
            } else {
                val noDevices = "Ningún dispositivo pudo ser emparejado"
                mAddressDevice!!.add(noDevices)
                mNameDevices!!.add(noDevices)
                Toast.makeText(this, getString(R.string.Bluetooth_vicular), Toast.LENGTH_LONG).show()
            }
        }

        binding.btnConectarBT.setOnClickListener {
            if (mBtAdapter.isEnabled && binding.spListaBluetooth.selectedItemPosition != AdapterView.INVALID_POSITION) {
                val intValSpin = binding.spListaBluetooth.selectedItemPosition
                m_address = mAddressDevice!!.getItem(intValSpin).toString()
                Log.i("BluetoothActivity1", "Enviando comando de reciclaje al dispositivo: $m_address")

                try {
                    val pairedDevices: Set<BluetoothDevice>? = mBtAdapter.bondedDevices
                    val device: BluetoothDevice? = pairedDevices?.find {
                        Log.i("BluetoothActivity2", it.address)
                        it.address == m_address
                    }

                    if (device != null) {
                        m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(device.uuids[0].uuid)
                        m_bluetoothSocket!!.connect()
                        //Log.i("BluetoothActivity4", m_bluetoothSocket!!.connect().toString())
                        //Toast.makeText(this, getString(R.string.Bluetooth_conexionExito), Toast.LENGTH_LONG).show()
                        Toast.makeText(this, "Conexión: ${m_bluetoothSocket!!.remoteDevice.address}", Toast.LENGTH_LONG).show()
                        binding.tvStateBluetooth.text = getString(R.string.Bluetooth_Reciclando)
                        binding.pbBluetooth.isVisible = true
                        sendRecycleCommand()

                    } else {
                        throw IOException("Device not found")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, getString(R.string.Bluetooth_conexionNoExito), Toast.LENGTH_LONG).show()
                    binding.tvStateBluetooth.text = getString(R.string.Bluetooth_NoReciclando)
                    binding.pbBluetooth.isVisible = false
                    finish()

                }
            }
        }
    }

    private fun updateBluetoothState() {
        binding.tvStateBluetooth.text = getString(R.string.Bluetooth_BtnActualizar)
        binding.pbBluetooth.isVisible = true
    }

    private fun showDialogLevel(flag: Boolean) {
        val type = intent.getStringExtra("type")
        val json = JSONObject(type)
        val title = json.getString("nombre_material")
        val imagen = json.getString("imagen")

        val dialog = Dialog(this)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.item_dialog_bluetoothdetail)
        dialog.setCanceledOnTouchOutside(false)

        val btnConfirmBT = dialog.findViewById<Button>(R.id.btnConfirmBT)
        val tvTitleItemBT = dialog.findViewById<TextView>(R.id.tvTitleItemBluetooth)
        val ivItemBT = dialog.findViewById<ImageView>(R.id.ivItemBluetooth)
        val tvStateBT = dialog.findViewById<TextView>(R.id.tvStateBluetooth)
        val ivStateSendInfo = dialog.findViewById<ImageView>(R.id.ivStateSendInfo)

        if (flag) {
            tvTitleItemBT.text = title
            Picasso.get().load(imagen).into(ivItemBT)
            tvStateBT.text = getString(R.string.Bluetooth_ExitoRecilaje)
            ivStateSendInfo.setImageResource(R.drawable.correcto)
            ivStateSendInfo.setColorFilter(ContextCompat.getColor(this, R.color.color_navBar))
        } else {
            tvTitleItemBT.text = title
            Picasso.get().load(imagen).into(ivItemBT)
            tvStateBT.text = getString(R.string.Bluetooth_NoExitoRecilaje)
            ivStateSendInfo.setImageResource(R.drawable.incorrecto)
            ivStateSendInfo.setColorFilter(ContextCompat.getColor(this, R.color.red))
        }

        btnConfirmBT.setOnClickListener {
            dialog.hide()
            finish()
        }

        dialog.show()
    }

    private fun sendRecycleCommand():Boolean {
        val type = intent.getStringExtra("type")
        val json = JSONObject(type)
        val contenedor = json.getInt("contenedor")

        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(contenedor.toString().toByteArray())
                Toast.makeText(this, getString(R.string.Bluetooth_sendComand), Toast.LENGTH_SHORT).show()
                binding.tvStateBluetooth.text = getString(R.string.Bluetooth_Reciclando)
                binding.pbBluetooth.isVisible = false
                Log.i("BluetoothActivity", "Enviando comando de reciclaje al dispositivo: $contenedor")
                val flag = prefsUsers.upQuestionFlag()
                Log.i("valor44444", flag.toString())
                if (flag) {
                    sharedViewModel.expProgress(5)
                } else {
                    Log.i("BluetoothActivity", "NO HAZ GANADO PUNTOS DE EXPERIENCIA")
                }
                flagDialog = true
                showDialogLevel(flagDialog)

            } catch (e: IOException) {
                Log.e("BluetoothActivity", "Error al enviar comando: ${e.message}")
                e.printStackTrace()
                Toast.makeText(this, "Error al enviar comando", Toast.LENGTH_SHORT).show()
                flagDialog = false
                showDialogLevel(flagDialog)

            }
        }
        return flagDialog
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.Bluetooth_back)
            .setCancelable(false)
            .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                super.onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton(R.string.AlertDialogNo) { dialog, id -> }
            .show()
    }

    private fun ivBackBT() {
        binding.ivBackBT.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.Bluetooth_back)
                .setCancelable(false)
                .setPositiveButton(R.string.AlertDialogSi) { dialog, id ->
                    super.onBackPressedDispatcher.onBackPressed()
                }
                .setNegativeButton(R.string.AlertDialogNo) { dialog, id -> }
                .show()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                if (ActivityCompat.checkSelfPermission(this@BluetoothActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                val deviceName = device?.name
                val deviceAddress = device?.address
                if (deviceName != null && deviceAddress != null) {
                    mAddressDevice!!.add(deviceAddress)
                    mNameDevices!!.add(deviceName)
                    mNameDevices!!.notifyDataSetChanged()
                }
            }
        }
    }
}
