package com.example.apinasaapp.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.apinasaapp.di.Injectable

abstract class BaseFragment : Fragment(), Injectable{
    protected fun toggleKeyBoard() = (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(0, 0)
}