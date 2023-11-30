package com.example.homework13_revised

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class Field(
    @SerializedName("field_id")
    val fieldId: Int,
    val hint: String,
    @SerializedName("field_type")
    val fieldType: String,
    val keyboard: String,
    val required: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    val icon: String,
    var enteredText: String = ""
)

data class ListContainer(
    val fields: List<Field>
)

data class DataForDetailsFragment(
    val fieldId: Int,
    val fieldText: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(fieldId)
        parcel.writeString(fieldText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataForDetailsFragment> {
        override fun createFromParcel(parcel: Parcel): DataForDetailsFragment {
            return DataForDetailsFragment(parcel)
        }

        override fun newArray(size: Int): Array<DataForDetailsFragment?> {
            return arrayOfNulls(size)
        }
    }
}

enum class FieldType {
    INPUT, CHOOSER
}

fun parseJson(jsonString: String): List<ListContainer> {
    val gson = Gson()
    val listType = object : TypeToken<List<List<Field>>>() {}.type
    val lists: List<List<Field>> = gson.fromJson(jsonString, listType)

    return lists.map { ListContainer(it) }
}

fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}
