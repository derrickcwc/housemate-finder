package com.example.housematefinder.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "room_list",
        foreignKeys = [ForeignKey(
            entity = User::class,
            childColumns = ["user_id"],
            parentColumns = ["userId"]
        )])
data class RoomList(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "user_id")
    var userId : Int,

    @ColumnInfo(name = "address")
    var address :String?,

    @ColumnInfo(name = "city")
    var city :String?,

    @ColumnInfo(name = "female_only")
    var female_only :Boolean,

    @ColumnInfo(name = "house_type")
    var house_type: String?,

    @ColumnInfo(name = "price")
    var price :Int,

    @ColumnInfo(name = "description")
    var description :String?,

    @ColumnInfo(name = "status")
    var status : Boolean

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeByte(if (female_only) 1 else 0)
        parcel.writeString(house_type)
        parcel.writeInt(price)
        parcel.writeString(description)
        parcel.writeByte(if (status) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomList> {
        override fun createFromParcel(parcel: Parcel): RoomList {
            return RoomList(parcel)
        }

        override fun newArray(size: Int): Array<RoomList?> {
            return arrayOfNulls(size)
        }
    }

}
