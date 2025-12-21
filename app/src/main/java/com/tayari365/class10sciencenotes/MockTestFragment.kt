package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MockTestFragment : BaseFragment() {

    private val topics = listOf(
        McqTopic(1, "Scientific Study"),
        McqTopic(2, "Classification of Living Beings"),
        McqTopic(3, "Life Cycle"),
        McqTopic(4, "Heredity"),
        McqTopic(5, "Physiological Structure and Life Process"),
        McqTopic(6, "Nature and Environment"),
        McqTopic(7, "Force and Motion"),
        McqTopic(8, "Pressure"),
        McqTopic(9, "Heat"),
        McqTopic(10, "Wave"),
        McqTopic(11, "Electricity and Magnetism"),
        McqTopic(12, "The Universe"),
        McqTopic(13, "ICT"),
        McqTopic(14, "Classification of Elements"),
        McqTopic(15, "Chemical Reactions"),
        McqTopic(16, "Gases"),
        McqTopic(17, "Metals"),
        McqTopic(18, "Hydrocarbons and Their Compounds"),
        McqTopic(19, "Materials Used in Daily Life")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mock_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = McqTopicsAdapter(topics) { topicId, position ->
            navigateToQuizLoaderActivity(topicId, position)
        }
    }

    private fun navigateToQuizLoaderActivity(topicId: Int, position: Int) {
        val intent = Intent(requireContext(), QuizLoaderActivity::class.java)
        intent.putExtra("QUIZ_POSITION", position)
        intent.putExtra("QUIZ_NAME", topics[position].title)
        startActivity(intent)
    }
}

