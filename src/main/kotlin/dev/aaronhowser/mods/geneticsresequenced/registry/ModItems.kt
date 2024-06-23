package dev.aaronhowser.mods.geneticsresequenced.registry

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.item.*
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ModItems {

    val ITEM_REGISTRY: DeferredRegister.Items = DeferredRegister.createItems(GeneticsResequenced.ID)

    val SCRAPER: DeferredItem<ScraperItem> =
        ITEM_REGISTRY.registerItem("scraper") { ScraperItem() }
    val SYRINGE: DeferredItem<SyringeItem> =
        ITEM_REGISTRY.registerItem("syringe") { SyringeItem() }
    val METAL_SYRINGE: DeferredItem<MetalSyringeItem> =
        ITEM_REGISTRY.registerItem("metal_syringe") { MetalSyringeItem() }
    val ORGANIC_MATTER: DeferredItem<EntityDnaItem> =
        ITEM_REGISTRY.registerItem("organic_matter") { EntityDnaItem() }
    val CELL: DeferredItem<EntityDnaItem> =
        ITEM_REGISTRY.registerItem("cell") { EntityDnaItem() }
    val GMO_CELL =
        ITEM_REGISTRY.registerSimpleItem("gmo_cell")
    val DNA_HELIX =
        ITEM_REGISTRY.registerSimpleItem("dna_helix")
    val PLASMID =
        ITEM_REGISTRY.registerItem("plasmid") { PlasmidItem() }
    val ANTI_PLASMID: DeferredItem<AntiPlasmidItem> =
        ITEM_REGISTRY.registerItem("anti_plasmid") { AntiPlasmidItem() }
    val OVERCLOCKER =
        ITEM_REGISTRY.registerSimpleItem("overclocker")
    val ANTI_FIELD_ORB: DeferredItem<AntiFieldOrbItem> =
        ITEM_REGISTRY.registerItem("anti_field_orb") { AntiFieldOrbItem() }
    val DRAGON_HEALTH_CRYSTAL: DeferredItem<DragonHealthCrystal> =
        ITEM_REGISTRY.registerItem("dragon_health_crystal") { DragonHealthCrystal() }
    val FRIENDLY_SLIME_SPAWN_EGG =
        ITEM_REGISTRY.registerSimpleItem("support_slime_spawn_egg")

}