package com.nytimes.android.ramonaharrison

import com.google.ar.core.Config
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.core.Config.CloudAnchorMode
import com.google.ar.core.Session


class CloudArFragment : ArFragment() {

    override fun getSessionConfiguration(session: Session): Config {
        planeDiscoveryController.setInstructionView(null)
        val config = super.getSessionConfiguration(session)
        config.cloudAnchorMode = Config.CloudAnchorMode.ENABLED
        return config
    }
}