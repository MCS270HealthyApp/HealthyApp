package notesClasses

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.healthyorg.android.healthyapp.database.NotesDao
import java.util.concurrent.Flow

//This repository class defines the pattern we use to fetch and store
//data in our notes database using LiveData
class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    //A method to insert notes in the database
    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    //A method to delete notes in the database
    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    //A method to update the notes in the database
    suspend fun update(note: Note){
        notesDao.update(note)
    }
}