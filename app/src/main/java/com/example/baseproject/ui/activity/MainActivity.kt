package com.example.baseproject.ui.activity

import android.graphics.Color
import android.graphics.Rect
import android.location.Geocoder
import com.example.baseproject.ui.activity.viewmodel.MyViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.example.baseproject.databinding.ActivityMainBinding
import com.example.baseproject.network.model.ConvertLatLngCommonResponse
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val myViewModel by viewModels<MyViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val geoCoder by lazy { Geocoder(this, Locale.getDefault()) }
    private var latLngList: ArrayList<ConvertLatLngCommonResponse.ConvertLatLngCommonResponseItem> = arrayListOf()
    private var isCompleted = false
    private var index = 0
    private var newIndex = 0
    private var size = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initObserver()
    }

    private fun initObserver() {
        myViewModel.addressResponse.observe(this) { response ->
            if (!response.isNullOrEmpty()) {
                response.forEachIndexed { index, convertLatLngCommonResponseItem ->
                    if (index == response.size.minus(1)) {
                        isCompleted = true
                    }
                    myViewModel.getAddressByLatLng(geoCoder, convertLatLngCommonResponseItem)
                }
            }
        }

        myViewModel.latLngResponse.observe(this) { response ->
            if (response != null) {
                latLngList.add(response)
                if (isCompleted) {
                    size = latLngList.size
                    myViewModel.postCustomerAddress(latLngList)
                    Log.d("complete_list", latLngList.toString())
                    Log.d("complete_list_size", latLngList.size.toString())
                }
            }
        }

        myViewModel.postAddressResponse.observe(this) {
            if (it == 200) {
                binding.doneIndexText.text = "Done Index: $index($size)"
                latLngList.clear()
                isCompleted = false
                size = 0
                myViewModel.getCustomerAddress(++index)
            } else {
                binding.doneIndexText.apply {
                    text = "Error Index: $index($size)"
                    setTextColor(Color.RED)
                }
            }
        }
    }

    private fun initListener() {
        binding.startBtn.setOnClickListener {
            isCompleted = false
            latLngList.clear()
            index = binding.index.text.toString().toIntOrNull() ?: 0
            if (index != 0) myViewModel.getCustomerAddress(index)
            else Toast.makeText(this, "Enter a valid index", Toast.LENGTH_SHORT).show()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}