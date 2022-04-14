package com.example.spotifymultithread.ui.search_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotifymultithread.R
import com.example.spotifymultithread.databinding.ItemTrackBinding
import com.example.spotifymultithread.model.Track

class TrackAdapter(
    private var tracks: List<Track>,
    private var onClick: (href: String) -> Unit,
) : RecyclerView.Adapter<TrackAdapter.Holder>() {

    fun setItems(data: List<Track>) {
        tracks = data
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {

        private fun getColors(id: Int) : Int {
            binding.root.context.resources.run {
                return when (id) {
                    0 -> getColor(R.color.track_id_1)
                    1 -> getColor(R.color.track_id_2)
                    else -> getColor(R.color.track_id_3)
                }
            }
        }

        fun bind(track: Track) {
            binding.run {
                root.setBackgroundColor(getColors(track.threadId))
                root.setOnClickListener { onClick(track.href) }
                tvInfo.text = track.href
                Glide.with(binding.root.context)
                    .load(track.album.images.find { it.height == 300 }?.url)
                    .error(R.drawable.ic_baseline_panorama_24)
                    .into(ivIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding =
            ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(itemBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

}