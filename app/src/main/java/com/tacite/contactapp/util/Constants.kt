package com.tacite.contactapp.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

object Constants {
    val colors = listOf(
        0xFFe28743, 0xFF5dc0d5, 0xFFb4e6ed, 0xFFbbedb4, 0xFFbbedb4
    )

    fun Context.sendMail(to: String, subject: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "vnd.android.cursor.item/email"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            println("Pas d'application")
        } catch (t: Throwable) {
            println("Un problème est survenu")
        }
    }
}