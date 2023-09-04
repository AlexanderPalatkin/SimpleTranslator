package com.example.simpletranslator.view.history

import android.os.Bundle
import android.view.MenuItem
import com.example.simpletranslator.databinding.ActivityHistoryBinding
import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.view.base.BaseActivity
import com.example.simpletranslator.viewmodel.history.HistoryInteractor
import com.example.simpletranslator.viewmodel.history.HistoryViewModel
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
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }
}
