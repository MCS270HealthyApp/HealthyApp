package notesClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Naming the table that will hold our notes
@Entity(tableName = "notesTable")

//Creating our notes class for our notes objects, which will all have a title,
//a description (aka the body of the note), and a date
class Note (@ColumnInfo(name = "title")val noteTitle :String,@ColumnInfo(name = "description")val noteDescription :String,@ColumnInfo(name = "timestamp")val timeStamp :String) {
    //Generating an id that we can use as a key when searching for objects
    @PrimaryKey(autoGenerate = true) var id = 0
}