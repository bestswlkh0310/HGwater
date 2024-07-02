package com.example.hgwater

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hgwater.databinding.ActivityMainBinding
import com.example.hgwater.databinding.LayoutHgLocationItemBinding
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // locations
    private val locationsSource = PublishSubject.create<List<HGContent>>()
        .apply {
            subscribe {
                val linearLayout = binding.llLocations
                linearLayout.removeAllViews()
                it.forEachIndexed { idx, item ->
                    val hgItem = LayoutHgLocationItemBinding.inflate(layoutInflater)
                    hgItem.apply {
                        val tempText = item.temp?.let { temp -> "$temp°" } ?: "-"
                        tvTemp.text = tempText
                        tvLocation.text = item.name
                    }
                    hgItem.root.setOnClickListener {
                        currentLocationIndexSource.onNext(idx)
                    }
                    linearLayout.addView(hgItem.root)
                }
            }.let { }
        }
    private var locations = listOf<HGContent>()
        set(value) {
            field = value
            locationsSource.onNext(field)
        }

    // currentLocationIndex
    private val currentLocationIndexSource = PublishSubject.create<Int>()
        .apply {
            subscribe {
                val hgTempString = locations[it].temp?.let { temp -> "$temp°" } ?: "-"
                binding.apply {
                    tvTemp.text = hgTempString
                    val tvLocationText = "오늘의 ${locations[it].name} 온도"
                    tvLocation.text = tvLocationText
                }
            }.let {  }
        }
    private var currentLocationIndex = 0
        set(value) {
            field = value
            currentLocationIndexSource.onNext(field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        lifecycleScope.launch {
            runCatching {
                val response = RetrofitClient.api.getTemp()
                response
            }.onSuccess {
                val hangang = it.data.data.hangang
                locations = listOf(
                    hangang.선유.copy(name = "선유"),
                    hangang.탄천.copy(name = "탄천"),
                    hangang.노량진.copy(name = "노량진"),
                    hangang.안양천.copy(name = "안양천"),
                    hangang.중랑천.copy(name = "중랑천")
                )
                currentLocationIndex = 0
            }.onFailure {
                AlertDialog.Builder(this@MainActivity).setTitle("한강 물 온도를 불러올 수 없습니다")
                    .setMessage("잠시 후 다시 시도해 주세요").setPositiveButton("확인") { _, _ -> }.create()
                    .show()
            }
        }
    }
}