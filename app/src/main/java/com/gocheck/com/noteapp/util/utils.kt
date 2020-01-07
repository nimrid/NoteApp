package com.gocheck.com.noteapp.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.snackBar(view : View, message : String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}