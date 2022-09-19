package com.ricardo.heroesapp.ui.view.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ricardo.heroesapp.R
import com.ricardo.heroesapp.data.model.Hero
import com.ricardo.heroesapp.databinding.FragmentHeroDetailBinding
import com.ricardo.heroesapp.ui.viewmodel.HeroViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HeroDetailFragment : Fragment() {

    private lateinit var binding: FragmentHeroDetailBinding
    private val args: HeroDetailFragmentArgs by navArgs()
    private val heroViewModel: HeroViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        heroViewModel.getHeroDetail(args.heroId)
    }

    private fun initObservers() {
        heroViewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.progressDetail.isVisible = isVisible
            binding.containerDetail.isVisible = !isVisible
        }
        heroViewModel.heroesList.observe(viewLifecycleOwner) { heroes ->
            setHeroInfo(heroes.first())
        }
    }

    private fun setHeroInfo(hero: Hero) {
        hero.apply {
            binding.tvHeroName.text = name
            val url = thumbnail.path.plus(".").plus(thumbnail.extension)
            Glide.with(requireContext())
                .load(url)
                .placeholder(R.drawable.hero_placeholder)
                .error(R.drawable.oops_error)
                .into(binding.ivHeroImage)

            binding.tvHeroDescription.text = description
            lifecycleScope.launch(Dispatchers.Unconfined) {
                val comicList: List<String> = comics.items.map { item -> item.name }
                setDecoratedListOnTextView(comicList, binding.tvHeroComics)
                val serieList: List<String> = series.items.map { item -> item.name }
                setDecoratedListOnTextView(serieList, binding.tvHeroSeries)
            }
        }
    }

    private fun setDecoratedListOnTextView(list: List<String>, textView: TextView) {
        val stringBuilder = SpannableStringBuilder()
        list.forEach { comic ->
            val string = SpannableString(comic)
            string.setSpan(BulletSpan(15), 0, comic.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            stringBuilder.append("\n")
            stringBuilder.append(string)
        }
        textView.text = stringBuilder
    }
}
