package dev.aaronhowser.mods.geneticsresequenced.item

import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.geneticsresequenced.registry.ModDataComponents
import dev.aaronhowser.mods.geneticsresequenced.registry.ModItems
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class AntiFieldOrbItem : Item(
    Properties()
        .stacksTo(1)
        .component(
            ModDataComponents.IS_ACTIVE_COMPONENT,
            false
        )
) {

    companion object {

        private fun isEnabled(itemStack: ItemStack): Boolean {
            return itemStack.get(ModDataComponents.IS_ACTIVE_COMPONENT) ?: false
        }

        private fun toggleEnabled(itemStack: ItemStack) {
            itemStack.set(
                ModDataComponents.IS_ACTIVE_COMPONENT,
                !isEnabled(itemStack)
            )
        }

        fun isActiveForPlayer(player: Player): Boolean {
            return player.inventory.items.any { it.item == ModItems.ANTI_FIELD_ORB.get() && isEnabled(it) }
        }

    }

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        toggleEnabled(pPlayer.getItemInHand(pUsedHand))
        return super.use(pLevel, pPlayer, pUsedHand)
    }

    override fun isFoil(pStack: ItemStack): Boolean {
        return isEnabled(pStack)
    }

    override fun appendHoverText(
        pStack: ItemStack,
        pContext: TooltipContext,
        pTooltipComponents: MutableList<Component>,
        pTooltipFlag: TooltipFlag
    ) {
        val componentString =
            if (isEnabled(pStack)) ModLanguageProvider.Tooltips.ACTIVE else ModLanguageProvider.Tooltips.INACTIVE

        pTooltipComponents.add(
            componentString
                .toComponent()
                .withStyle(ChatFormatting.GRAY)
        )

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag)
    }

}