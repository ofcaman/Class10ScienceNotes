package com.tayari365.class10sciencenotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialFadeThrough
import com.tayari365.class10sciencenotes.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupLinks()
    }

    private fun setupUI() {
        // Set app version dynamically
        val versionName = requireContext().packageManager
            .getPackageInfo(requireContext().packageName, 0).versionName
        binding.tvAppVersion.text = getString(R.string.version_format, versionName)

        // Load banner with Glide
        Glide.with(this)
            .load(R.drawable.image2)
            .centerCrop()
            .into(binding.ivAppBanner)

        // Fade animations
        binding.cardAppInfo.alpha = 0f
        binding.cardDescription.alpha = 0f
        binding.cardDevelopers.alpha = 0f
        binding.cardLinks.alpha = 0f

        binding.cardAppInfo.animate().alpha(1f).setDuration(300).setStartDelay(300).start()
        binding.cardDescription.animate().alpha(1f).setDuration(300).setStartDelay(500).start()
        binding.cardDevelopers.animate().alpha(1f).setDuration(300).setStartDelay(700).start()
        binding.cardLinks.animate().alpha(1f).setDuration(300).setStartDelay(900).start()
    }

    private fun setupLinks() {
        // YouTube
        binding.btnYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@Tayari365"))
            startActivity(intent)
        }

        // Website
        binding.btnWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tayari365.com"))
            startActivity(intent)
        }

        // Privacy Policy
        binding.btnPrivacyPolicy.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://tayari365.com/tayari365-class-10-science-app-privacy-policy/")
            )
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
