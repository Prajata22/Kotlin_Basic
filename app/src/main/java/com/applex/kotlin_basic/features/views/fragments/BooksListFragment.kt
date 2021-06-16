package com.applex.kotlin_basic.features.views.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.FragmentBooksListBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.models.BookDetailsModel
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.features.views.adapters.BooksAdapter
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.PreferenceManager
import kotlinx.android.synthetic.main.fragment_books_list.*
import javax.inject.Inject

/**
 * Created by Prajata on 25 May 2021
 */
class BooksListFragment : Fragment() {

    @Inject
    lateinit var commonUtils: CommonUtils

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var binding: FragmentBooksListBinding
    private lateinit var booksViewModel: BooksViewModel

    private val booksModelList: ArrayList<BookDetailsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_books_list, container, false)

        booksViewModel = ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)

        val booksComponent: BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (requireActivity().application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        commonUtils.settingImage(R.drawable.no_data, binding.noData)
        commonUtils.settingImage(R.drawable.error, binding.error)

        binding.shimmerLayout.isVisible = true
        binding.shimmerLayout.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)
        showErrorData()

        binding.swipeRefresh
            .setColorSchemeColors(
                ContextCompat.getColor(requireActivity(), R.color.purple_500),
                ContextCompat.getColor(requireActivity(), R.color.purple_700)
            )

        binding.swipeRefresh.setOnRefreshListener {
            booksModelList.clear()
            Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)
        }
    }

    private fun buildRecyclerView() {
        booksViewModel.getBooksList().observe(requireActivity(), {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.error.visibility = View.GONE
            binding.order.setSelection(preferenceManager.getSortOrder()!!)
            booksModelList.addAll(it.data)
            sortingList(booksModelList, binding.order.selectedItem.toString())
        })
    }

    private fun showErrorData() {
        booksViewModel.getErrorData().observe(requireActivity()) { throwable ->
            when {
                throwable != null && throwable.message?.isNotEmpty() == true -> commonUtils.showToast(
                    throwable.message!!
                )
                else -> commonUtils.showToast("Something went wrong")
            }

            binding.mainLayout.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.white
                )
            )
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.orderByLayout.visibility = View.GONE
            binding.recyclerList.visibility = View.GONE
            binding.noData.visibility = View.GONE
            binding.error.visibility = View.VISIBLE
        }
    }

    private fun sortingList(booksModelList: ArrayList<BookDetailsModel>, orderBy: String) {
        when {
            booksModelList.size > 0 -> {
                binding.mainLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.grey
                    )
                )
                binding.noData.visibility = View.GONE
                binding.orderByLayout.visibility = View.VISIBLE
                binding.recyclerList.visibility = View.VISIBLE

                when (orderBy) {
                    "Book Name" -> booksModelList.sortWith { model1: BookDetailsModel, model2: BookDetailsModel ->
                        model1.book_name.toString().compareTo(model2.book_name.toString())
                    }
                    "Author" -> booksModelList.sortWith { model1: BookDetailsModel, model2: BookDetailsModel ->
                        model1.author.toString().compareTo(model2.author.toString())
                    }
                    "Category" -> booksModelList.sortWith { model1: BookDetailsModel, model2: BookDetailsModel ->
                        model1.category.toString().compareTo(model2.category.toString())
                    }
                    "Publish Date" -> booksModelList.sortWith { model1: BookDetailsModel, model2: BookDetailsModel ->
                        model1.publish_date.toString().compareTo(model2.publish_date.toString())
                    }
                    "Page Count" -> booksModelList.sortWith { model1: BookDetailsModel, model2: BookDetailsModel ->
                        model1.page_count.toString().compareTo(model2.page_count.toString())
                    }
                }

                val adapter = BooksAdapter(
                    requireActivity(),
                    booksModelList,
                    booksViewModel
                )
                binding.recyclerList.adapter = adapter
                when {
                    binding.swipeRefresh.isRefreshing -> binding.swipeRefresh.isRefreshing = false
                }

                binding.order.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        preferenceManager.setSortOrder(position)
                        order.setSelection(preferenceManager.getSortOrder()!!)
                        sortingList(booksModelList, order.selectedItem.toString())
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
            else -> {
                binding.mainLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
                binding.orderByLayout.visibility = View.GONE
                binding.recyclerList.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val searchView = item.actionView as SearchView
                searchView.queryHint = "Search"

                val arrayList: ArrayList<BookDetailsModel> = ArrayList()

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        arrayList.clear()
                        when {
                            !newText.isNullOrEmpty() -> {
                                booksModelList.forEach {
                                    when {
                                        it.book_name?.contains(
                                            newText,
                                            false
                                        ) == true -> arrayList.add(it)
                                    }
                                }
                            }
                            else -> {
                                arrayList.addAll(booksModelList)
                            }
                        }
                        sortingList(arrayList, binding.order.selectedItem.toString())

                        binding.swipeRefresh
                            .setColorSchemeColors(
                                ContextCompat.getColor(requireActivity(), R.color.purple_500),
                                ContextCompat.getColor(requireActivity(), R.color.purple_700)
                            )

                        binding.swipeRefresh.setOnRefreshListener {
                            sortingList(arrayList, binding.order.selectedItem.toString())
                        }

                        return false
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }
}