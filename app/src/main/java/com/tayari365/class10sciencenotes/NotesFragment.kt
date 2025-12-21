package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tayari365.class10sciencenotes.databinding.FragmentNotesBinding

class NotesFragment : BaseFragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.physicsLayout.setOnClickListener {
            navigateToNotesLoader(PhysicsNotesLoaderActivity::class.java)
        }

        binding.chemistryLayout.setOnClickListener {
            navigateToNotesLoader(ChemistryNotesLoaderActivity::class.java)
        }

        binding.biologyLayout.setOnClickListener {
            navigateToNotesLoader(BiologyNotesLoaderActivity::class.java)
        }
    }

    private fun navigateToNotesLoader(activityClass: Class<*>) {
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
