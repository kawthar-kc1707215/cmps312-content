package qu.cmps312.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Product

@Dao
interface ProductDao {
    // Product related methods
    @Query("select p.*, c.name category from Product p join Category c on p.categoryId = c.id where p.categoryId = :categoryId order by p.name")
    suspend fun getProducts(categoryId: Int): List<Product>

    @Insert
    suspend fun insertProducts(products: List<Product>) : List<Long>

    // Category related methods
    @Query("select * from Category order by name")
    suspend fun getCategories() : List<Category>

    @Query("select * from Category where name = :name")
    suspend fun getCategory(name: String) : Category?

    @Query("select count(*) from Category")
    suspend fun getCategoryCount() : Int

    @Insert
    suspend fun insertCategories(categories: List<Category>) : List<Long>
}