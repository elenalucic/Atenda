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
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore


class HomeFragment : Fragment(),ClassAdapter.ContentListener,ClassAdapter.OnItemClickListener {

    private val db= Firebase.firestore
    private lateinit var recyclerAdapter :ClassAdapter
    private val classItem = ArrayList<ClassItem>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.class_recyclerView)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val className = bundle.getString("className")
            val subjectName = bundle.getString("subjectName")
            val classItem = ClassItem()

            if (className != null) {
                classItem.className= className
            }
            if (subjectName != null) {
                classItem.subjectName=subjectName
            }

            val doc = db.collection("class")
                .document()
            classItem.id = doc.id
            doc.set(classItem).addOnSuccessListener {
                Log.i("HomeFragment", "Class added.",)
            }
        }
        db.collection("class").get()
            .addOnSuccessListener {
                for (data in it.documents) {
                    val item = data.toObject(ClassItem::class.java)
                    if(item != null) {
                        item.id = data.id
                        classItem.add(item)
                    }
                }

                recyclerAdapter = ClassAdapter(classItem, this@HomeFragment,this@HomeFragment)

                recyclerView.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = recyclerAdapter
                }

            }
            .addOnFailureListener {
                Log.e("Main activity", it.message.toString())
            }

        val addButton : ImageButton = view.findViewById(R.id.addBtn)
        addButton.setOnClickListener{
            val fragment= AddClassFragment()
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)
            fragmentTransaction?.commit()

        }

        val backBtn= view.findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener{
            val fragment= MainFragment()
            val fragmentTransaction : FragmentTransaction?= activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.add_class,fragment)
            fragmentTransaction?.commit()
        }

    return view

    }

    override fun onItemButtonClick(index: Int, classItem: ClassItem, clickType: ItemClickType) {

         if (clickType == ItemClickType.REMOVE) {
            recyclerAdapter.removeItem(index)
            classItem.id?.let {
                db.collection("class")
                    .document(classItem.id)
                    .delete()
            }
        }




    }

    override fun onItemClick(position: Int, list: ArrayList<ClassItem>) {
        val bundle=Bundle()
        val fragment=StudentsFragment()
        bundle.putString("className", classItem[position].className)
        bundle.putString("subjectName",classItem[position].subjectName)
        bundle.putString("id",classItem[position].id)
        fragment.arguments=bundle

        val fragmentTransaction : FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.add_class, fragment)?.addToBackStack(null)
        fragmentTransaction?.commit()
    }





}