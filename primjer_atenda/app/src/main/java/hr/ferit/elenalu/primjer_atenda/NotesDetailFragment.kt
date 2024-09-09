package hr.ferit.elenalu.primjer_atenda

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction


class NotesDetailFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes_detail, container, false)


        val backBtn =view.findViewById<ImageButton>(R.id.backBtn)
        val title = view.findViewById<TextView>(R.id.title_notes)
        val content=view.findViewById<TextView>(R.id.notes_content)

        title.text = arguments?.getString("notesName").toString()
        content.text = arguments?.getString("noteContent").toString()

        backBtn.setOnClickListener {
            val bundle =Bundle()
            val fragment= NotesFragment()
            fragment.arguments=bundle
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        return view
    }
}

