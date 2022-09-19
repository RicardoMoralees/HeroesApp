package com.ricardo.heroesapp.ui.view.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ricardo.heroesapp.R
import com.ricardo.heroesapp.data.model.Hero
import com.ricardo.heroesapp.databinding.ItemHeroBinding

class HeroAdapter(
    private val onHeroSelected: (heroId: Int) -> Unit,
    private val onBottomReached: (position: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val heroes: MutableList<Hero> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HeroesViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hero = heroes[position]
        if (position == heroes.size - 1) {
            onBottomReached(heroes.size)
        }
        (holder as HeroesViewHolder).bind(hero)
    }

    override fun getItemCount(): Int = this.heroes.size

    fun addAll(newHeroes: List<Hero>) {
        heroes.addAll(newHeroes)
        notifyDataSetChanged()
    }

    inner class HeroesViewHolder(private val binding: ItemHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: Hero) {
            this.binding.apply {
                tvHeroName.text = hero.name
                cardHero.setOnClickListener {
                    onHeroSelected(hero.id)
                }
                hero.thumbnail.apply {
                    val url = path.plus(DOT).plus(extension)
                    Glide.with(root.context)
                        .load(url)
                        .placeholder(R.drawable.hero_placeholder)
                        .error(R.drawable.oops_error)
                        .into(ivHeroImage)
                }
            }
        }
    }

    companion object {
        private const val DOT = "."
    }
}
