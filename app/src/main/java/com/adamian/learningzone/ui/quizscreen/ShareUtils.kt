package com.adamian.learningzone.ui.quizscreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import kotlin.apply
import kotlin.io.use

fun shareBitmap(context: Context, bitmap: Bitmap) {

    val cachePath = File(context.cacheDir, "images").apply { mkdirs() }
    val file = File(cachePath, "summary.png")

    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_SUBJECT, "TechZone – Αποτέλεσμα Quiz")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(
        Intent.createChooser(intent, "Κοινοποίηση αποτελέσματος")
    )
}
