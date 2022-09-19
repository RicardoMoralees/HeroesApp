package com.ricardo.heroesapp.ui.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ricardo.heroesapp.databinding.FragmentMainBinding
import com.ricardo.heroesapp.ui.view.main.MainFragmentDirections.Companion.actionMainFragmentToHeroDetailFragment
import com.ricardo.heroesapp.ui.view.main.adapters.HeroAdapter
import com.ricardo.heroesapp.ui.viewmodel.HeroViewModel

class MainFragment : Fragment() {

    private val heroViewModel: HeroViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var heroAdapter: HeroAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        heroViewModel.getAllHeroes(0)
    }

    private fun initViews() {
        heroAdapter = HeroAdapter({ heroId ->
            findNavController().navigate(actionMainFragmentToHeroDetailFragment(heroId))
        },
            { position: Int ->
                heroViewModel.getAllHeroes(position)
            })
        binding.rvHeroes.adapter = heroAdapter
    }

    private fun initObservers() {
        heroViewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.progressHeroes.isVisible = isVisible
        }
        heroViewModel.heroesList.observe(viewLifecycleOwner) { heroes ->
            heroAdapter.addAll(heroes)
        }
    }

}