package com.example.utils

import io.ktor.http.content.*
import java.io.File
import java.util.*


fun PartData.FileItem.save(path: String): String {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    val folder = File(path)
    folder.mkdir()
    File("$path$fileName").writeBytes(fileBytes)
    return fileName
}

fun String.saveByteArray(path: String): String {
    val fileBytes = Base64.getDecoder().decode(this)
    val fileName = UUID.randomUUID().toString() + ".png"
    val folder = File(path)
    folder.mkdir()
    File("$path$fileName").writeBytes(fileBytes)
    return fileName
}

fun removeImage(path: String, fileName: String): Boolean {
    return File("$path$fileName").delete()
}