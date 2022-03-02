package xyz.sxsong.paperplane.ZoteroAPI.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagPOJO(
    @SerializedName("tag")
    val tag: String
) : Parcelable
