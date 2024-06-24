package dev.aaronhowser.mods.geneticsresequenced.packets.server_to_client

import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GenesCapability.Companion.getGenes
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.GenesCapability.Companion.hasGene
import dev.aaronhowser.mods.geneticsresequenced.genes.ModGenes
import dev.aaronhowser.mods.geneticsresequenced.packets.ModPacket
import dev.aaronhowser.mods.geneticsresequenced.registries.ModAttributes
import dev.aaronhowser.mods.geneticsresequenced.util.ClientUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class GeneChangedPacket(
    private val entityId: Int,
    private val geneId: ResourceLocation,
    private val wasAdded: Boolean
) : ModPacket {

    override fun encode(buffer: FriendlyByteBuf) {
        buffer.writeInt(entityId)
        buffer.writeResourceLocation(geneId)
        buffer.writeBoolean(wasAdded)
    }

    companion object {
        fun decode(buffer: FriendlyByteBuf): GeneChangedPacket {
            return GeneChangedPacket(
                buffer.readInt(),
                buffer.readResourceLocation(),
                buffer.readBoolean()
            )
        }
    }

    override fun receiveMessage(context: Supplier<NetworkEvent.Context>) {
        if (context.get().direction != NetworkDirection.PLAY_TO_CLIENT) {
            throw IllegalStateException("Received GeneChangedPacket on wrong side!")
        }

        val player: LocalPlayer = Minecraft.getInstance().player
            ?: throw IllegalStateException("Received GeneChangedPacket without player!")

        val gene = Gene.fromId(geneId)
            ?: throw IllegalStateException("Received GeneChangedPacket with invalid gene id!")

        val entity = player.level.getEntity(entityId) as? LivingEntity
            ?: throw IllegalStateException("Received GeneChangedPacket with invalid entity id!")

        entity.getGenes()?.apply {
            if (wasAdded) {
                addGene(entity, gene)
            } else {
                removeGene(entity, gene)
            }
        }

        if (entity is LocalPlayer) {
            if (gene == ModGenes.cringe) ClientUtil.handleCringe(wasAdded)

            handleAttributes(entity, gene)
        }

        context.get().packetHandled = true
    }

    private fun handleAttributes(player: LocalPlayer, gene: Gene) {
        val attributeInstance = when (gene) {
            ModGenes.efficiency, ModGenes.efficiencyFour -> player.attributes.getInstance(ModAttributes.EFFICIENCY)
            ModGenes.wallClimbing -> player.attributes.getInstance(ModAttributes.WALL_CLIMBING)
            else -> null
        } ?: return

        val newLevel = when (gene) {
            ModGenes.efficiency -> if (wasAdded) 1.0 else 0.0

            ModGenes.efficiencyFour -> {
                if (wasAdded) {
                    4.0
                } else {
                    if (player.hasGene(ModGenes.efficiency)) {
                        1.0
                    } else {
                        0.0
                    }
                }
            }

            ModGenes.wallClimbing -> if (wasAdded) 1.0 else 0.0

            else -> throw IllegalStateException("Gene $gene went through the GeneChangedPacket but isn't handled!")
        }

        attributeInstance.baseValue = newLevel
    }
}