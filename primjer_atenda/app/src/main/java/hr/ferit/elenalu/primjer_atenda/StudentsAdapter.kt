package hr.ferit.elenalu.primjer_atenda

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentsAdapter(val students:ArrayList<Students>, val listener: StudentsAdapter.ContentListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.student_recycler_view, parent, false)
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StudentViewHolder -> {
                holder.bind(position, students[position])
            }
        }


    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun removeItem(index: Int) {
        students.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, students.size)
    }

    fun addItem(student: Students) {
        students.add(0, student)
        notifyItemInserted(0)
    }


    inner class StudentViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val studentName = view.findViewById<TextView>(R.id.name)
        private val studentSurname= view.findViewById<TextView>(R.id.surname)

        private val deleteStudents = view.findViewById<ImageButton>(R.id.deleteButton)


        fun bind(index: Int, students: Students) {

            studentName.text=students.studentName
            studentSurname.text=students.studentSurname

            deleteStudents.setOnClickListener{
                listener.onItemButtonClick(index,students,ItemClickType.REMOVE)
            }


        }


    }

    interface ContentListener{
        fun onItemButtonClick(index: Int, students: Students, clickType: ItemClickType)

    }


}
