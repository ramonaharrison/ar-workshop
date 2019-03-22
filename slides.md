# ARCore & Sceneform Workshop

- Welcome! Before we get started...
- Clone the project
- Check that Android Studio is the correct version or higher
- Set up an ARCore supported device...
- ... or emulator

---

# About ARCore

- Google released ARCore in February 2018.
- ARCore helps devs build apps that can understand the environment around a device and place objects and information in it.

---

# About Sceneform

- Google followed up with Sceneform at I/O 2018.
- Sceneform helps devs render 3D scenes on Android without needing to learn OpenGL.

---

# Add the dependency

In `app/build.gradle`

```groovy

dependencies {
	// ...

	implementation "com.google.ar.sceneform.ux:sceneform-ux:1.4.0"
}
```

---

# Configure the manifest

Setup camera in the `<manifest>` section

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.ar" android:required="true" />
```

In the `<application>` section, add a Play Store filter for users on devices that are not supported by ARCore.

```xml
<meta-data android:name="com.google.ar.core" android:value="required" />
```

---

# Add the ARFragment

In `content_main.xml`

```xml
<fragment
   android:id="@+id/sceneform_fragment"
   android:name="com.google.ar.sceneform.ux.ArFragment"
   android:layout_width="match_parent"
   android:layout_height="match_parent" />
```

---

# Add the ARFragment

In `MainActivity.kt`

```kotlin

```

---

# Run it!

---

# Emulator troubleshooting

- Make sure the emulator is version ______ or higher.
- Install the ARCore APK (link in `README.md`)
- If you're still stuck, follow the steps 

---

# Scene / 3D coordinates

---

# Plane detection

---

# Feature points
___

# Detecting taps

---

# Update loop

---

# Hit test

---

# Hit test

--- 

# Run it!

---

# Sceneform plugin

---

# Sceneform plugin: installation

---

# Import an asset

---

# Supported formats

---

# Sceneform assets

---

# Sceneform assets: editing

___

# Loading assets

---

# Renderables

---

# Nodes

---

# Scene graph

---

# Run it!

---

# Now for the fun stuff...

---

# Cloud anchors

---

# Augmented Faces

___

# Grazie!