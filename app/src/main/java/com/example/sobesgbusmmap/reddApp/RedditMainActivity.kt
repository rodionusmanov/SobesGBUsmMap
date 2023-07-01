package com.example.sobesgbusmmap.reddApp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.RedditActivityMainBinding
import com.example.sobesgbusmmap.reddApp.model.RedditRepository
import com.example.sobesgbusmmap.reddApp.model.retrofit.RedditRetrofitService
import com.example.sobesgbusmmap.reddApp.view.RedditPagerAdapter
import com.example.sobesgbusmmap.reddApp.viewModel.RedditViewModel
import com.example.sobesgbusmmap.reddApp.viewModel.RedditViewModelFactory
import kotlinx.coroutines.launch

class RedditMainActivity : AppCompatActivity() {

    private lateinit var viewModel: RedditViewModel
    private val redditAdapter = RedditPagerAdapter()
    private lateinit var binding: RedditActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RedditActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RedditRetrofitService.getInstance()
        val redditRepository = RedditRepository(retrofitService)
        binding.redditRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.redditRecyclerView.adapter = redditAdapter

        viewModel = ViewModelProvider(
            this,
            RedditViewModelFactory(redditRepository)
        )[RedditViewModel::class.java]

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        redditAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progressDialog.visibility = View.VISIBLE
            else {
                binding.progressDialog.visibility = View.INVISIBLE
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                    Log.e("loading exception", it.error.toString())
                }

            }
        }

        lifecycleScope.launch {
            viewModel.getRedditList().observe(this@RedditMainActivity) {
                it?.let {
                    redditAdapter.submitData(lifecycle, it)
                }
            }
        }
    }
}