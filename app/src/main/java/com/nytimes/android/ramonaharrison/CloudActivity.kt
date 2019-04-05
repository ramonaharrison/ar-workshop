package com.nytimes.android.ramonaharrison

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.R.attr.configure
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Config.CloudAnchorMode
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.nytimes.android.ramonaharrison.helpers.SnackbarHelper
import kotlinx.android.synthetic.main.content_cloud.*
import com.google.ar.sceneform.FrameTime
import com.nytimes.android.ramonaharrison.helpers.StorageHelper
import androidx.constraintlayout.solver.widgets.ResolutionNode.RESOLVED


class CloudActivity : AppCompatActivity() {

    private enum class AppAnchorState {
        NONE,
        HOSTING,
        HOSTED,
        RESOLVING,
        RESOLVED
    }

    private var appAnchorState = AppAnchorState.NONE

    private lateinit var arFragment: ArFragment
    private var cloudAnchor: Anchor? = null
    private val snackbarHelper = SnackbarHelper()

    private val storageHelper = StorageHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud)

        arFragment = fragment as ArFragment

        val sceneView = arFragment.arSceneView
        val session = sceneView.session

        sceneView.scene.addOnUpdateListener { checkUpdatedAnchor() }

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (appAnchorState != AppAnchorState.NONE) {
                return@setOnTapArPlaneListener
            }

            setCloudAnchor(hitResult.createAnchor())
            appAnchorState = AppAnchorState.HOSTING
            cloudAnchor = arFragment.arSceneView.session?.hostCloudAnchor(cloudAnchor)
            val uri = ArModel.COFFEE.getUri()

            snackbarHelper.showMessage(this, "Now hosting anchor...")

            placeObject(cloudAnchor, uri)
        }

        clear_button.setOnClickListener { setCloudAnchor(null) }


        resolve_button.setOnClickListener {
            if (cloudAnchor != null) {
                snackbarHelper.showMessageWithDismiss(parent, "Please clear Anchor")
                return@setOnClickListener
            }
            val dialog = ResolveDialogFragment()
            dialog.setOkListener(object : ResolveDialogFragment.OkListener {
                override fun onOkPressed(dialogValue: String) {
                    this@CloudActivity.onResolveOkPressed(dialogValue)
                }


            })
            dialog.show(supportFragmentManager, "Resolve")

        }
    }

    private fun onResolveOkPressed(dialogValue: String) {
        val shortCode = Integer.parseInt(dialogValue)
        val cloudAnchorId = storageHelper.getCloudAnchorID(this, shortCode)

        val resolvedAnchor = arFragment.arSceneView.session?.resolveCloudAnchor(cloudAnchorId)
        setCloudAnchor(resolvedAnchor)
        placeObject(cloudAnchor, ArModel.COFFEE.getUri())
        snackbarHelper.showMessage(this, "Now Resolving Anchor...")
        appAnchorState = AppAnchorState.RESOLVING
    }

    @Synchronized
    private fun checkUpdatedAnchor() {
        if (appAnchorState !== AppAnchorState.HOSTING && appAnchorState !== AppAnchorState.RESOLVING) {
            return
        }
        val cloudState = cloudAnchor?.getCloudAnchorState()

        if (appAnchorState == AppAnchorState.HOSTING) {
            if (cloudState == null || cloudState.isError()) {
                snackbarHelper.showMessageWithDismiss(this, "Error hosting anchor: " + cloudState);
                appAnchorState = AppAnchorState.NONE;
            } else if (cloudState == Anchor.CloudAnchorState.SUCCESS) {
                val shortCode = storageHelper.nextShortCode(this);
                storageHelper.storeUsingShortCode(this, shortCode, cloudAnchor?.getCloudAnchorId());
                snackbarHelper.showMessageWithDismiss(
                    this, "Anchor hosted successfully! Cloud Short Code: " + shortCode
                );
                appAnchorState = AppAnchorState.HOSTED;
            }
        } else if (appAnchorState == AppAnchorState.RESOLVING) {
            if (cloudState == null || cloudState.isError()) {
                snackbarHelper.showMessageWithDismiss(this, "Error resolving anchor: " + cloudState);
                appAnchorState = AppAnchorState.NONE;
            } else if (cloudState == Anchor.CloudAnchorState.SUCCESS) {
                snackbarHelper.showMessageWithDismiss(this, "Anchor resolved successfully!");
                appAnchorState = AppAnchorState.RESOLVED;
            }
        }
    }

    private fun setCloudAnchor(newAnchor: Anchor?) {
        cloudAnchor?.detach()
        cloudAnchor = newAnchor

        appAnchorState = AppAnchorState.NONE
        snackbarHelper.hide(this)
    }

    private fun addNodeToScene(anchor: Anchor?, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
    }

    private fun placeObject(anchor: Anchor?, model: Uri) {
        ModelRenderable.builder()
            .setSource(arFragment.context, model)
            .build()
            .thenAccept { renderable -> addNodeToScene(anchor, renderable) }
            .exceptionally { throwable ->
                Log.e("CloudActivity", throwable.message)
                Toast.makeText(this@CloudActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                null
            }
    }

}