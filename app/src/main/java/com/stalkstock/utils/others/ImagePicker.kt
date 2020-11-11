package com.stalkstock.utils.others;

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.BuildConfig
import com.stalkstock.R
import com.yalantis.ucrop.UCrop

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

/**
 * Created by AIM on 10/16/2018.
 */
abstract class ImagePicker : AppCompatActivity() {
    private val SELECT_FILE = 2022
    private val SELECT_VIDEO = 200
    private val REQUEST_CAMERA = 2011
    private val REQUEST_PERMISSIONS_CAMERA = 2
    private val REQUEST_PERMISSIONS_VIDEO = 22
    var mImageFile: File? = null

    fun checkPermissionCamera() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!cameraPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSIONS_CAMERA)
                return
            } else {
                selectImage()
            }
        } else {
            selectImage()
        }
    }
    fun checkPermissionVideo() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!cameraPermission(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSIONS_VIDEO)
                return
            } else {
                selectVideos()
            }
        } else {
            selectVideos()
        }
    }

    private fun selectVideos() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/* video/*"
        startActivityForResult(pickIntent, SELECT_VIDEO)
    }

    override fun onStart() {
        super.onStart()
        Log.e("onStart","here")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (permission in grantResults) {
            permissionCheck = permissionCheck + permission
        }
        if (grantResults.size > 0 && permissionCheck == PackageManager.PERMISSION_GRANTED &&requestCode==REQUEST_PERMISSIONS_CAMERA) {
            selectImage()
        }
        else if (grantResults.size > 0 && permissionCheck == PackageManager.PERMISSION_GRANTED &&requestCode==REQUEST_PERMISSIONS_VIDEO) {
            selectVideos()
        }
    }

    fun cameraPermission(permissions: Array<String>): Boolean {
        return ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, permissions[2]) == PackageManager.PERMISSION_GRANTED
    }

    private fun selectImage() {
        val items =   arrayOf("Camera" ,"Choose from Gallery","Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.add_photo))
        builder.setItems(items) { dialog, item ->
            if (item == 0) {
                cameraIntent()
            } else if (item == 1) {
                galleryIntent()
            } else if (item == 2) {
                dialog.dismiss()
            }
        }
        builder.show()
    }


    //TODO open camera
    private fun cameraIntent() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        try {
            createImageFile(this, imageFileName, ".jpg")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file_uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", mImageFile!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    //TODO open gallery
    private fun galleryIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_FILE)
    }

    @Throws(IOException::class)
    fun createImageFile(context: Context, name: String, extension: String) {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        mImageFile = File.createTempFile(
                name,
                extension,
                storageDir
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQUEST_CAMERA) {
                val uri = Uri.fromFile(mImageFile)
                cropImage(uri)
//                var picture_path = CommonMethods.getAbsolutePath(this, uri)
//                selectedImage(picture_path)
            } else if (requestCode == SELECT_FILE) {
                val uri = data?.getData()
                var picture_path = UtilityMethods.getImagePathFromInputStreamUri(this, uri!!)
                cropImage(Uri.fromFile(File(picture_path)))
//                var picture_path = CommonMethods.getAbsolutePath(this, uri!!)
//                selectedImage(picture_path)
            }
             else if (requestCode == SELECT_VIDEO) {
                val selectedMediaUri = data?.getData()
                Log.d("SelectedURI>>>>>>",selectedMediaUri.toString())
                if (selectedMediaUri.toString().contains(".jpg")||selectedMediaUri.toString().contains(".png")||selectedMediaUri.toString().contains(".jpeg"))
                {
                    val uri = data?.getData()
                    var picture_path = UtilityMethods.getImagePathFromInputStreamUri(this, uri!!)
                    cropImage(Uri.fromFile(File(picture_path)))
                } else if (selectedMediaUri.toString().contains(".mp4")) {
                    val uri = data?.getData()
                    val imagePathFromInputStreamUri = UtilityMethods.getImagePathFromInputStreamUri(this, uri!!)
                    selectedVideo(uri,imagePathFromInputStreamUri)
                    //handle video
                }
            }

            if (requestCode == UCrop.REQUEST_CROP) {
                var resultUri: Uri = UCrop.getOutput(data!!)!!
                selectedImage(resultUri.path)
            } else if (resultCode == UCrop.RESULT_ERROR) {
                var cropError: Throwable = UCrop.getError(data!!)!!
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            var cropError: Throwable = UCrop.getError(data!!)!!
            Log.e("CropErr>>",cropError.toString())
        }
    }

    abstract fun selectedVideo(uri: Uri?, imagePathFromInputStreamUri1: String)

    private fun cropImage(uri: Uri) {
        UCrop.of(uri, uri)
            .withAspectRatio(4F, 4F)
            .withMaxResultSize(1024, 1024)
            .start(this)
    }


    abstract fun selectedImage(imagePath: String?)
}