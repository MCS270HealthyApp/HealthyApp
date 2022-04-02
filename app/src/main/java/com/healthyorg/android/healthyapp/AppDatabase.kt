import android.content.Context
import androidx.room.*
import com.healthyorg.android.healthyapp.Daily_Weight
import com.healthyorg.android.healthyapp.WeightDao

@Database(entities = [Daily_Weight::class], version = 1)
abstract class WeightRoomDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao

    companion object{
        @Volatile
        private var INSTANCE: WeightRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): WeightRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeightRoomDatabase::class.java,
                    "weight_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}