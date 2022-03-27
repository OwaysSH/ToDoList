package app.oways.mykloud.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.oways.mykloud.R
import app.oways.mykloud.other.gone
import app.oways.mykloud.other.visible
import kotlinx.android.synthetic.main.list_item_load_state.view.*

sealed class LoadStateViewHolder(
    val parent: ViewGroup,
    val retry: () -> Unit
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_load_state, parent, false)) {

    fun bind() {
        when (this) {
            is Error -> {
                itemView.progress_bar?.gone()
                /*itemView.image_view_retry?.apply {
                    setOnClickListener {
                        retry()
                        gone()
                    }
                    visible()
                }*/
            }
            is Loading -> {
                retry()
                itemView.apply {
                    progress_bar?.visible()
                   // image_view_retry?.gone()
                }
            }
            else -> {
                itemView.apply {
                    progress_bar?.gone()
                   // image_view_retry?.gone()
                }
            }
        }
    }

    class Loading(parent: ViewGroup, retry: () -> Unit) : LoadStateViewHolder(parent, retry)
    class NotLoading(parent: ViewGroup, retry: () -> Unit) : LoadStateViewHolder(parent, retry)
    class Error(parent: ViewGroup, retry: () -> Unit) : LoadStateViewHolder(parent, retry)

}