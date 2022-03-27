package app.oways.mykloud.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.oways.mykloud.R
import app.oways.mykloud.data.remote.DataState
import app.oways.mykloud.other.gone
import app.oways.mykloud.other.visible
import app.oways.mykloud.ui.ToDoViewModel
import app.oways.mykloud.ui.adapter.ToDoLoadStateAdapter
import app.oways.mykloud.ui.adapter.ToDoPagingDataAdapter
import app.oways.mykloud.ui.callback.IFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty_list_layout.*
import kotlinx.android.synthetic.main.fragment_to_do.*
import kotlinx.android.synthetic.main.progress_loading.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoFragment : Fragment(R.layout.fragment_to_do), IFragment {

    private val viewModel: ToDoViewModel by viewModels()
    private var todoAdapter: ToDoPagingDataAdapter? = null
    private var recyclerListStatus: Bundle? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (todoAdapter == null) {
            showProgress()
            initView()
            viewModel.toDoCount.observe(viewLifecycleOwner, {
                if (it == 0){
                    viewModel.getToDoList()
                }
            })
        } else {
            hideProgress()
            restoreRecyclerState()
        }
        observers()
    }

    private fun initView() {
        add_todo_container?.setOnClickListener {
            viewModel.insertToDoItem()
        }
        initAdapter()
    }

    private fun observers() {

        viewModel.toDoLiveData.observe(viewLifecycleOwner, { dataState->
            when(dataState){
                is DataState.Success->{
                    hideProgress()
                    empty_container?.gone()
                    lifecycleScope.launch {
                        dataState.data.collectLatest { pagingData ->
                            todoAdapter?.submitData(pagingData)
                        }
                    }
                }
                is DataState.Empty ->{
                    hideProgress()
                    empty_container?.visible()
                }
                is DataState.Loading ->{
                    showProgress()
                }
            }
        })
      /*  viewModel.todoList.observe(viewLifecycleOwner, { event ->
            event?.getContentIfNotHandled().let {dataState->
                when (dataState) {
                    is DataState.Success -> {
                        empty_container?.gone()
                        dataState.data?.let { list ->
                            hideProgress()
                            destinationAdapter?.submitData(pagingData)

                        }
                    }
                    is DataState.Empty ->{
                        empty_container?.visible()
                    }
                    is DataState.GenericError -> {
                        hideProgress()
                    }
                    is DataState.NetworkError -> {
                        hideProgress()
                    }
                }
            }

        })*/
       /* viewModel.selectedDestinationsLiveData.observe(viewLifecycleOwner, {

            it?.map { it ->
                it.id?.let { id -> selectedList[id] = it }
            }
            destinationAdapter?.addSelected(ArrayList(selectedList.keys))
            setCustomToolbar()

        })
        viewModel.destinations.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<DestinationResponse?> -> {
                    dataState.data?.destinationList?.let { list ->
                        hideProgress()
                        destinationAdapter?.addList(list)
                    }
                }
                is DataState.GenericError -> {
                    hideProgress()
                }
                is DataState.NetworkError -> {
                    hideProgress()
                }
            }
        })*/

    }
/*

    private fun setRecyclerViewAdapter() {
        todo_recycler_view?.apply {
            this.adapter = todoAdapter
        }
    }

    private fun initAdapter() {
        todoAdapter = ToDoAdapter{ id->
            viewModel.removeToDoItem(id)
        }
    }
*/

    private fun initAdapter() {
        if (todoAdapter == null) {

            todoAdapter = ToDoPagingDataAdapter{ toDoId->
                viewModel.removeToDoItem(toDoId)
            }
            todo_recycler_view?.apply {
                adapter = todoAdapter?.withLoadStateHeaderAndFooter(
                    header = ToDoLoadStateAdapter(todoAdapter!!::retry),
                    footer = ToDoLoadStateAdapter(todoAdapter!!::retry)
                )
            }
            getToDoList()
        } else {
            restoreRecyclerState()
        }
    }

  /*  private fun restoreRecyclerState() {
        if (recyclerListStatus != null) {
            todo_recycler_view?.layoutManager?.onRestoreInstanceState(
                recyclerListStatus?.getParcelable(
                    "KEY_RECYCLER_STATE"
                )
            )
            todo_recycler_view?.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }
        }
    }*/

    private fun restoreRecyclerState() {
        if (recyclerListStatus != null) {
            todo_recycler_view?.layoutManager?.onRestoreInstanceState(
                recyclerListStatus?.getParcelable(
                    "KEY_RECYCLER_STATE"
                )
            )
            todo_recycler_view?.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                this.adapter = todoAdapter?.withLoadStateHeaderAndFooter(
                    header = ToDoLoadStateAdapter(
                        todoAdapter!!::retry
                    ),
                    footer = ToDoLoadStateAdapter(
                        todoAdapter!!::retry
                    )
                )
            }
        }
    }


    private fun getToDoList() {
        viewModel.getLocalToDoList()
    }

    override fun onPause() {
        super.onPause()
        recyclerListStatus = Bundle()
        val listState = todo_recycler_view?.layoutManager?.onSaveInstanceState()
        recyclerListStatus?.putParcelable("KEY_RECYCLER_STATE", listState)
    }

    override fun showProgress() {
        pb_loading_layout?.visible()
    }

    override fun hideProgress() {
        pb_loading_layout?.gone()
    }

    override fun onBack() {
        requireActivity().finish()
    }

}