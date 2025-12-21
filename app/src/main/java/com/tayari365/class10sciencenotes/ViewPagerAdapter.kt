package com.tayari365.class10sciencenotes

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

class ViewPagerAdapter(
    private val context: Context,
    private val images: List<Int>, // List of drawable resource IDs
    private val onItemClick: (Int) -> Unit // Pass the clicked image resource ID
) : RecyclerView.Adapter<ViewPagerAdapter.ImageViewHolder>() {

    // ✅ Handler for auto-sliding
    private var viewPager: ViewPager2? = null
    private val handler = Handler(Looper.getMainLooper())
    private val slideRunnable = Runnable {
        viewPager?.let {
            it.currentItem = it.currentItem + 1
        }
        startAutoSlide() // keep sliding
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(imageResId: Int) {
            Glide.with(context)
                .load(imageResId)
                .override(800, 600)
                .fitCenter()
                .into(imageView)

            itemView.setOnClickListener {
                onItemClick(imageResId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val actualPosition = position % images.size
        holder.bind(images[actualPosition])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Infinite scroll

    // ✅ Attach ViewPager2 so adapter can control it
    fun attachToViewPager(vp: ViewPager2) {
        this.viewPager = vp
        startAutoSlide()
    }

    // ✅ Start auto sliding every 3 seconds
    private fun startAutoSlide() {
        handler.removeCallbacks(slideRunnable)
        handler.postDelayed(slideRunnable, 3000) // 3 sec delay
    }

    // ✅ Stop auto sliding when not visible
    fun stopAutoSlide() {
        handler.removeCallbacks(slideRunnable)
    }
}
