package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tayari365.class10sciencenotes.databinding.FragmentLecturesBinding

class LecturesFragment : BaseFragment() {

    private var _binding: FragmentLecturesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLecturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Click listener for Physics
        binding.root.findViewById<View>(R.id.physics_layout).setOnClickListener {
            val intent = Intent(activity, PhysicsNotesLoaderActivity::class.java)
            intent.putExtra("FROM_LECTURES", true)
            startActivity(intent)
        }

        // Click listener for Chemistry
        binding.root.findViewById<View>(R.id.chemistry_layout).setOnClickListener {
            val intent = Intent(activity, ChemistryNotesLoaderActivity::class.java)
            intent.putExtra("FROM_LECTURES", true)
            startActivity(intent)
        }

        // Click listener for Biology
        binding.root.findViewById<View>(R.id.biology_layout).setOnClickListener {
            val intent = Intent(activity, BiologyNotesLoaderActivity::class.java)
            intent.putExtra("FROM_LECTURES", true)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = LecturesFragment()
    }
}

