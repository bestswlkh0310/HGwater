package com.example.hgwater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hgwater.model.response.Hgwater
import com.example.hgwater.databinding.ActivityMainBinding
import com.example.hgwater.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    val tag: String = "로그"
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val call = RetrofitClient.api.getTemp()
        call.enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: retrofit2.Response<JsonElement>) {
                if (response.isSuccessful) {
                    // 성공 시 텍스트 값 바꾸기
                    val result = response.body()
                    val data = Gson().fromJson(result, Hgwater::class.java)[1].respond
                    val text = "${data.temp}도"
                    binding.tv2.text = text
                } else {
                    Log.d(tag, "응 - onResponse() called")
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(tag, "실패 ${t.message}")
            }
        })
    }
}