package com.example.homeworkapp.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.webkit.URLUtil
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.homeworkapp.R
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.example.homeworkapp.activity.MainActivity
import com.example.homeworkapp.databinding.FragmentScannerQrCodeBinding
import com.google.android.material.snackbar.Snackbar

class ScannerFragment: Fragment(R.layout.fragment_scanner_qr_code) {

    private var _binding: FragmentScannerQrCodeBinding? = null
    private val binding get() = _binding!!

    private  val codeScanner by lazy { CodeScanner(requireActivity(), binding.scannerView) }

    private var shouldCheckCameraPermission = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScannerQrCodeBinding.bind(view)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.qr_scanner)

        tryGetCameraPermission()
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                enableQrScanner()
            } else {
                changePermissionTextVisibility(View.VISIBLE)
            }
        }

    private fun tryGetCameraPermission() {
        val permission = Manifest.permission.CAMERA

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableQrScanner()
            }

            shouldShowRequestPermissionRationale(permission)
            -> {
                showPermissionDialog()
            }
            else -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }
        }
    }

    private fun enableQrScanner() {
        val scannerView = binding.scannerView
        scannerView.visibility = View.VISIBLE
        changePermissionTextVisibility(View.GONE)

        codeScanner.decodeCallback = DecodeCallback {
            openQrCodeUrl(it.text)
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun openQrCodeUrl(url: String) {
        if (URLUtil.isValidUrl(url)) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } else {
            Snackbar.make(
                binding.root,
                getString(R.string.invalid_qr_code),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun changePermissionTextVisibility(visibility: Int) {
        binding.scannerPermissionText.visibility = visibility
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.qr_code_scanner_camera)
            .setMessage(R.string.qr_code_scanner_permission_text)
            .setPositiveButton(R.string.go_to_settings) { dialog, _ ->
                permissionDialogPositiveClick(dialog)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                permissionDialogNegativeClick(dialog)
            }
            .create()
            .show()
    }

    private fun permissionDialogPositiveClick(dialog: DialogInterface) {
        dialog.dismiss()
        changePermissionTextVisibility(View.VISIBLE)
        shouldCheckCameraPermission = true
        startActivity(Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts(
                "package", requireContext().packageName, null
            )
        })
    }

    private fun permissionDialogNegativeClick(dialog: DialogInterface) {
        dialog.dismiss()
        changePermissionTextVisibility(View.VISIBLE)
    }

    override fun onResume() {
        super.onResume()
        if (shouldCheckCameraPermission) {
            tryGetCameraPermission()
            shouldCheckCameraPermission = false
        }

        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}