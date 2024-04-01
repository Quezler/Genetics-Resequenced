package dev.aaronhowser.mods.geneticsresequenced.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.aaronhowser.mods.geneticsresequenced.api.capability.genes.Genes.Companion.getGenes
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity

object ListGenesCommand : Command<CommandSourceStack> {

    private const val TARGET_ARGUMENT = "target"

    fun register(): ArgumentBuilder<CommandSourceStack, *> {
        return Commands
            .literal("list")
            .then(
                Commands
                    .argument(TARGET_ARGUMENT, EntityArgument.entity())
                    .executes(ListGenesCommand)
            )
    }

    override fun run(context: CommandContext<CommandSourceStack>): Int {

        val target = EntityArgument.getEntity(context, TARGET_ARGUMENT) as? LivingEntity ?: return 0
        val targetGenesList = target.getGenes()?.getGeneList() ?: return 0

        if (targetGenesList.isEmpty()) {
            context.source.sendSuccess(Component.literal("No genes found!"), false)
            return 1
        }

        val messageComponent = target.displayName.copy().append(Component.literal("'s genes:"))

        for (gene in targetGenesList) {
            val geneComponent = Component.literal("\n• ${gene.description}")
            messageComponent.append(geneComponent)
        }

        context.source.sendSuccess(messageComponent, false)
        return 1
    }
}