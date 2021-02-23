package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
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