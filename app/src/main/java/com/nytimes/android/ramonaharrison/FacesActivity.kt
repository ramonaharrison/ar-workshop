package com.nytimes.android.ramonaharrison

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_faces.*


class FacesActivity : AppCompatActivity() {

//    var faceRegionsRenderable: ModelRenderable? = null
//    var faceMeshTexture: Texture? = null
//
//    val faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faces)
        setSupportActionBar(toolbar)

//        loadFaceRenderable()
//        loadFaceTexture()
    }

//    private fun loadFaceRenderable() {
//        ModelRenderable.builder()
//            .setSource(this, R.raw.fox_face)
//            .build()
//            .thenAccept { modelRenderable: ModelRenderable ->
//                modelRenderable.isShadowCaster = false
//                modelRenderable.isShadowReceiver = false
//                faceRegionsRenderable = modelRenderable
//            }
//    }
//
//    private fun loadFaceTexture() {
//        Texture.builder()
//            .setSource(this, R.drawable.fox_face_mesh_texture)
//            .build()
//            .thenAccept({ texture: Texture ->
//                faceMeshTexture = texture
//            })
//    }
}
