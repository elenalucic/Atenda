package hr.ferit.elenalu.primjer_atenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

enum class ItemClickType {
    REMOVE,
    EDIT
}

class ClassAdapter(val items:ArrayList<ClassItem>, val listener: ContentListener, val listener1: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): RecyclerView.ViewHolder {
        return ClassViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.class_recycler_view, parent, false)
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position:Int) {
        when(holder){
            is ClassViewHolder->{
                holder.bind(position,items[position],listener)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(index: Int){
        items.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,items.size)
    }

    fun addItem(classItem : ClassItem){
        items.add(0, classItem)
        notifyItemInserted(0)
    }


    inner class ClassViewHolder(private val view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        val nameClass=view.findViewById<TextView>(R.id.class_tv)
        private val nameSubject=view.findViewById<TextView>(R.id.subject_tv)

        private val deleteClass=view.findViewById<ImageButton>(R.id.deleteButton)



        fun bind(index: Int, classItem: ClassItem, listener: ContentListener){

            nameClass.setText(classItem.className)
            nameSubject.setText(classItem.subjectName)


           deleteClass.setOnClickListener{
                listener.onItemButtonClick(index,classItem,ItemClickType.REMOVE)
            }
        }


        init {
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position=adapterPosition
            listener1.onItemClick(position,items)

        }


    }
    interface ContentListener{
        fun onItemButtonClick(index: Int, classItem: ClassItem, clickType: ItemClickType)

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,list:ArrayList<ClassItem>)
    }



}