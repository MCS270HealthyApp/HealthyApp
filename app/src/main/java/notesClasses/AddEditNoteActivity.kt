package notesClasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthyorg.android.healthyapp.R
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

//This class handles all of the stuff for the page where users add, edit, or
//update notes
class AddEditNoteActivity : AppCompatActivity() {
    //Initializing the edit text for the title and for the content of the note,
    //as well as the button to save the note
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    lateinit var viewModal: NotesListViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        //Initializing our viewModel
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NotesListViewModal::class.java)

        //Linking up the variables to their xml counterparts
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        //Getting data that was passed via an intent
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Save Note")
        }

        //The listener for our save button, which either adds a new note object to the database
        //using the title, content and current date, or else updates and existing note
        saveBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()

            //If the note already existed, we just update that note on the database when the user
            //clicks the save button
            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                //If the note actually has stuff written in it, we add it to the database
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())

                    viewModal.addNote(Note(noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, NotesActivity::class.java))
            this.finish()
        }
    }
}