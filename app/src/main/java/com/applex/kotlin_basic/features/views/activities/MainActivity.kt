package com.applex.kotlin_basic.features.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.applex.kotlin_basic.R
import com.applex.kotlin_basic.application.KotlinBasicApplication
import com.applex.kotlin_basic.databinding.ActivityMainBinding
import com.applex.kotlin_basic.features.di.BooksComponent
import com.applex.kotlin_basic.features.di.DaggerBooksComponent
import com.applex.kotlin_basic.features.models.BookDetailsModel
import com.applex.kotlin_basic.features.viewModel.BooksViewModel
import com.applex.kotlin_basic.utils.CommonUtils
import com.applex.kotlin_basic.utils.PreferenceManager

import javax.inject.Inject

import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var commonUtils: CommonUtils

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var binding : ActivityMainBinding
    private lateinit var booksViewModel : BooksViewModel

    private val booksModelList : ArrayList<BookDetailsModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        booksViewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        val booksComponent : BooksComponent = DaggerBooksComponent.builder()
            .applicationComponent(
                (application as KotlinBasicApplication)
                    .getApplicationComponent()
            )
            .build()

        booksComponent.inject(this)
        booksComponent.inject(booksViewModel)

        setSupportActionBar(binding.toolbar)

        commonUtils.settingImage(R.drawable.no_data, binding.noData)
        commonUtils.settingImage(R.drawable.error, binding.error)

        Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)
        showErrorData()

        binding.swipeRefresh
            .setColorSchemeColors(
                ContextCompat.getColor(this, R.color.purple_500),
                ContextCompat.getColor(this, R.color.purple_700)
            )

        binding.swipeRefresh.setOnRefreshListener {
            booksModelList.clear()
            Handler(Looper.getMainLooper()).postDelayed({ buildRecyclerView() }, 1000)
        }
    }

    private fun buildRecyclerView() {
        booksViewModel.getBooksList().observe(this, {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.error.visibility = View.GONE
            binding.order.setSelection(preferenceManager.getSortOrder()!!)
            booksModelList.addAll(it.data)
            sortingList(binding.order.selectedItem.toString())
        })
    }

    private fun showErrorData() {
        booksViewModel.getErrorData().observe(this) { throwable ->
            when {
                throwable != null && throwable.message?.isNotEmpty() == true -> commonUtils.showToast(throwable.message!!)
                else -> commonUtils.showToast("Something went wrong")
            }

            binding.mainLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.orderByLayout.visibility = View.GONE
            binding.recyclerList.visibility = View.GONE
            binding.noData.visibility = View.GONE
            binding.error.visibility = View.VISIBLE
        }
    }

    private fun sortingList(orderBy : String) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.search -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}