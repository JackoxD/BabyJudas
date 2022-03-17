package com.gawel.babyjudas.featureBaby.data.repositories

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.gawel.babyjudas.core.utils.Constants
import com.gawel.babyjudas.featureBaby.domain.repositories.NsdService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "NdsServiceImpl"

@Singleton
class NsdServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NsdService {

    private var serviceName: String? = null
    private var nsdManager: NsdManager? = null

    private val registrationListener = object : NsdManager.RegistrationListener {
        override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
            // Save the service name. Android may have changed it in order to
            // resolve a conflict, so update the name you initially requested
            // with the name Android actually used.
            serviceName = NsdServiceInfo.serviceName
            Log.d(TAG, "onServiceRegistered: $serviceName")
        }

        override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Registration failed! Put debugging code here to determine why.
            Log.e(TAG, "onRegistrationFailed: $serviceInfo,\nerrorCode: $errorCode")
        }

        override fun onServiceUnregistered(arg0: NsdServiceInfo) {
            // Service has been unregistered. This only happens when you call
            // NsdManager.unregisterService() and pass in this listener.
        }

        override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            // Unregistration failed. Put debugging code here to determine why.
            Log.e(TAG, "onUnregistrationFailed: $serviceInfo,\nerrorCode: $errorCode")

        }
    }

    override fun registerService(port: Int) {
        // Create the NsdServiceInfo object, and populate it.
        val serviceInfo = NsdServiceInfo().apply {
            // The name is subject to change based on conflicts
            // with other services advertised on the same network.
            serviceName = Constants.NDS_SERVICE_NAME
            serviceType = Constants.NDS_SERVICE_TYPE
            setPort(port)
        }
        nsdManager = (context.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
        }
    }

    override fun unregister() {
        nsdManager?.unregisterService(registrationListener)
    }

}