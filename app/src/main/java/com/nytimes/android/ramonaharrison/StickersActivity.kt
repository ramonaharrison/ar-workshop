package com.nytimes.android.ramonaharrison

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.nytimes.android.ramonaharrison.helpers.CameraHelper
import kotlinx.android.synthetic.main.activity_stickers.*
import kotlinx.android.synthetic.main.content_stickers.*

class StickersActivity : AppCompatActivity() {

    private val galleryAdapter = GalleryAdapter()
    private lateinit var galleryRecyclerview: RecyclerView

    private lateinit var arFragment: ArFragment
    private lateinit var camera: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stickers)
        setSupportActionBar(toolbar)

        galleryRecyclerview = findViewById<RecyclerView>(R.id.gallery_recyclerview).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = galleryAdapter
            setHasFixedSize(true)
        }

        arFragment = fragment as ArFragment
        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val uri = galleryAdapter.getSelected().getUri()
            placeObject(anchor, uri)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        camera = CameraHelper(this, arFragment.arSceneView)
        fab.setOnClickListener { camera.snap() }
    }

    private fun addSphere(color: Int, anchor: Anchor, radius: Float, centerX : Float, centerY: Float, centerZ : Float) {
        MaterialFactory.makeOpaqueWithColor(this, com.google.ar.sceneform.rendering.Color(color))
            .thenAccept { material ->
                val shape = ShapeFactory.makeSphere(radius, Vector3(centerX, centerY, centerZ), material)
                addNodeToScene(anchor, shape)
            }
    }

    private fun addNodeToScene(anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
    }

    private fun placeObject(anchor: Anchor, model: Uri) {
        ModelRenderable.builder()
            .setSource(fragment.context, model)
            .build()
            .thenAccept { renderable -> addNodeToScene(anchor, renderable) }
            .exceptionally { throwable ->
                Log.e("MainActivity", throwable.message)
                Toast.makeText(this@StickersActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                null
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
