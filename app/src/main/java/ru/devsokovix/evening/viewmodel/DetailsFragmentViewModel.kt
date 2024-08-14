package ru.devsokovix.evening.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DetailsFragmentViewModel  : ViewModel() {
    suspend fun loadWallpaper(url: String): Bitmap? {
        return suspendCoroutine {
            try {
                val url = URL(url)
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                it.resume(bitmap)
            } catch (e: IOException){
                it.resume(null)
            }
        }
    }
}