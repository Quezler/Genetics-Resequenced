package dev.aaronhowser.mods.geneticsresequenced.api.capability.genes

import dev.aaronhowser.mods.geneticsresequenced.config.ServerConfig

@Suppress("unused")
enum class EnumGenes(
    val description: String,
    val mutatesTo: EnumGenes? = null,
    val isMutation: Boolean = false,
    val isNegative: Boolean = false
) {

    //Mutations, must be first
    HASTE_2("Haste II", null, true),
    EFFICIENCY_4("Efficiency IV", null, true),
    REGENERATION_4("Regeneration IV", null, true),
    SPEED_4("Speed IV", null, true),
    SPEED_2("Speed II", SPEED_4, true),
    RESISTANCE_2("Resistance II", null, true),
    STRENGTH_2("Strength II", null, true),
    MEATY_2("Meaty II", null, true),
    MORE_HEARTS_2("More Hearts II", null, true),
    INVISIBLE("Invisbility", null, true),
    FLY("Flight", null, true),
    LUCK("Luck", null, true),
    SCARE_ZOMBIES("Scare Zombies", null, true),
    SCARE_SPIDERS("Scare Spiders", null, true),
    THORNS("Thorns", null, true),
    CLAWS_2("Claws II", null, true),

    //Standard list
    DRAGONS_BREATH("Dragon's Breath"),
    EAT_GRASS("Eat Grass"),
    EMERALD_HEART("Emerald Heart"),
    ENDER_DRAGON_HEALTH("Ender Dragon Health"),
    EXPLOSIVE_EXIT("Explosive Exit"),
    FIRE_PROOF("Fire Proof"),
    ITEM_MAGNET("Item Magnet"),
    JUMP_BOOST("Jump Boost", FLY),
    MILKY("Milky"),
    MORE_HEARTS("More Hearts", MORE_HEARTS_2),
    NIGHT_VISION("Night Vision"),
    NO_FALL_DAMAGE("No Fall Damage"),
    PHOTOSYNTHESIS("Photosynthesis", THORNS),
    POISON_PROOF("Poison Immunity"),
    RESISTANCE("Resistance", RESISTANCE_2),
    SAVE_INVENTORY("Save Inventory"),
    SCARE_CREEPERS("Scare Creepers", SCARE_ZOMBIES),
    SCARE_SKELETONS("Scare Skeletons", SCARE_SPIDERS),
    SHOOT_FIREBALLS("Shoot Fireballs"),
    SLIMY("Slimy Death"),
    SPEED("Speed", SPEED_2),
    STRENGTH("Strength", STRENGTH_2),
    TELEPORTER("Teleport", FLY),
    WATER_BREATHING("Water Breathing"),
    WOOLY("Wooly"),
    WITHER_HIT("Wither Hit"),
    WITHER_PROOF("Wither Proof"),
    XP_MAGNET("XP Magmet"),
    STEP_ASSIST("Step Assit"),
    INFINITY("Infinity"),
    BIOLUMIN("Bioluminescence"),
    CYBERNETIC("Cybernetic"),
    LAY_EGG("Lay Eggs"),
    MEATY("Meaty", MEATY_2),
    NO_HUNGER("No Hunger"),
    CLAWS("Claws", CLAWS_2),
    HASTE("Haste", HASTE_2),
    EFFICIENCY("Efficiency", EFFICIENCY_4),
    CLIMB_WALLS("Climb Walls"),
    MOB_SIGHT("Mob Sight"),
    REGENERATION("Regeneration", REGENERATION_4),

    //Negative effects
    POISON("Poison II", isNegative = true),
    POISON_4("Poison IV", isNegative = true),
    WITHER("Wither II", isNegative = true),
    WEAKNESS("Weakness", isNegative = true),
    BLINDNESS("Blindness", isNegative = true),
    SLOWNESS("Slowness", isNegative = true),
    SLOWNESS_4("Slowness IV", isNegative = true),
    SLOWNESS_6("Slowness VI", isNegative = true),
    NAUSEA("Nausea", isNegative = true),
    HUNGER("Hunger", isNegative = true),
    FLAME("Flambe", isNegative = true),
    CURSED("Cursed", isNegative = true),
    LEVITATION("Levitation", isNegative = true),
    MINING_WEAKNESS("Mining Weakness", isNegative = true),
    DEAD_CREEPERS("Green Death", isNegative = true),
    DEAD_UNDEAD("Un-Death", isNegative = true),
    DEAD_OLD_AGE("Gray Death", isNegative = true),
    DEAD_HOSTILE("White Death", isNegative = true),
    DEAD_ALL("Black Death", isNegative = true),
    REALLY_DEAD_ALL("Void Death", isNegative = true)
    ;

    var isActive: Boolean = true

    fun canAddMutation(genes: IGene, syringeGenes: IGene): Boolean {
        return when (this) {
            HASTE_2 -> genes.hasGene(HASTE) || syringeGenes.hasGene(HASTE)
            EFFICIENCY_4 -> genes.hasGene(EFFICIENCY) || syringeGenes.hasGene(EFFICIENCY)
            REGENERATION_4 -> genes.hasGene(REGENERATION) || syringeGenes.hasGene(REGENERATION)
            SPEED_4 -> genes.hasGene(SPEED_2) || syringeGenes.hasGene(SPEED_2)
            SPEED_2 -> genes.hasGene(SPEED) || syringeGenes.hasGene(SPEED)
            RESISTANCE_2 -> genes.hasGene(RESISTANCE) || syringeGenes.hasGene(RESISTANCE)
            STRENGTH_2 -> genes.hasGene(STRENGTH) || syringeGenes.hasGene(STRENGTH)
            MEATY_2 -> genes.hasGene(MEATY) || syringeGenes.hasGene(MEATY)
            MORE_HEARTS_2 -> genes.hasGene(MORE_HEARTS) || syringeGenes.hasGene(MORE_HEARTS)
            INVISIBLE -> true
            FLY -> genes.hasGene(JUMP_BOOST) || genes.hasGene(TELEPORTER) || genes.hasGene(NO_FALL_DAMAGE) ||
                    syringeGenes.hasGene(JUMP_BOOST) || syringeGenes.hasGene(TELEPORTER) || syringeGenes.hasGene(
                NO_FALL_DAMAGE
            )

            LUCK -> true
            SCARE_ZOMBIES -> genes.hasGene(SCARE_CREEPERS) || syringeGenes.hasGene(SCARE_CREEPERS)
            SCARE_SPIDERS -> genes.hasGene(SCARE_SKELETONS) || syringeGenes.hasGene(SCARE_SKELETONS)
            THORNS -> genes.hasGene(PHOTOSYNTHESIS) || syringeGenes.hasGene(PHOTOSYNTHESIS)
            CLAWS_2 -> genes.hasGene(CLAWS) || syringeGenes.hasGene(CLAWS)
            else -> true
        }
    }

    fun getNumberNeeded(gene: EnumGenes): Int {
        if (gene.isMutation) return 50
        return if (ServerConfig.hardMode.get()) 24 else when (gene) {
            STEP_ASSIST, JUMP_BOOST -> 10
            MILKY, WOOLY, MEATY, LAY_EGG, THORNS -> 12
            EAT_GRASS, NIGHT_VISION, MOB_SIGHT, WATER_BREATHING, BIOLUMIN -> 16
            DRAGONS_BREATH, SCARE_CREEPERS, SCARE_SKELETONS, WITHER_HIT, SPEED, CLAWS, STRENGTH, EXPLOSIVE_EXIT -> 20
            FIRE_PROOF, POISON_PROOF, SHOOT_FIREBALLS, TELEPORTER -> 24
            EMERALD_HEART, NO_FALL_DAMAGE, NO_HUNGER, RESISTANCE, XP_MAGNET, ITEM_MAGNET, INFINITY, CYBERNETIC -> 30
            WITHER_PROOF, MORE_HEARTS, CLIMB_WALLS, SAVE_INVENTORY, PHOTOSYNTHESIS -> 40
            REGENERATION, ENDER_DRAGON_HEALTH, SLIMY -> 60
            else -> 22
        }
    }
}