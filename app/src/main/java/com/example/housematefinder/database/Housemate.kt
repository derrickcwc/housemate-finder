package com.example.housematefinder.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "housemate",
        foreignKeys = [ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["userId"]
        )])

data class Housemate(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "user_id")
    var userId : Int,

    @ColumnInfo(name = "city")
    var city :String?,

    @ColumnInfo(name = "age")
    var age : Int?,

    @ColumnInfo(name = "gender")
    var gender : String?,

    @ColumnInfo(name = "description")
    var description :String?,

    @ColumnInfo(name = "status")
    var status :Boolean,

) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeString(city)
        parcel.writeValue(age)
        parcel.writeString(gender)
        parcel.writeString(description)
        parcel.writeByte(if (status) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Housemate> {
        override fun createFromParcel(parcel: Parcel): Housemate {
            return Housemate(parcel)
        }

        override fun newArray(size: Int): Array<Housemate?> {
            return arrayOfNulls(size)
        }
    }
}
