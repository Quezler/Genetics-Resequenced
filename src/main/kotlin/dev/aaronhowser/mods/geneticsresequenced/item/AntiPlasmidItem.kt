package dev.aaronhowser.mods.geneticsresequenced.item

import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil.withColor
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class AntiPlasmidItem : Item(Properties()) {

    override fun appendHoverText(
        pStack: ItemStack,
        pContext: TooltipContext,
        pTooltipComponents: MutableList<Component>,
        pTooltipFlag: TooltipFlag
    ) {

        val gene = PlasmidItem.getGene(pStack)

        if (gene == null) {
            pTooltipComponents.add(
                Component
                    .translatable(ModLanguageProvider.Tooltips.ANTI_PLASMID_EMPTY)
                    .withColor(ChatFormatting.GRAY)
            )
        } else {
            pTooltipComponents.add(
                Component
                    .translatable(ModLanguageProvider.Tooltips.PLASMID_GENE, gene.nameComponent)
                    .withColor(ChatFormatting.GRAY)
            )
        }

    }

}