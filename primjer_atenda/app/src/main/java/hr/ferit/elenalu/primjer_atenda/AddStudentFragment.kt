package hr.ferit.elenalu.primjer_atenda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddStudentFragment : Fragment(){




    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)


        val addButton = view.findViewById<Button>(R.id.add_student_btn)
        val cancelButton = view.findViewById<Button>(R.id.cancel_student_btn)

        val name = view.findViewById<EditText>(R.id.name_edt)
        val surname = view.findViewById<EditText>(R.id.surname_edt)


        addButton.setOnClickListener {
            val studentName=name.text.toString()
            val studentSurname=surname.text.toString()
            setFragmentResult("requestKey1", bundleOf("name" to studentName, "surname" to studentSurname))

            activity?.supportFragmentManager?.popBackStack()
        }

        cancelButton.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()

        }



        return view
    }


}