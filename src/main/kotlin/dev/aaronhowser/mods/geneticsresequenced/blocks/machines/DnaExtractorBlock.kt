package dev.aaronhowser.mods.geneticsresequenced.blocks.machines

import dev.aaronhowser.mods.geneticsresequenced.block_entities.DnaExtractorBlockEntity
import dev.aaronhowser.mods.geneticsresequenced.block_entities.ModBlockEntities
import dev.aaronhowser.mods.geneticsresequenced.blocks.base.CraftingMachineBlock
import dev.aaronhowser.mods.geneticsresequenced.util.BlockEntityHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material

object DnaExtractorBlock : CraftingMachineBlock(
    Properties.of(Material.METAL),
    DnaExtractorBlockEntity::class.java
) {

    override fun <T : BlockEntity> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return BlockEntityHelper.createTickerHelper(
            pBlockEntityType,
            ModBlockEntities.DNA_EXTRACTOR.get(),
            DnaExtractorBlockEntity::tick
        )
    }

}