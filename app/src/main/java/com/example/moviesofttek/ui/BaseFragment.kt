package com.example.moviesofttek.ui

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    var dialogProgress: ProgressDialog? = null
    val isInternetAvailable: Boolean
        get() = isNetworkAvailable(requireContext())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoader()
        if (!isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), "No hay conexión a internet", Toast.LENGTH_LONG).show()
        }
    }

    private fun initLoader(){
        dialogProgress = ProgressDialog(requireContext())
        dialogProgress?.let {
            it.setMessage("Cargando...")
            it.setIndeterminate(true)
            it.setCancelable(false)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}