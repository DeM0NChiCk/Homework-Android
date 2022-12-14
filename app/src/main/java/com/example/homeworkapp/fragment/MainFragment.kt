package com.example.homeworkapp.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.example.homeworkapp.databinding.FragmentMainBinding
import com.example.homeworkapp.service.MyLocationService

class MainFragment: Fragment(R.layout.fragment_main) {

    private var shouldCheckLocationPermission = false

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        with(binding) {
            with(requireActivity()){
                btnStartLocationService.setOnClickListener {
                    if (!isLocationEnabled(requireContext())) {
                        Toast.makeText(
                            requireContext(),
                            "Cannot start tracking while location is disabled",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }
                    startForegroundService(MyLocationService.getServiceIntent(requireContext()))
                }
                btnStopLocationService.setOnClickListener {
                    stopService(MyLocationService.getServiceIntent(requireContext()))
                }
            }

            tryGetLocationPermission()

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                enableLocationTrackerButtons()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                showCoarseLocationToast()
                enableLocationTrackerButtons()
            }
            else -> {
                showPermissionDialog()
            }
        }
    }

    private fun tryGetLocationPermission() {
        val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                fineLocationPermission
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableLocationTrackerButtons()
            }

            ContextCompat.checkSelfPermission(
                requireContext(),
                coarseLocationPermission
            ) == PackageManager.PERMISSION_GRANTED -> {
                showCoarseLocationToast()
                enableLocationTrackerButtons()
            }

            shouldShowRequestPermissionRationale(coarseLocationPermission)
            -> {
                showPermissionDialog()
            }

            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        coarseLocationPermission,
                        fineLocationPermission
                    )
                )
            }
        }
    }

    @Suppress("DEPRECATION")
    fun isLocationEnabled(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            val mode = Settings.Secure.getInt(
                context.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }

    private fun enableLocationTrackerButtons() {
        with(binding) {
            btnStartLocationService.isEnabled = true
            btnStopLocationService.isEnabled = true
        }
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.tracker_location)
            .setMessage(R.string.tracker_permission_text)
            .setPositiveButton(R.string.go_to_settings) { dialog, _ ->
                permissionDialogPositiveClick(dialog)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showCoarseLocationToast() {
        Toast.makeText(
            requireContext(),
            "Poor tracking accuracy",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun permissionDialogPositiveClick(dialog: DialogInterface) {
        dialog.dismiss()
        shouldCheckLocationPermission = true
        startActivity(Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts(
                "package", requireContext().packageName, null
            )
        })
    }

    override fun onResume() {
        super.onResume()
        if (shouldCheckLocationPermission) {
            tryGetLocationPermission()
            shouldCheckLocationPermission = false
        }
    }



}