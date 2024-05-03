package dev.aaronhowser.mods.geneticsresequenced.items

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.util.ClientHelper
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil.withColor
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

object DnaHelixItem : EntityDnaItem() {

    private const val GENE_ID_NBT = "GeneId"
    private const val BASIC_NBT = "IsBasic"


    fun ItemStack.hasGene(): Boolean = this.tag?.contains(GENE_ID_NBT) ?: false

    fun ItemStack.getGene(): Gene? {
        val string = this.tag?.getString(GENE_ID_NBT)
        if (string.isNullOrBlank()) return null
        return Gene.fromId(string)
    }

    fun ItemStack.setGene(gene: Gene?): ItemStack {
        if (gene == null) {
            return this.setBasic()
        }

        val tag = this.orCreateTag
        tag.putString(GENE_ID_NBT, gene.id.toString())
        tag.remove(MOB_ID_NBT)
        return this
    }

    fun ItemStack.setBasic(): ItemStack {
        val tag = this.orCreateTag
        tag.remove(GENE_ID_NBT)
        tag.remove(MOB_ID_NBT)
        tag.putBoolean(BASIC_NBT, true)
        return this
    }

    fun ItemStack.isBasic(): Boolean = this.tag?.getBoolean(BASIC_NBT) ?: false

    override fun interactLivingEntity(
        pStack: ItemStack,
        pPlayer: Player,
        pInteractionTarget: LivingEntity,
        pUsedHand: InteractionHand
    ): InteractionResult {
        return if (pStack.hasGene())
            InteractionResult.PASS
        else
            super.interactLivingEntity(pStack, pPlayer, pInteractionTarget, pUsedHand)
    }

    override fun appendHoverText(
        pStack: ItemStack,
        pLevel: Level?,
        pTooltipComponents: MutableList<Component>,
        pIsAdvanced: TooltipFlag
    ) {
        val gene = pStack.getGene()

        if (gene == null) {
            showNoGeneTooltips(pStack, pTooltipComponents, pLevel)
        } else {
            pTooltipComponents.add(
                Component.translatable("tooltip.geneticsresequenced.gene", gene.nameComponent)
                    .withColor(ChatFormatting.GRAY)
            )
        }
    }

    private fun showNoGeneTooltips(
        pStack: ItemStack,
        pTooltipComponents: MutableList<Component>,
        pLevel: Level?
    ) {

        if (pStack.isBasic()) {
            pTooltipComponents.add(
                Component.translatable(
                    "tooltip.geneticsresequenced.gene",
                    Gene.basicGeneComponent
                )
                    .withColor(ChatFormatting.GRAY)
            )
            return
        }

        pTooltipComponents.add(
            Component.translatable(
                "tooltip.geneticsresequenced.gene",
                Gene.unknownGeneComponent
            )
                .withColor(ChatFormatting.GRAY)
        )

        val entity = getEntityType(pStack)
        if (entity != null) {
            pTooltipComponents.add(
                Component.translatable(
                    "tooltip.geneticsresequenced.helix_entity",
                    entity.description
                )
                    .withColor(ChatFormatting.GRAY)
            )
        }

        try {
            val isCreative = pLevel?.isClientSide == true && ClientHelper.playerIsCreative()
            if (isCreative) {
                val component =
                    Component
                        .translatable("tooltip.geneticsresequenced.dna_item.creative")
                        .withColor(ChatFormatting.GRAY)

                pTooltipComponents.add(component)
            }
        } catch (e: Exception) {
            GeneticsResequenced.LOGGER.error("EntityDnaItem isCreative check failed", e)
        }
    }

}