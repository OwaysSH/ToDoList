package app.oways.mykloud.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.oways.mykloud.R
import app.oways.mykloud.data.local.entity.ToDoEntity
import kotlinx.android.synthetic.main.todo_list_item.view.*

class ToDoPagingDataAdapter (private val removeItemById: (id: String) -> Unit) :
    PagingDataAdapter<ToDoEntity, ToDoPagingDataAdapter.ToDoViewHolder>(
        COMPARATOR
    ) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ToDoEntity>() {
            override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ToDoEntity,
                newItem: ToDoEntity
            ) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            removeItemById
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ToDoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_list_item, parent, false)
        )

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: ToDoEntity?,
            removeItemById: (id: String) -> Unit
        ) {
            with(itemView) {
                item?.apply {
                    todo_id_tv?.text = id
                    todo_name_tv?.text = name
                }
                remove_image_view?.setOnClickListener {
                    item?.id?.let { it1 -> removeItemById(it1) }
                }
            }
        }
    }

}