package com.example.hgwater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hgwater.model.response.hgwater
import com.example.hgwater.databinding.ActivityMainBinding
import com.example.hgwater.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    val TAG: String = "로그"
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call = RetrofitClient.api.getTemp()
        call.enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: retrofit2.Response<JsonElement>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    val data = Gson().fromJson(result, hgwater::class.java)[1].respond
                    binding.tv2.text = "${data.temp}도"
                } else {
                    Log.d(TAG, "응 - onResponse() called")
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "실패 ${t.message}")
            }
        })
    }
}