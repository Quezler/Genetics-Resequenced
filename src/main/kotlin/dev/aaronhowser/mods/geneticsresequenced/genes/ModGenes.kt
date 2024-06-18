package dev.aaronhowser.mods.geneticsresequenced.genes

import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GeneBuilder
import dev.aaronhowser.mods.geneticsresequenced.util.OtherUtil
import net.minecraft.world.effect.MobEffects

@Suppress("unused", "MemberVisibilityCanBePrivate")
object ModGenes {

    fun registerDefaultGenes() {
        // Doesn't do anything, but it loads the object which initializes the genes
    }

    private fun registerGene(geneId: String): GeneBuilder =
        GeneBuilder(OtherUtil.modResource(geneId))

    val basic: Gene = registerGene("basic")
        .setDnaPointsRequired(0)
        .removePlasmid()
        .build()

    // Mutations (must be initialized first because they're used in arguments in ones below)

    val clawsTwo: Gene = registerGene("claws_2")
        .setDnaPointsRequired(50)
        .allowMobs()
        .build()
    val efficiencyFour: Gene = registerGene("efficiency_4")
        .setDnaPointsRequired(50)
        .build()
    val flight: Gene = registerGene("flight")
        .setDnaPointsRequired(50)
        .build()
    val hasteTwo: Gene = registerGene("haste_2")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.DIG_SPEED, 2)
        .build()
    val meatyTwo: Gene = registerGene("meaty_2")
        .setDnaPointsRequired(50)
        .allowMobs()
        .build()
    val moreHeartsTwo: Gene = registerGene("more_hearts_2")
        .setDnaPointsRequired(50)
        .allowMobs()
        .build()
    val photosynthesis: Gene = registerGene("photosynthesis")
        .setDnaPointsRequired(40)
        .build()
    val regenerationFour: Gene = registerGene("regeneration_4")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.REGENERATION, 4)
        .build()
    val resistanceTwo: Gene = registerGene("resistance_2")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.DAMAGE_RESISTANCE, 2)
        .build()
    val speedFour: Gene = registerGene("speed_4")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.MOVEMENT_SPEED, 4)
        .build()
    val speedTwo: Gene = registerGene("speed_2")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.MOVEMENT_SPEED, 2)
        .setMutatesInto(speedFour)
        .build()
    val strengthTwo: Gene = registerGene("strength_2")
        .setDnaPointsRequired(50)
        .setPotion(MobEffects.DAMAGE_BOOST, 2)
        .build()
    val scareZombies: Gene = registerGene("scare_zombies")
        .setDnaPointsRequired(50)
        .build()
    val scareSpiders: Gene = registerGene("scare_spiders")
        .setDnaPointsRequired(50)
        .build()

    //Standard list
    val bioluminescence: Gene = registerGene("bioluminescence")
        .allowMobs()
        .setDnaPointsRequired(16)
        .build()
    val chatterbox: Gene = registerGene("chatterbox")
        .setDnaPointsRequired(20)
        .build()
    val claws: Gene = registerGene("claws")
        .setDnaPointsRequired(20)
        .allowMobs()
        .setMutatesInto(clawsTwo)
        .build()
    val dragonsBreath: Gene = registerGene("dragons_breath")
        .setDnaPointsRequired(20)
        .build()
    val eatGrass: Gene = registerGene("eat_grass")
        .setDnaPointsRequired(16)
        .build()
    val efficiency: Gene = registerGene("efficiency")
        .setDnaPointsRequired(30)
        .setMutatesInto(efficiencyFour)
        .build()
    val emeraldHeart: Gene = registerGene("emerald_heart")
        .setDnaPointsRequired(30)
        .allowMobs()
        .build()
    val enderDragonHealth: Gene = registerGene("ender_dragon_health")
        .setDnaPointsRequired(60)
        .build()
    val explosiveExit: Gene = registerGene("explosive_exit")
        .setDnaPointsRequired(20)
        .allowMobs()
        .build()
    val fireProof: Gene = registerGene("fire_proof")
        .setDnaPointsRequired(24)
        .allowMobs()
        .build()
    val haste: Gene = registerGene("haste")
        .setDnaPointsRequired(30)
        .setPotion(MobEffects.DIG_SPEED, 1)
        .setMutatesInto(hasteTwo)
        .build()
    val infinity: Gene = registerGene("infinity")
        .setDnaPointsRequired(30)
        .build()
    val invisible: Gene = registerGene("invisible")
        .setDnaPointsRequired(50)
        .allowMobs()
        .setPotion(MobEffects.INVISIBILITY, 1)
        .build()
    val itemMagnet: Gene = registerGene("item_magnet")
        .setDnaPointsRequired(30)
        .build()
    val jumpBoost: Gene = registerGene("jump_boost")
        .setDnaPointsRequired(10)
        .allowMobs()
        .setPotion(MobEffects.JUMP, 1)
        .setMutatesInto(flight)
        .build()
    val keepInventory: Gene = registerGene("keep_inventory")
        .setDnaPointsRequired(40)
        .build()
    val knockBack: Gene = registerGene("knock_back")
        .setDnaPointsRequired(20)
        .allowMobs()
        .build()
    val layEgg: Gene = registerGene("lay_egg")
        .setDnaPointsRequired(12)
        .allowMobs()
        .build()
    val luck: Gene = registerGene("luck")
        .setDnaPointsRequired(50)
        .allowMobs()
        .setPotion(MobEffects.LUCK, 1)
        .build()
    val meaty: Gene = registerGene("meaty")
        .setDnaPointsRequired(12)
        .allowMobs()
        .setMutatesInto(meatyTwo)
        .build()
    val milky: Gene = registerGene("milky")
        .setDnaPointsRequired(12)
        .allowMobs()
        .build()
    val mobSight: Gene = registerGene("mob_sight")
        .setDnaPointsRequired(16)
        .build()
    val moreHearts: Gene = registerGene("more_hearts")
        .setDnaPointsRequired(40)
        .allowMobs()
        .setMutatesInto(moreHeartsTwo)
        .build()
    val nightVision: Gene = registerGene("night_vision")
        .setDnaPointsRequired(16)
        .setPotion(MobEffects.NIGHT_VISION, 1)
        .build()
    val noFallDamage: Gene = registerGene("no_fall_damage")
        .setDnaPointsRequired(30)
        .allowMobs()
        .build()
    val noHunger: Gene = registerGene("no_hunger")
        .setDnaPointsRequired(30)
        .build()
    val poisonImmunity: Gene = registerGene("poison_immunity")
        .setDnaPointsRequired(24)
        .allowMobs()
        .build()
    val regeneration: Gene = registerGene("regeneration")
        .setDnaPointsRequired(60)
        .allowMobs()
        .setPotion(MobEffects.REGENERATION, 1)
        .setMutatesInto(regenerationFour)
        .build()
    val resistance: Gene = registerGene("resistance")
        .setDnaPointsRequired(30)
        .allowMobs()
        .setPotion(MobEffects.DAMAGE_RESISTANCE, 1)
        .setMutatesInto(resistanceTwo)
        .build()
    val scareCreepers: Gene = registerGene("scare_creepers")
        .setDnaPointsRequired(20)
        .setMutatesInto(scareZombies)
        .build()
    val scareSkeletons: Gene = registerGene("scare_skeletons")
        .setDnaPointsRequired(20)
        .setMutatesInto(scareSpiders)
        .build()
    val shootFireballs: Gene = registerGene("shoot_fireballs")
        .setDnaPointsRequired(24)
        .build()
    val slimyDeath: Gene = registerGene("slimy_death")
        .setDnaPointsRequired(60)
        .build()
    val speed: Gene = registerGene("speed")
        .setDnaPointsRequired(20)
        .allowMobs()
        .setPotion(MobEffects.MOVEMENT_SPEED, 1)
        .setMutatesInto(speedTwo)
        .build()
    val stepAssist: Gene = registerGene("step_assist")
        .setDnaPointsRequired(10)
        .build()
    val strength: Gene = registerGene("strength")
        .setDnaPointsRequired(20)
        .allowMobs()
        .setPotion(MobEffects.DAMAGE_BOOST, 1)
        .setMutatesInto(strengthTwo)
        .build()
    val teleport: Gene = registerGene("teleport")
        .setDnaPointsRequired(24)
        .setMutatesInto(flight)
        .build()
    val thorns: Gene = registerGene("thorns")
        .setDnaPointsRequired(12)
        .allowMobs()
        .setMutatesInto(photosynthesis)
        .build()
    val wallClimbing: Gene = registerGene("wall_climbing")
        .setDnaPointsRequired(40)
        .build()
    val waterBreathing: Gene = registerGene("water_breathing")
        .setDnaPointsRequired(16)
        .allowMobs()
        .build()
    val witherHit: Gene = registerGene("wither_hit")
        .setDnaPointsRequired(20)
        .allowMobs()
        .build()
    val witherProof: Gene = registerGene("wither_proof")
        .setDnaPointsRequired(40)
        .allowMobs()
        .build()
    val wooly: Gene = registerGene("wooly")
        .setDnaPointsRequired(12)
        .allowMobs()
        .build()
    val xpMagnet: Gene = registerGene("xp_magnet")
        .setDnaPointsRequired(30)
        .build()

    //Negative effects
    val blindness: Gene = registerGene("blindness")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.BLINDNESS, 1)
        .allowMobs()
        .setNegative()
        .build()
    val cringe: Gene = registerGene("cringe")
        .setDnaPointsRequired(20)
        .setNegative()
        .build()
    val cursed: Gene = registerGene("cursed")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.UNLUCK, 1)
        .allowMobs()
        .setNegative()
        .build()
    val flambe: Gene = registerGene("flambe")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()
    val hunger: Gene = registerGene("hunger")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.HUNGER, 1)
        .setNegative()
        .allowMobs()
        .build()
    val levitation: Gene = registerGene("levitation")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.LEVITATION, 1)
        .allowMobs()
        .setNegative()
        .build()
    val miningFatigue: Gene = registerGene("mining_fatigue")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.DIG_SLOWDOWN, 1)
        .allowMobs()
        .setNegative()
        .build()
    val nausea: Gene = registerGene("nausea")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.CONFUSION, 1)
        .allowMobs()
        .setNegative()
        .build()
    val poison: Gene = registerGene("poison")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.POISON, 1)
        .allowMobs()
        .setNegative()
        .build()
    val poisonFour: Gene = registerGene("poison_4")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.POISON, 4)
        .allowMobs()
        .setNegative()
        .build()
    val slowness: Gene = registerGene("slowness")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.MOVEMENT_SLOWDOWN, 1)
        .allowMobs()
        .setNegative()
        .build()
    val slownessFour: Gene = registerGene("slowness_4")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.MOVEMENT_SLOWDOWN, 4)
        .allowMobs()
        .setNegative()
        .build()
    val slownessSix: Gene = registerGene("slowness_6")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.MOVEMENT_SLOWDOWN, 6)
        .allowMobs()
        .setNegative()
        .build()
    val weakness: Gene = registerGene("weakness")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.WEAKNESS, 1)
        .allowMobs()
        .setNegative()
        .build()
    val wither: Gene = registerGene("wither")
        .setDnaPointsRequired(1)
        .setPotion(MobEffects.WITHER, 1)
        .allowMobs()
        .setNegative()
        .build()

    val blackDeath: Gene = registerGene("black_death")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()
    val greenDeath: Gene = registerGene("green_death")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()
    val whiteDeath: Gene = registerGene("white_death")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()
    val grayDeath: Gene = registerGene("gray_death")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()
    val unUndeath: Gene = registerGene("un_undeath")
        .setDnaPointsRequired(1)
        .setNegative()
        .allowMobs()
        .build()

}