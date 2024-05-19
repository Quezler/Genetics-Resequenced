package dev.aaronhowser.mods.geneticsresequenced.blocks

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.AirborneDispersalBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.CloningMachineBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.incubator.IncubatorBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.blood_purifier.BloodPurifierBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.cell_analyzer.CellAnalyzerBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.coal_generator.CoalGeneratorBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.dna_decryptor.DnaDecryptorBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.dna_extractor.DnaExtractorBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.plasmid_infuser.PlasmidInfuserBlock
import dev.aaronhowser.mods.geneticsresequenced.blocks.machines.plasmid_injector.PlasmidInjectorBlock
import dev.aaronhowser.mods.geneticsresequenced.items.ModItems
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.ObjectHolderDelegate
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {

    //TODO: Make all blocks classes and not objects

    private val defaultItemProperties = Item.Properties().tab(ModItems.MOD_TAB)

    val BLOCK_REGISTRY: DeferredRegister<Block> =
        DeferredRegister.create(ForgeRegistries.BLOCKS, GeneticsResequenced.ID)

    val BIOLUMINESCENCE_BLOCK by register("bioluminescence") { BioluminescenceBlock }
    val COAL_GENERATOR by register("coal_generator") { CoalGeneratorBlock() }
    val CELL_ANALYZER by register("cell_analyzer") { CellAnalyzerBlock }
    val DNA_EXTRACTOR by register("dna_extractor") { DnaExtractorBlock }
    val DNA_DECRYPTOR by register("dna_decryptor") { DnaDecryptorBlock }
    val BLOOD_PURIFIER by register("blood_purifier") { BloodPurifierBlock }
    val PLASMID_INFUSER by register("plasmid_infuser") { PlasmidInfuserBlock }
    val PLASMID_INJECTOR by register("plasmid_injector") { PlasmidInjectorBlock }
    val AIRBORNE_DISPERSAL_DEVICE by register("airborne_dispersal_device") { AirborneDispersalBlock }
    val CLONING_MACHINE by register("cloning_machine") { CloningMachineBlock }
    val INCUBATOR by register("incubator") { IncubatorBlock }
    val ANTIFIELD_BLOCK by register("anti_field_block") { AntiFieldBlock() }

    private fun register(
        name: String,
        itemProperties: Item.Properties = defaultItemProperties,
        supplier: () -> Block
    ): ObjectHolderDelegate<Block> {
        val block = BLOCK_REGISTRY.registerObject(name, supplier)

        ModItems.ITEM_REGISTRY.register(name) {
            BlockItem(
                block.get(),
                itemProperties
            )
        }

        return block
    }

}