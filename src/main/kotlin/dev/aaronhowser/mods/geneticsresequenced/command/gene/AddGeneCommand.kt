package dev.aaronhowser.mods.geneticsresequenced.command.gene

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.api.genes.Gene
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes
import dev.aaronhowser.mods.geneticsresequenced.attachment.GenesData.Companion.addGene
import dev.aaronhowser.mods.geneticsresequenced.attachment.GenesData.Companion.hasGene
import dev.aaronhowser.mods.geneticsresequenced.command.ModCommands.SUGGEST_GENE_RLS
import dev.aaronhowser.mods.geneticsresequenced.command.ModCommands.SUGGEST_GENE_STRINGS
import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.arguments.ResourceLocationArgument
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity

object AddGeneCommand {

    private const val GENE_RL_ARGUMENT = "geneRl"
    private const val GENE_STRING_ARGUMENT = "geneString"
    private const val TARGET_ARGUMENT = "targets"

    fun register(): ArgumentBuilder<CommandSourceStack, *> {
        return Commands
            .literal("add")
            .requires { it.hasPermission(2) }
            .then(
                Commands
                    .literal("fromString")
                    .then(
                        Commands
                            .argument(GENE_STRING_ARGUMENT, StringArgumentType.string())
                            .suggests(SUGGEST_GENE_STRINGS)
                            .then(
                                Commands.argument(TARGET_ARGUMENT, EntityArgument.entities())
                                    .executes { cmd ->
                                        addGene(
                                            cmd,
                                            StringArgumentType.getString(cmd, GENE_STRING_ARGUMENT),
                                            EntityArgument.getEntities(cmd, TARGET_ARGUMENT)
                                        )
                                    }
                            )
                            .executes { cmd ->
                                addGene(
                                    cmd,
                                    StringArgumentType.getString(cmd, GENE_STRING_ARGUMENT),
                                    entities = null
                                )
                            }
                    )
            )
            .then(
                Commands.literal("fromId")
                    .then(
                        Commands
                            .argument(GENE_RL_ARGUMENT, ResourceLocationArgument.id())
                            .suggests(SUGGEST_GENE_RLS)
                            .then(
                                Commands
                                    .argument(TARGET_ARGUMENT, EntityArgument.entities())
                                    .executes { cmd ->
                                        addGene(
                                            cmd,
                                            ResourceLocationArgument.getId(cmd, GENE_RL_ARGUMENT),
                                            EntityArgument.getEntities(cmd, TARGET_ARGUMENT)
                                        )
                                    }
                            )
                            .executes { cmd ->
                                addGene(
                                    cmd,
                                    ResourceLocationArgument.getId(cmd, GENE_RL_ARGUMENT),
                                    entities = null
                                )
                            }
                    )
            )

    }

    private fun addGene(
        context: CommandContext<CommandSourceStack>,
        geneRl: ResourceLocation,
        entities: MutableCollection<out Entity>? = null
    ): Int {
        val gene = ModGenes.fromResourceLocation(context.source.registryAccess(), geneRl)
            ?: throw IllegalArgumentException("Gene with id $geneRl does not exist!")

        return addGene(context, gene, entities)
    }

    private fun addGene(
        context: CommandContext<CommandSourceStack>,
        geneString: String,
        entities: MutableCollection<out Entity>? = null
    ): Int {

        val gene = ModGenes.fromIdPath(context.source.registryAccess(), geneString)
            ?: throw IllegalArgumentException("Gene with id $geneString does not exist!")

        return addGene(context, gene, entities)
    }

    private fun addGene(
        context: CommandContext<CommandSourceStack>,
        geneToAdd: Holder<Gene>,
        entities: MutableCollection<out Entity>? = null
    ): Int {

        val targets: List<LivingEntity> =
            entities?.mapNotNull { it as? LivingEntity } ?: listOfNotNull(context.source.entity as? LivingEntity)

        if (targets.size == 1) {
            handleSingleTarget(context, targets.first(), geneToAdd)
        } else {
            handleMultipleTargets(context, targets, geneToAdd)
        }

        return 1
    }

    private fun handleSingleTarget(
        context: CommandContext<CommandSourceStack>,
        target: LivingEntity,
        geneHolder: Holder<Gene>,
    ) {
        val geneWasAdded = addGeneToTarget(target, geneHolder)

        if (geneWasAdded) {
            val component =
                ModLanguageProvider.Commands.ADD_SINGLE_SUCCESS.toComponent(
                    Gene.getNameComponent(geneHolder),
                    target.name
                )

            context.source.sendSuccess({ component }, false)
        } else {
            val component =
                ModLanguageProvider.Commands.ADD_SINGLE_FAIL.toComponent(
                    Gene.getNameComponent(geneHolder),
                    target.name
                )

            context.source.sendFailure(component)
        }
    }

    private fun handleMultipleTargets(
        context: CommandContext<CommandSourceStack>,
        targets: List<LivingEntity>,
        geneHolder: Holder<Gene>
    ) {
        var amountSuccess = 0
        var amountFail = 0

        for (target in targets) {
            val geneWasAdded = addGeneToTarget(target, geneHolder)
            if (geneWasAdded) amountSuccess++ else amountFail++
        }

        if (amountSuccess != 0) {
            val component =
                ModLanguageProvider.Commands.ADD_MULTIPLE_SUCCESS.toComponent(
                    Gene.getNameComponent(geneHolder),
                    amountSuccess
                )

            context.source.sendSuccess({ component }, false)
        }

        if (amountFail != 0) {
            val component =
                ModLanguageProvider.Commands.ADD_MULTIPLE_FAIL.toComponent(
                    Gene.getNameComponent(geneHolder),
                    amountFail
                )
            context.source.sendFailure(component)
        }
    }

    private fun addGeneToTarget(
        target: LivingEntity,
        geneHolder: Holder<Gene>,
    ): Boolean {
        val alreadyHasGene = target.hasGene(geneHolder)
        if (alreadyHasGene) {
            GeneticsResequenced.LOGGER.info("Tried to add gene ${geneHolder.key!!.location()} to ${target.name.string}, but they already have it!")
            return false
        }

        if (!geneHolder.value().canEntityHave(target)) {
            GeneticsResequenced.LOGGER.info("Tried to add gene ${geneHolder.key!!.location()} to ${target.name.string}, but they can't have it!")
            return false
        }

        val success = target.addGene(geneHolder)

        return success
    }

}