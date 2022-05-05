package notesClasses

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.healthyorg.android.healthyapp.R
import java.util.*

class NotesActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    //Initializing our recycler view, edit text, button, and viewModel.
    lateinit var viewModal: NotesListViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)


        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)


        notesRV.layoutManager = LinearLayoutManager(this)

        //Initializing our adapter
        val noteRVAdapter = NoteRVAdapter(this, this, this)

        //Setting our adapter to the recyclerView
        notesRV.adapter = noteRVAdapter

        //Initializing our ViewModel
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NotesListViewModal::class.java)

        viewModal.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        })
        //A listener to open a new page where someone can add a new note
        addFAB.setOnClickListener {
            val intent = Intent(this@NotesActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    //Opening a new intent and passing the necessary data to it from the database
    override fun onNoteClick(note: Note) {
        val intent = Intent(this@NotesActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    //Deleting a note
    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
    }
}