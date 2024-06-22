package dev.aaronhowser.mods.geneticsresequenced.event.entity

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.gene.behavior.DamageGenes
import dev.aaronhowser.mods.geneticsresequenced.gene.behavior.DeathGenes
import dev.aaronhowser.mods.geneticsresequenced.gene.behavior.TickGenes
import net.minecraft.world.entity.LivingEntity
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent
import net.neoforged.neoforge.event.level.ExplosionEvent
import net.neoforged.neoforge.event.tick.EntityTickEvent

@EventBusSubscriber(
    modid = GeneticsResequenced.ID
)
object OtherEntityEvents {

    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        DeathGenes.handleEmeraldHeart(event)
        DeathGenes.handleExplosiveExit(event)
//        DeathGenes.handleSlimyDeath(event)
    }

    @SubscribeEvent
    fun onDetonate(event: ExplosionEvent.Detonate) {
        DeathGenes.explosiveExitDetonation(event)
    }

    @SubscribeEvent
    fun onLivingDamage(event: LivingDamageEvent) {
        DamageGenes.handleNoFallDamage(event)
        DamageGenes.handleWitherProof(event)
        DamageGenes.handleFireProof(event)
        DamageGenes.handlePoisonProof(event)

        DamageGenes.handleDragonHealth(event) //must be last
    }

    @SubscribeEvent
    fun onLivingHurt(event: LivingHurtEvent) {
        DamageGenes.handleThorns(event)
        DamageGenes.handleClaws(event)
        DamageGenes.handleWitherHit(event)
        DamageGenes.handleJohnny(event)
        DamageGenes.handleChilling(event)
    }

    //TODO: Make sure this works with players and not just entities
    @SubscribeEvent
    fun onLivingTick(event: EntityTickEvent.Pre) {
        val entity = event.entity as? LivingEntity ?: return

        TickGenes.handleBioluminescence(entity)
        TickGenes.handlePhotosynthesis(entity)
        TickGenes.handleTickingGenes(entity)
    }

}