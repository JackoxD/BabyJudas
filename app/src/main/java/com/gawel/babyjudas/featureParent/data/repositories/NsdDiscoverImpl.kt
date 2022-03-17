package com.gawel.babyjudas.featureParent.data.repositories

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.gawel.babyjudas.core.utils.Constants
import com.gawel.babyjudas.featureParent.domain.repositories.NsdDiscover
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "NdsDiscoverImpl"
@Singleton
class NsdDiscoverImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NsdDiscover {

    private lateinit var nsdManager: NsdManager
    private lateinit var service: NsdServiceInfo


    private val resolveListener = object : NsdManager.ResolveListener {

        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.d(TAG, "Resolve Succeeded. $serviceInfo")
            if (serviceInfo.serviceName == Constants.NDS_DISCOVER_NAME) {
                Log.d(TAG, "Same IP.")
                return
            }
            service = serviceInfo
            val port: Int = serviceInfo.port
            val host: InetAddress = serviceInfo.host
        }
    }

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success. $service")
            when {
                !service.serviceType.contains(Constants.NDS_SERVICE_TYPE) ->
                    Log.d(TAG, "Unknown Service Type: ${service.serviceType}")
                service.serviceName == Constants.NDS_DISCOVER_NAME ->
                    Log.d(TAG, "Same machine: ${Constants.NDS_DISCOVER_NAME}")
                service.serviceName.contains(Constants.NDS_SERVICE_NAME) -> {
                    try {
                        nsdManager.resolveService(service, resolveListener)
                    } catch (ignore: Exception){}
                }
            }
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            try {
                nsdManager.stopServiceDiscovery(this)
            } catch (ignore: Exception){}
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager.stopServiceDiscovery(this)
        }
    }

    override fun discoverService() {
        nsdManager = (context.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            discoverServices(Constants.NDS_SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    override fun stopDiscover() {
        nsdManager?.stopServiceDiscovery(discoveryListener)
    }


}