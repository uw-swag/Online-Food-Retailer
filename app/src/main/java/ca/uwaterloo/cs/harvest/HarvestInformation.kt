package ca.uwaterloo.cs.harvest

import android.os.Parcel
import android.os.Parcelable
import ca.uwaterloo.cs.Singleton
import ca.uwaterloo.cs.bemodels.HasOneImage
import ca.uwaterloo.cs.product.ProductInformation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.*

@kotlinx.serialization.Serializable
data class HarvestInformation (
    var harvestId: String,
    var harvestDescription: String = "no description",
    val fromWorker: String,
    var productId: String?,
    val name: String,
    val description: String,
    override var image: String,
    var amount: Int,
) : Serializable, Parcelable, HasOneImage {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    constructor(fromWorker: String, product: ProductInformation, amount: Int, harvestDescription: String): this (
        UUID.randomUUID().toString(),
        harvestDescription,
        fromWorker,
        product.productId,
        product.name,
        product.description,
        product.image,
        amount
    )

    constructor(fromWorker: String, name: String, description: String, image: String, amount: Int, harvestDescription: String): this (
        UUID.randomUUID().toString(),
        harvestDescription,
        fromWorker,
        "",
        name,
        description,
        image,
        amount
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(harvestId)
        parcel.writeString(fromWorker)
        parcel.writeString(productId)
        parcel.writeString(name)
        parcel.writeString(harvestDescription)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HarvestInformation> {
        override fun createFromParcel(parcel: Parcel): HarvestInformation {
            return HarvestInformation(parcel)
        }

        override fun newArray(size: Int): Array<HarvestInformation?> {
            return arrayOfNulls(size)
        }
    }

    fun exportData(fileDir: String) {
        // saving in the database
        val dir = File(fileDir)
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, "Harvest-$harvestId.txt")
        if (!file.exists())
        {
            file.createNewFile()
        }
        val stringData = Json.encodeToString(this)
        file.writeText(stringData)
    }

    fun deleteData(fileDir: String) {
        Singleton.workerIdAndHarvestIdDeleted.add(Pair(this.fromWorker, this.harvestId))
        // deleting in the database
        val file = File(fileDir, "Harvest-$harvestId.txt")
        if (file.exists())
        {
            file.delete()
        }
    }
}
