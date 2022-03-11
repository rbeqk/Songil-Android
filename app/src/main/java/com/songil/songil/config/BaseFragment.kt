package com.songil.songil.config

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.songil.songil.R

abstract class BaseFragment<B : ViewBinding> (private val bind : (View) -> B, @LayoutRes private val layoutResId : Int) : Fragment() {
    private var _binding : B ?= null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    protected fun getWindowSize() : ArrayList<Int> {
        val height : Int
        val width : Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val displayMetrics = requireContext().resources.displayMetrics
            height = displayMetrics.heightPixels//windowMetrics.bounds.height()
            width = displayMetrics.widthPixels//windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        return arrayListOf(width, height)
    }

    protected fun getStatusBarHeight() : Int {
        val resourceId = requireContext().resources.getIdentifier("status_bar_height", "dimen", "android")
        return requireContext().resources.getDimension(resourceId).toInt()
    }

    // 네트워크 에러를 관찰할 observer
    protected val baseNetworkErrorObserver = Observer<BaseViewModel.FetchState>{ fetchState ->
        when (fetchState){
            BaseViewModel.FetchState.BAD_INTERNET -> {
                Toast.makeText(activity, getString(R.string.bad_internet), Toast.LENGTH_SHORT).show()
            }
            BaseViewModel.FetchState.FAIL -> {
                Toast.makeText(activity, getString(R.string.bad_internet), Toast.LENGTH_SHORT).show()
            }
            BaseViewModel.FetchState.WRONG_CONNECTION -> {
                Toast.makeText(activity, getString(R.string.bad_internet), Toast.LENGTH_SHORT).show()
            }
            BaseViewModel.FetchState.PARSE_ERROR -> {}
            null -> {}
        }
    }
}