package dev.aaronhowser.mods.geneticsresequenced.item

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.api.genes.GeneRegistry
import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.geneticsresequenced.gene.ModGenes
import dev.aaronhowser.mods.geneticsresequenced.gene.ModGenes.getHolder
import dev.aaronhowser.mods.geneticsresequenced.item.components.GeneItemComponent
import dev.aaronhowser.mods.geneticsresequenced.registry.ModDataComponents
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems
import dev.aaronhowser.mods.geneticsresequenced.util.ClientUtil
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil.withColor
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class DnaHelixItem : EntityDnaItem() {

    companion object {

        fun hasGene(itemStack: ItemStack): Boolean {
            return itemStack.has(ModDataComponents.GENE_COMPONENT)
        }

        fun getGeneHolder(itemStack: ItemStack): Holder<Gene>? {
            return itemStack.get(ModDataComponents.GENE_COMPONENT)?.geneHolder
        }

        fun setGeneHolder(itemStack: ItemStack, geneHolder: Holder<Gene>): ItemStack {
            itemStack.set(ModDataComponents.GENE_COMPONENT, GeneItemComponent(geneHolder))
            return itemStack
        }

        fun setBasic(itemStack: ItemStack, registries: HolderLookup.Provider): ItemStack {
            return setGeneHolder(itemStack, ModGenes.BASIC.getHolder(registries)!!)
        }

        fun getAllHelices(registries: HolderLookup.Provider): List<ItemStack> {
            return GeneRegistry.getRegistrySorted(registries)
                .map { geneHolder ->
                    ModItems.DNA_HELIX.toStack().apply { setGeneHolder(this, geneHolder) }
                }
        }
    }

    override fun appendHoverText(
        pStack: ItemStack,
        pContext: TooltipContext,
        pTooltipComponents: MutableList<Component>,
        pTooltipFlag: TooltipFlag
    ) {
        val geneHolder = getGeneHolder(pStack)

        if (geneHolder == null) {
            showNoGeneTooltips(pStack, pTooltipComponents)
        } else {
            pTooltipComponents.add(
                ModLanguageProvider.Tooltips.GENE
                    .toComponent(Gene.getNameComponent(geneHolder))
                    .withColor(ChatFormatting.GRAY)
            )
        }

    }

    private fun showNoGeneTooltips(
        pStack: ItemStack,
        pTooltipComponents: MutableList<Component>
    ) {

        pTooltipComponents.add(
            ModLanguageProvider.Tooltips.GENE
                .toComponent(Gene.unknownGeneComponent)
                .withColor(ChatFormatting.GRAY)
        )

        val entity = getEntityType(pStack)
        if (entity != null) {
            pTooltipComponents.add(
                ModLanguageProvider.Tooltips.HELIX_ENTITY
                    .toComponent(entity.description)
                    .withColor(ChatFormatting.GRAY)
            )
        }

        try {
            val isCreative = ClientUtil.playerIsCreative()

            if (isCreative) {
                val component =
                    ModLanguageProvider.Tooltips.CELL_CREATIVE
                        .toComponent()
                        .withColor(ChatFormatting.GRAY)
                pTooltipComponents.add(component)
            }
        } catch (e: Exception) {
            GeneticsResequenced.LOGGER.error("DnaHelixItem isCreative check failed", e)
        }

    }

}