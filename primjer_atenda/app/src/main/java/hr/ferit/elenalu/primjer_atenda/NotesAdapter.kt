package hr.ferit.elenalu.primjer_atenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val notes:ArrayList<Notes>, val listener: ContentListener,val listener1: OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notes_recycler_view, parent, false)
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position:Int) {
        when(holder){
            is NotesViewHolder->{
                holder.bind(position,notes[position],listener)
            }
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun removeItem(index: Int){
        notes.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,notes.size)
    }

    fun addItem(note: Notes){
        notes.add(0, note)
        notifyItemInserted(0)
    }


    inner class NotesViewHolder(private val view: View): RecyclerView.ViewHolder(view),View.OnClickListener{
        val nameNote=view.findViewById<TextView>(R.id.notes_tv)
        private val content=view.findViewById<TextView>(R.id.content_tv)

        private val deleteClass=view.findViewById<ImageButton>(R.id.deleteButton_notes)



        fun bind(index: Int, notes: Notes, listener: ContentListener){

            nameNote.setText(notes.notesName)
            content.setText(notes.addNote)


            deleteClass.setOnClickListener{
                listener.onItemButtonClick(index,notes,ItemClickType.REMOVE)
            }
        }

        init {
            view.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position=adapterPosition
            listener1.onItemClick(position,notes)

        }






    }
    interface ContentListener{
        fun onItemButtonClick(index: Int, notes: Notes, clickType: ItemClickType)

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, list: ArrayList<Notes>)
    }




}