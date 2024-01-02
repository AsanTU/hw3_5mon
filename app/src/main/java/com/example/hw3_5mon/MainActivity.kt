package com.example.hw3_5mon

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw3_5mon.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var adapter = ImageAdapter(arrayListOf())
    var page = 1
    var isLoading = false
    var isLastPage = false
    var currentPage = 1
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
        scrollListener()
    }

    private fun initClickers() {
        with(binding) {
            btnNextPage.setOnClickListener {
                page++
                getImages(page)
            }
            btnSearch.setOnClickListener {
                getImages(1)
            }
        }
    }

    private fun ActivityMainBinding.getImages(page: Int) {
        progressView.isVisible = true
        RetrofitService().api.getImages(wordEd.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(
                    call: Call<PixaModel>,
                    response: Response<PixaModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            progressView.isVisible = false
                            adapter.addNewImages(it.hits as ArrayList<ImageModel>)
                        }
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun scrollListener() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE
                    ) {
                        loadMoreItems()
                    }
                }
            }
        })
    }

    private fun loadMoreItems() {
        isLoading = true
        currentPage++
        binding.getImages(currentPage)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}