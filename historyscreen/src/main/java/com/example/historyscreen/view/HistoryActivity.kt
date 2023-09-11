package com.example.historyscreen.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.core.base.BaseActivity
import com.example.descriptionscreen.DescriptionActivity
import com.example.historyscreen.R
import com.example.historyscreen.databinding.ActivityHistoryBinding
import com.example.historyscreen.interactor.HistoryInteractor
import com.example.historyscreen.viewmodel.HistoryViewModel
import com.example.model.AppState
import com.example.model.data.DataModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private lateinit var binding: ActivityHistoryBinding

    override lateinit var model: HistoryViewModel

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionbarHomeButtonAsUp()
        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()

        model.getData("", false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.menu_history_search -> {
                val historySearchDialogFragment = HistorySearchDialogFragment.newInstance()
                historySearchDialogFragment.setOnHistorySearchClickListener(
                    onHistorySearchClickListener
                )
                historySearchDialogFragment.show(
                    supportFragmentManager,
                    BOTTOM_SHEET_FRAGMENT_HISTORY_SEARCH_DIALOG_TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity) {
            renderData(it)
        }
        model.subscribeToSearchWord().observe(this@HistoryActivity) {
            if (it != null) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@HistoryActivity,
                        it.word,
                        it.description!!,
                        it.imageUrl
                    )
                )
            }
        }
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }

    private val onHistorySearchClickListener: HistorySearchDialogFragment.OnHistorySearchClickListener =
        object : HistorySearchDialogFragment.OnHistorySearchClickListener {

            override fun onClick(searchWord: String) {
                if (searchWord.isNotEmpty()) {
                    model.getDataByWord(word = searchWord)
                }
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_HISTORY_SEARCH_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092396"
    }
}