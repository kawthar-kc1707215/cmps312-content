package qu.cmps312.shoppinglist.entity

import androidx.room.*
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Product::class,
    parentColumns = ["id"],
    childColumns = ["productId"],
    onDelete = ForeignKey.CASCADE)],
    // Create an index on the productId column to speed-up query execution
    indices = [Index(value = ["productId"])])
data class ShoppingItem(
    @PrimaryKey
    @DocumentId
    val id: String = "",
    val productId: String,
    var quantity: Int,
    /*
    Unfortunate limitation in Room:
      If productName is annotated with @Ignore Room will ignore when writing 👍 but
      it will also ignore it when reading👎 even if productName column is explicitly
      returned in a join query.
      So, as a workaround we store it as a null value but never read it
    */
    //@Ignore // productName will NOT be stored in the database
    var productName: String? = null,

    /* Need to add TypeConverter otherwise you get compile time error
        Cannot figure out how to read/save this field into database
     */
    @ServerTimestamp
     val updatedDate: Date = java.util.Calendar.getInstance().time,
     var uid : String? = null) {
    // Required by Firebase deserializer other you get exception 'does not define a no-argument constructor'
    constructor(): this("", "", 0)

    constructor(productId: String, quantity: Int) : this("", productId, quantity)
}
