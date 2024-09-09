package hr.ferit.elenalu.primjer_atenda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_main, container, false)




        val notesBtn = view.findViewById<ImageButton>(R.id.notes)
        val studentBtn= view.findViewById<ImageButton>(R.id.students)

        notesBtn.setOnClickListener{
            val bundle =Bundle()
            val fragment= NotesFragment()
            fragment.arguments=bundle
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.main_activity,fragment)
            fragmentTransaction?.commit()


        }

        studentBtn.setOnClickListener{
            val bundle =Bundle()
            val fragment= HomeFragment()
            fragment.arguments=bundle
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.main_activity,fragment)
            fragmentTransaction?.commit()
        }

        return  view
    }



}
