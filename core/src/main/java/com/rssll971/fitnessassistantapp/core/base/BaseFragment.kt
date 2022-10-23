/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.core.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Parent Fragment for all Fragments in app
 * */
open class BaseFragment: Fragment() {
    /**
     * Method shows snackBar with received [text]
     * */
    fun showSnackBarMessage(text: String){
        val sb = Snackbar.make(
            activity?.findViewById(android.R.id.content)!!,
            text,
            Snackbar.LENGTH_LONG)
        sb.show()
    }
}