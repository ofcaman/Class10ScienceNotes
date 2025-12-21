package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.tayari365.class10sciencenotes.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(), GridItemAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var dataList: ArrayList<rvModel>

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val imageResIds = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            viewPager.currentItem = (viewPager.currentItem + 1) % imageResIds.size
            handler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupRecyclerView()
        setupGridRecyclerView()
        setupClickListeners()
        setupChatPreviewCard() // <-- Added for Chat with AI
        applyTheme()
    }

    private fun setupViewPager() {
        viewPager = binding.viewPager
        viewPagerAdapter = ViewPagerAdapter(requireContext(), imageResIds, ::handleImageClick)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3
        handler.post(autoScrollRunnable)
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        dataList = ArrayList()
        // Populate chapters
        for (i in 1..19) {
            dataList.add(rvModel(R.drawable.note, "Chapter $i"))
        }

        adapter = Adapter(dataList, requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupGridRecyclerView() {
        val gridRecyclerView: RecyclerView = binding.gridRecyclerView
        gridRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        val gridDataList = ArrayList<GridItemModel>().apply {
            add(GridItemModel(R.drawable.tensetbook, "10Set Book"))
            add(GridItemModel(R.drawable.formulas, "Formulas"))
            add(GridItemModel(R.drawable.mindmaps, "Mind Maps"))
            // add(GridItemModel(R.drawable.impquestions, "Imp Question"))
            // add(GridItemModel(R.drawable.pyps, "PYPs"))
            // add(GridItemModel(R.drawable.pyqs, "Model Questions"))
        }

        val gridAdapter = GridItemAdapter(gridDataList, requireContext(), this)
        gridRecyclerView.adapter = gridAdapter
    }

    private fun setupClickListeners() {
        binding.textView3.setOnClickListener {
            startActivity(Intent(requireContext(), AllChapters::class.java))
        }

        binding.more.setOnClickListener {
            startActivity(Intent(requireContext(), MoreContentActivity::class.java))
        }
    }

    private fun setupChatPreviewCard() {
        val chatPreviewCard: MaterialCardView = binding.chatPreviewCard
        val chatEditText = binding.chatPreviewCard.findViewById<EditText>(R.id.chatEditText)

        val openChatActivity = {
            val intent = Intent(requireContext(), ChatActivity::class.java)
            startActivity(intent)
        }

        chatPreviewCard.setOnClickListener { openChatActivity() }
        chatEditText.setOnClickListener { openChatActivity() }
    }


    private fun handleImageClick(imageResId: Int) {
        when (imageResId) {
            R.drawable.image1 -> Toast.makeText(context, "Image 1 clicked", Toast.LENGTH_SHORT).show()
            R.drawable.image2 -> Toast.makeText(context, "Image 2 clicked", Toast.LENGTH_SHORT).show()
            R.drawable.image3 -> Toast.makeText(context, "Image 3 clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun applyTheme() {
        val isDarkMode = isDarkMode()
        binding.root.setBackgroundColor(
            resources.getColor(
                if (isDarkMode) R.color.background_dark else R.color.background_light,
                null
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(autoScrollRunnable)
    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 -> startActivity(Intent(requireContext(), TenSetLoaderActivity::class.java))
            1 -> {
                val intent = Intent(requireContext(), NotesViewerActivity::class.java).apply {
                    putExtra("SUBJECT_ID", "formulas")
                    putExtra("POSITION", position)
                }
                startActivity(intent)
            }
            2 -> startActivity(Intent(requireContext(), MindMapActivity::class.java))
        }
    }
}
