package com.example.sobesgbusmmap.reddApp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.databinding.RedditItemBinding
import com.example.sobesgbusmmap.filmApp.utils.lastAfter
import com.example.sobesgbusmmap.reddApp.model.dataTransferObject.InnerChildrenData

class RedditPagerAdapter :
    PagingDataAdapter<InnerChildrenData, RedditPagerAdapter.RedditViewHolder>(RedditComparator) {

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {

        val redditData = getItem(position)!!
        holder.view.apply {
            tvSelfText.text = redditData.innerData.selfText
            tvScore.text = "${redditData.innerData.score}"
            tvComments.text = "${redditData.innerData.numberOfComments}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RedditViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RedditItemBinding.inflate(inflater, parent, false)
        getLastName()
        return RedditViewHolder(binding)
    }

    private fun getLastName() {
        val redditData = getItem(this.itemCount - 1)
        if (redditData != null) {
            lastAfter = redditData.innerData.name
        }
    }

    class RedditViewHolder(val view: RedditItemBinding) : RecyclerView.ViewHolder(view.root)

    object RedditComparator : DiffUtil.ItemCallback<InnerChildrenData>() {
        override fun areItemsTheSame(
            oldItem: InnerChildrenData,
            newItem: InnerChildrenData
        ): Boolean {
            return oldItem.innerData == newItem.innerData
        }

        override fun areContentsTheSame(
            oldItem: InnerChildrenData,
            newItem: InnerChildrenData
        ): Boolean {
            return oldItem == newItem
        }

    }
}