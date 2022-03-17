package com.gawel.babyjudas.featureMain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.gawel.babyjudas.R
import com.gawel.babyjudas.core.base.BaseFragment
import com.gawel.babyjudas.core.models.State
import com.gawel.babyjudas.databinding.FragmentChooseAppBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentChooseAppBinding>() {
    private val viewModel by viewModels<MainFViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.sampleData.collect { event ->
                when (event) {
                    is State.Loading -> {
                        binding.sampleText.text = "Loading..."
                    }
                    is State.Success -> {
                        binding.sampleText.text = event.data
                    }
                    is State.Error -> {
                        binding.sampleText.text = getString(R.string.cannot_download_quote)
                    }

                    else -> {}
                }
            }
        }

        binding.btnCamera.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCameraFragment();
            NavHostFragment.findNavController(this)
                .navigate(action)
        }
        binding.btnWatch.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToWatchFragment();
            NavHostFragment.findNavController(this)
                .navigate(action)
        }

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseAppBinding {
        return FragmentChooseAppBinding.inflate(inflater, container, false)
    }
}