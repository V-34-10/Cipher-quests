package com.ciphero.questa.model

import android.os.Parcel
import android.os.Parcelable

data class Offerwall(
    val inx: Int? = 0,
    val title: String = "",
    val bgUrl: String = "",
    val fgUrl: String = "",
    val play: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Offerwall::class.java.classLoader) as? Int,
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(inx)
        parcel.writeString(title)
        parcel.writeString(bgUrl)
        parcel.writeString(fgUrl)
        parcel.writeString(play)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Offerwall> {

        override fun createFromParcel(parcel: Parcel): Offerwall = Offerwall(parcel)

        override fun newArray(size: Int): Array<Offerwall?> = arrayOfNulls(size)
    }
}