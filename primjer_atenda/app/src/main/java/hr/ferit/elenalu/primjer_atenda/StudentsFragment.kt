package hr.ferit.elenalu.primjer_atenda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class StudentsFragment : Fragment() ,StudentsAdapter.ContentListener{

    private val db= Firebase.firestore
    private lateinit var recyclerAdapter :StudentsAdapter

    private lateinit var classId:String
    private lateinit var title:TextView
    private lateinit var subtitle:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_students, container, false)


        subtitle= view.findViewById(R.id.subtitle_toolbar)
        title = view.findViewById(R.id.title_toolbar)

        title.text = arguments?.getString("className").toString()

        subtitle.text = arguments?.getString("subjectName").toString()
        classId = arguments?.getString("id").toString()

        val students = ArrayList<Students>()

        val recyclerView = view.findViewById<RecyclerView>(R.id.student_recyclerView)

        setFragmentResultListener("requestKey1") { requestKey, bundle ->
            val studentName = bundle.getString("name")
            val studentSurname=bundle.getString("surname")
            val student = Students()

            if (studentName != null) {
                student.studentName= studentName
            }
            if (studentSurname != null) {
                student.studentSurname=studentSurname
            }

            val doc = db.collection("class")
                .document(classId)
                .collection("students")
                .document()
            student.id = doc.id
            doc.set(student).addOnSuccessListener {
                Log.i("StudentsFragment", "Student added.")
            }
        }
        db.collection("class").document(classId).collection("students").get()
            .addOnSuccessListener { results->
                for (data in results.documents) {

                    val student = data.toObject(Students::class.java)
                    if(student != null) {
                        student.id = data.id
                        students.add(student)
                    }
                }

                recyclerAdapter = StudentsAdapter(students,this@StudentsFragment)

                recyclerView.apply{
                    layoutManager = LinearLayoutManager(requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false)
                    adapter = recyclerAdapter
                }

            }
            .addOnFailureListener {
                Log.e("Main activity", it.message.toString())
            }



        val backBtn= view.findViewById<ImageButton>(R.id.backBtn)

        backBtn.setOnClickListener{
            val fragment= HomeFragment()
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)
            fragmentTransaction?.commit()
        }

        val addStudent = view.findViewById<ImageButton>(R.id.addStudent)
        addStudent.setOnClickListener{
            val fragment= AddStudentFragment()
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)?.addToBackStack(null)
            fragmentTransaction?.commit()
        }


        return  view
    }

    override fun onItemButtonClick(index: Int, students: Students, clickType: ItemClickType) {

        if (clickType == ItemClickType.REMOVE) {
            recyclerAdapter.removeItem(index)
            students.id?.let {
                db.collection("class").document(classId).collection("students")
                    .document(students.id)
                    .delete()
            }
        }




    }

}


