package dev.aaronhowser.mods.geneticsresequenced.recipe.base

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput

class IncubatorRecipeInput(
    private val topItem: ItemStack,
    private val bottomItem: ItemStack,
    val isHighTemp: Boolean
) : RecipeInput {

    val isLowTemp: Boolean
        get() = !this.isHighTemp

    fun getTopItem(): ItemStack {
        return topItem.copy()
    }

    fun getBottomItem(): ItemStack {
        return bottomItem.copy()
    }

    override fun getItem(index: Int): ItemStack {
        return when (index) {
            0 -> topItem.copy()
            1 -> bottomItem.copy()
            else -> error("Invalid index $index")
        }
    }

    override fun size(): Int = 2
}