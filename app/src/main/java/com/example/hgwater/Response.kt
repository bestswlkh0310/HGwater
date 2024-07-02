package com.example.hgwater

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("STATUS")
    val status: String,
    @SerializedName("MSG")
    val msg: String,
    @SerializedName("DATAs")
    val data: Data
)

data class Data(
    @SerializedName("DATA")
    val data: HGWaterData
)

data class HGWaterData(
    @SerializedName("HANGANG")
    val hangang: HG
)

data class HG(
    val 선유: HGContent,
    val 노량진: HGContent,
    val 안양천: HGContent,
    val 탄천: HGContent,
    val 중랑천: HGContent
)

data class HGContent(
    @SerializedName("TEMP")
    val temp: Float?,
    @SerializedName("LAST_UPDATE")
    val lastUpdate: String,
    @SerializedName("PH")
    val ph: Float?,
    // for model
    val name: String?
)