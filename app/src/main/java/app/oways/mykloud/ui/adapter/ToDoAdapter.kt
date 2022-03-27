package app.oways.mykloud.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.oways.mykloud.R
import app.oways.mykloud.data.remote.ToDoItem
import kotlinx.android.synthetic.main.todo_list_item.view.*
import kotlin.collections.ArrayList

class ToDoAdapter (private val deleteToDo: (String) -> Unit
): RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    private var list: ArrayList<ToDoItem>? = null
    private var selectedList: ArrayList<Long> = ArrayList()
    //private var sortBy: SortBy = SortBy.NON

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(list?.get(position), deleteToDo)
    }

    override fun getItemCount() = list?.size ?: 0

    fun addList(data: ArrayList<ToDoItem>) {
        list = data
        list?.reverse()
        //Collections.sort(list, Collections.reverseOrder())
       // Collections.sort(list, DestinationComparator(sortBy))
        this.notifyDataSetChanged()
    }
   /* fun addSelected(list: ArrayList<Long>) {
        selectedList = list
    }

    fun setSortBy(sort: SortBy) {
        sortBy = sort
        list?.let { addList(it) }
    }*/

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: ToDoItem?,
            deleteToDo: (String) -> Unit
        ) {
            with(itemView) {
                item?.apply {
                    todo_id_tv?.text = id
                    todo_name_tv?.text = name
                }
                setOnClickListener {
                    item?.id?.let { it1 -> deleteToDo(it1) }
                }
            }
        }
    }
}
