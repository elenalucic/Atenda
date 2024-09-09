package hr.ferit.elenalu.primjer_atenda

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResult


class AddClassFragment : Fragment(), ClassAdapter.ContentListener,ClassAdapter.OnItemClickListener {
    @SuppressLint("SuspiciousIndentation")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add_class, container, false)


        val addButton =view.findViewById<Button>(R.id.add_btn)
        val cancelButton= view.findViewById<Button>(R.id.cancel_btn)

        val className= view.findViewById<EditText>(R.id.class_edt)
        val subjectName=view.findViewById<EditText>(R.id.subject_edt)







        addButton.setOnClickListener{
            val classname = className.text.toString()
            val subjectname = subjectName.text.toString()



            setFragmentResult("requestKey", bundleOf("className" to classname, "subjectName" to subjectname))
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class, HomeFragment())?.commit()
            }


        cancelButton.setOnClickListener{

            val bundle = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = bundle
            val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class, fragment)
            fragmentTransaction?.commit()

        }



        return view
    }

   override fun onItemButtonClick(index: Int, classItem: ClassItem, clickType: ItemClickType) {
       Log.d("Main activity", clickType.toString())
    }

    override fun onItemClick(position: Int, list: ArrayList<ClassItem>) {
            val item = list[position]
            Log.d("onItemClick", "You clicked on ${item.className}")
        }
    }


