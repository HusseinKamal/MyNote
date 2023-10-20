package com.hussein.mynote.model
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hussein.mynote.util.Constant

@Entity(tableName = Constant.NOTE_TABLE)
data class Note(
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "description")
    var description: String?,
  /*  @ColumnInfo(name = "date")
    var date: String?,*/
    @ColumnInfo(name = "time")
    var time: String?,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int = 0 // You shoud add id here to make it auto generated as it is not passed in constructor
}