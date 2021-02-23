package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.opengl.ETC1.getHeight


fun Activity.hideKeyboard() {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

private fun dip(value: Int): Int {
    return (value * Resources.getSystem().displayMetrics.density).toInt()
}

fun Activity.isKeyboardOpen(): Boolean {
    val rootView = window.decorView.rootView
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)

    val screenHeight = rootView.rootView.height
    val heightDiff = screenHeight - (r.bottom - r.top)
    return heightDiff > screenHeight / 3
}

fun Activity.isKeyboardClosed(): Boolean {
    val rootView = window.decorView.rootView
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)

    val screenHeight = rootView.rootView.height
    val heightDiff = screenHeight - (r.bottom - r.top)
    return heightDiff < screenHeight / 3
}