package dev.aaronhowser.mods.geneticsresequenced.events

import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Gene
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraftforge.eventbus.api.Cancelable
import net.minecraftforge.eventbus.api.Event

object CustomEvents {

    data class PlayerInventoryChangeEvent(
        val player: ServerPlayer,
        val slot: Int,
        val stack: ItemStack
    ) : Event()

    @Cancelable
    data class GeneChangeEvent(
        val entity: LivingEntity,
        val gene: Gene,
        val wasAdded: Boolean
    ) : Event()

}