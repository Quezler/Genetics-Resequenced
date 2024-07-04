package dev.aaronhowser.mods.geneticsresequenced.compatibility.emi

import dev.aaronhowser.mods.geneticsresequenced.compatibility.emi.recipe.blood_purifier.PurifySyringe
import dev.aaronhowser.mods.geneticsresequenced.compatibility.emi.recipe.cell_analyzer.OrganicMatterToCell
import dev.aaronhowser.mods.geneticsresequenced.item.EntityDnaItem
import dev.aaronhowser.mods.geneticsresequenced.registry.ModBlocks
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack

@EmiEntrypoint
class ModEmiPlugin : EmiPlugin {

    companion object {
        val BLOOD_PURIFIER_STACK: EmiStack = EmiStack.of(ModBlocks.BLOOD_PURIFIER)
        val BLOOD_PURIFIER_CATEGORY: EmiRecipeCategory =
            EmiRecipeCategory(OtherUtil.modResource("blood_purifier"), BLOOD_PURIFIER_STACK)

        val CELL_ANALYZER_STACK: EmiStack = EmiStack.of(ModBlocks.CELL_ANALYZER)
        val CELL_ANALYZER_CATEGORY: EmiRecipeCategory =
            EmiRecipeCategory(OtherUtil.modResource("cell_analyzer"), CELL_ANALYZER_STACK)

    }

    override fun register(registry: EmiRegistry) {
        bloodPurifier(registry)
        cellAnalyzer(registry)
    }

    private fun bloodPurifier(registry: EmiRegistry) {
        registry.addCategory(BLOOD_PURIFIER_CATEGORY)
        registry.addWorkstation(BLOOD_PURIFIER_CATEGORY, BLOOD_PURIFIER_STACK)

        registry.addRecipe(PurifySyringe(isMetal = false))
        registry.addRecipe(PurifySyringe(isMetal = true))
    }

    private fun cellAnalyzer(registry: EmiRegistry) {
        registry.addCategory(CELL_ANALYZER_CATEGORY)
        registry.addWorkstation(CELL_ANALYZER_CATEGORY, CELL_ANALYZER_STACK)

        for (entityType in EntityDnaItem.validEntityTypes) {
            registry.addRecipe(OrganicMatterToCell(entityType))
        }

    }

}