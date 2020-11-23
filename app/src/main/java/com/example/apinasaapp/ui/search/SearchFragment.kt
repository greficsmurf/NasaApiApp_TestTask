package com.example.apinasaapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apinasaapp.R
import com.example.apinasaapp.adapters.SearchResultRecyclerAdapter
import com.example.apinasaapp.base.BaseFragment
import com.example.apinasaapp.customview.CleanableEditText
import com.example.apinasaapp.databinding.FragmentSearchBinding
import com.example.apinasaapp.util.getResourceByLoadStates
import com.example.apinasaapp.util.navigateSafe
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    private val vm: SearchViewModel by viewModels {
        vmFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )

        val adapter = SearchResultRecyclerAdapter(
            navigateToDetails = {
                findNavController().navigateSafe(SearchFragmentDirections.actionSearchFragmentToImageVideoDetailsFragment(it))
            }
        )

        binding.apply {
            searchRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                setAdapter(adapter)
            }
            lifecycleOwner = viewLifecycleOwner
        }

        vm.pagingData.observe(viewLifecycleOwner, Observer{ pagingData ->
            adapter.submitData(lifecycle, pagingData)
        })
        adapter.addLoadStateListener {
            binding.resource = getResourceByLoadStates(it)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_fragment, menu)
        try {
            val menuItem = menu.findItem(R.id.searchBar)
            val searchView = menuItem.actionView as CleanableEditText

            searchView.apply {
                setOnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        vm.setSearchText(text.toString())
                        true
                    }
                    else
                        false
                }
                addTextChangedListener(
                        object : TextWatcher {
                            private var timer = Timer()
                            private val DELAY = 700L

                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    count: Int,
                                    after: Int
                            ) {
                            }

                            override fun onTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    before: Int,
                                    count: Int
                            ) {
                                timer.cancel()

                                if(s.isNullOrBlank())
                                {
                                    vm.setSearchText(s.toString())
                                }

                                timer = Timer()
                                timer.schedule(
                                        timerTask {
                                            vm.setSearchText(s.toString())
                                        },
                                        DELAY
                                )
                            }

                        }
                )
                isSingleLine = true
                width = Int.MAX_VALUE
                requestFocus()
                toggleKeyBoard()
                setText(vm.searchText)
                selectAll()
            }
        }catch (e: Exception){
            Snackbar.make(requireView(), getString(R.string.search_bar_init_error), Snackbar.LENGTH_LONG).show()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}