package dev.aaronhowser.mods.geneticsresequenced.util

import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import net.minecraft.ChatFormatting
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.RegistryObject
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
object OtherUtil {

    fun modResource(path: String): ResourceLocation = ResourceLocation(GeneticsResequenced.ID, path)

    fun CompoundTag.getUuidOrNull(key: String): UUID? {
        if (!this.hasUUID(key)) return null
        return this.getUUID(key)
    }

    fun MutableComponent.withColor(color: ChatFormatting): MutableComponent = this.withStyle { it.withColor(color) }

    private val entityUuidMap: MutableMap<UUID, LivingEntity> = mutableMapOf()
    fun getNearbyEntityFromUuid(uuid: UUID, searchAroundEntity: LivingEntity): LivingEntity? {
        val mappedValue = entityUuidMap[uuid]
        if (mappedValue != null) return mappedValue

        val nearbyEntities = searchAroundEntity.level.getNearbyEntities(
            LivingEntity::class.java,
            TargetingConditions.DEFAULT,
            searchAroundEntity,
            searchAroundEntity.boundingBox.inflate(50.0)
        )

        for (entity in nearbyEntities) {
            if (entity.uuid == uuid) {
                entityUuidMap[uuid] = entity
                return entity
            }
        }

        return null
    }

    val RegistryObject<out Item>.itemStack: ItemStack
        get() = this.get().itemStack

    val Item.itemStack: ItemStack
        get() = this.defaultInstance
}