package hr.ferit.elenalu.primjer_atenda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NotesFragment : Fragment(),NotesAdapter.ContentListener,NotesAdapter.OnItemClickListener{

    private val db= Firebase.firestore
    private lateinit var recyclerAdapter :NotesAdapter
    private val notes= ArrayList<Notes>()



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_notes, container, false)



        val recyclerView = view.findViewById<RecyclerView>(R.id.notes_recyclerView)
        db.collection("notes").get()
            .addOnSuccessListener {
                for (data in it.documents) {
                    val note = data.toObject(Notes :: class.java)
                    if(note != null) {
                        note.id = data.id
                        notes.add(note)
                    }
                }

                recyclerAdapter = NotesAdapter(notes, this@NotesFragment,this@NotesFragment)

                recyclerView.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }

            }
            .addOnFailureListener {
                Log.e("Main activity", it.message.toString())
            }


        val notesAdd : ImageButton = view.findViewById(R.id.notesAdd)
        notesAdd.setOnClickListener{

            val bundle =Bundle()
            val fragment= AddNotesFragment()
            fragment.arguments=bundle
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)
            fragmentTransaction?.commit()

        }

        val backbtn=view.findViewById<ImageButton>(R.id.backBtn)
        backbtn.setOnClickListener{
            val bundle =Bundle()
            val fragment= MainFragment()
            fragment.arguments=bundle
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)
            fragmentTransaction?.commit()

        }


        return view



    }

    override fun onItemButtonClick(index: Int, notes: Notes, clickType: ItemClickType) {

        if (clickType == ItemClickType.REMOVE) {
            recyclerAdapter.removeItem(index)
            notes.id?.let {
                db.collection("notes")
                    .document(notes.id)
                    .delete()
            }
        }
    }

    override fun onItemClick(position: Int, list: ArrayList<Notes>) {
        val bundle=Bundle()
        val fragment=NotesDetailFragment()
        bundle.putString("notesName", notes[position].notesName)
        bundle.putString("noteContent",notes[position].addNote)
        bundle.putString("id",notes[position].id)
        fragment.arguments=bundle

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.add_class, fragment)
        fragmentTransaction?.commit()
    }



}