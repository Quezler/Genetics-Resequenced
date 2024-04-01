package dev.aaronhowser.mods.geneticsresequenced.event

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.ModTags
import dev.aaronhowser.mods.geneticsresequenced.api.capability.CapabilityHandler
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.EnumGenes
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Genes.Companion.getGenes
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GenesCapabilityProvider
import dev.aaronhowser.mods.geneticsresequenced.commands.ModCommands
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import kotlin.random.Random

@EventBusSubscriber(
    modid = GeneticsResequenced.ID
)
object ModEvents {

    @SubscribeEvent
    fun onAttachCapability(event: AttachCapabilitiesEvent<Entity>) {
        val entity = event.`object`

        val entityHasCapability = entity.getCapability(GenesCapabilityProvider.GENE_CAPABILITY).isPresent
        if (entityHasCapability) return

        event.addCapability(CapabilityHandler.GENE_CAPABILITY_RL, GenesCapabilityProvider)
    }

    @SubscribeEvent
    fun onPlayerCloned(event: PlayerEvent.Clone) {
        if (!event.isWasDeath) return

        event.original.getCapability(GenesCapabilityProvider.GENE_CAPABILITY).ifPresent { oldGenes ->
            event.original.getCapability(GenesCapabilityProvider.GENE_CAPABILITY).ifPresent { newGenes ->
                newGenes.setGeneList(oldGenes.getGeneList())
            }
        }
    }

    @SubscribeEvent
    fun onRegisterCommandsEvent(event: RegisterCommandsEvent) {
        ModCommands.register(event.dispatcher)
    }

    @SubscribeEvent
    fun onInteractEntity(event: PlayerInteractEvent.EntityInteract) {

        if (event.side.isClient) return

        val target = event.target
        if (target !is LivingEntity) return

        val genes = target.getGenes() ?: return

        println("Genes: ")
        println(genes.getGeneList().joinToString(", ") { it.name })

        if (genes.hasGene(EnumGenes.WOOLY)) wooly(event)
        if (genes.hasGene(EnumGenes.MILKY)) milky(event)
        if (genes.hasGene(EnumGenes.MEATY)) meaty(event)
    }

    private fun wooly(event: PlayerInteractEvent.EntityInteract) {
        val clickedWithShears = event.itemStack.`is`(ModTags.SHEARS_TAG)
        if (!clickedWithShears) return

        val woolItemStack = ItemStack(Blocks.WHITE_WOOL)

        val woolEntity = ItemEntity(
            event.level,
            event.target.eyePosition.x,
            event.target.eyePosition.y,
            event.target.eyePosition.z,
            woolItemStack
        )
        event.level.addFreshEntity(woolEntity)
        woolEntity.setDeltaMovement(
            Random.nextDouble(-0.05, 0.05),
            Random.nextDouble(0.05, 0.1),
            Random.nextDouble(-0.05, 0.05)
        )

        event.itemStack.hurtAndBreak(1, event.entity) { }
    }

    private fun meaty(event: PlayerInteractEvent.EntityInteract) {
        val clickedWithShears = event.itemStack.`is`(ModTags.SHEARS_TAG)
        if (!clickedWithShears) return

        val porkItemStack = ItemStack(Items.PORKCHOP)

        val porkEntity = ItemEntity(
            event.level,
            event.target.eyePosition.x,
            event.target.eyePosition.y,
            event.target.eyePosition.z,
            porkItemStack
        )
        event.level.addFreshEntity(porkEntity)
        porkEntity.setDeltaMovement(
            Random.nextDouble(-0.05, 0.05),
            Random.nextDouble(0.05, 0.1),
            Random.nextDouble(-0.05, 0.05)
        )

        event.itemStack.hurtAndBreak(1, event.entity) { }
    }

    private fun milky(event: PlayerInteractEvent.EntityInteract) {
        val clickedWithBucket = event.itemStack.`is`(Items.BUCKET)
        if (!clickedWithBucket) return

        event.itemStack.shrink(1)
        event.entity.addItem(ItemStack(Items.MILK_BUCKET))
    }

}