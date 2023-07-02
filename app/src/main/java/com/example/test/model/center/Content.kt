package com.example.example

import com.google.gson.annotations.SerializedName


data class Content (

  @SerializedName("centerId" ) var centerId : Int?    = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("lat"      ) var lat      : Double? = null,
  @SerializedName("lnt"      ) var lnt      : Double? = null,
  @SerializedName("address"  ) var address  : String? = null,
  @SerializedName("price"    ) var price    : Int?    = null,
  @SerializedName("imgUrl"   ) var imgUrl   : String? = null

)