package hr.ferit.elenalu.primjer_atenda

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNotesFragment : Fragment(), NotesAdapter.ContentListener,NotesAdapter.OnItemClickListener{

    private val db= Firebase.firestore
    private lateinit var recyclerAdapter: NotesAdapter
    val recyclerView =view?.findViewById<RecyclerView>(R.id.notes_recyclerView)


    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add_notes, container, false)


        val save =view.findViewById<ImageButton>(R.id.addNotes)


        val notesName= view.findViewById<TextView>(R.id.notes_name_edit)
        val addNote=view.findViewById<TextView>(R.id.add_some_notes)
        val backBtn=view.findViewById<ImageButton>(R.id.backBtn)


        db.collection("notes").get()
            .addOnSuccessListener {
                val notes= ArrayList<Notes>()
                for (data in it.documents) {
                    val note = data.toObject(Notes :: class.java)
                    if(note != null) {
                        note.id = data.id
                        notes.add(note)
                    }
                }

                recyclerAdapter = NotesAdapter(notes, this@AddNotesFragment,this@AddNotesFragment)

                recyclerView?.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }

            }
            .addOnFailureListener {
                Log.e("Main activity", it.message.toString())
            }




        save.setOnClickListener{
            val  note= Notes()
            note.notesName = notesName.text.toString()
            note.addNote= addNote.text.toString()


            db.collection("notes")
                .add(note)
                .addOnSuccessListener {
                    note.id = it.id
                    recyclerAdapter.addItem(note)



                }
            val bundle = Bundle()
            val fragment = NotesFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class, fragment)
            fragmentTransaction?.commit()
        }

        backBtn.setOnClickListener{

            val bundle = Bundle()
            val fragment = NotesFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class, fragment)
            fragmentTransaction?.commit()

        }



        return view
    }

    override fun onItemButtonClick(index: Int, notes: Notes, clickType: ItemClickType) {
        Log.d("Main activity", clickType.toString())
    }

    override fun onItemClick(position: Int, list: ArrayList<Notes>) {
        val item = list[position]
        Log.d("onItemClick", "You clicked on ${item.notesName}")
    }




}