package dev.aaronhowser.mods.geneticsresequenced.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.gene.Gene
import dev.aaronhowser.mods.geneticsresequenced.registry.ModGenes
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

class GeneRequirements : SimpleJsonResourceReloadListener(
    GsonBuilder().setPrettyPrinting().create(),
    GeneticsResequenced.ID + "/gene_requirements"
) {

    companion object {
        private val geneRequirements: MutableMap<ResourceKey<Gene>, Set<ResourceKey<Gene>>> = mutableMapOf()
        fun getGeneRequirements(): Map<ResourceKey<Gene>, Set<ResourceKey<Gene>>> = geneRequirements.toMap()

        fun getRequirementsForGene(gene: ResourceKey<Gene>): Set<ResourceKey<Gene>> {
            return geneRequirements[gene] ?: emptySet()
        }
    }

    private fun addGeneRequirements(gene: ResourceKey<Gene>, requirements: List<ResourceKey<Gene>>) {
        geneRequirements[gene] = geneRequirements[gene]?.plus(requirements) ?: requirements.toSet()
    }

    private data class GeneRequirementsData(
        val gene: ResourceKey<Gene>,
        val requirements: List<ResourceKey<Gene>>
    ) {
        companion object {
            val CODEC: Codec<GeneRequirementsData> = RecordCodecBuilder.create { instance ->
                instance.group(
                    ResourceKey.codec(ModGenes.GENE_REGISTRY_KEY)
                        .fieldOf("gene")
                        .forGetter(GeneRequirementsData::gene),
                    ResourceKey.codec(ModGenes.GENE_REGISTRY_KEY).listOf()
                        .fieldOf("requirements")
                        .forGetter(GeneRequirementsData::requirements)
                ).apply(instance, ::GeneRequirementsData)
            }
        }
    }

    override fun apply(
        pObject: Map<ResourceLocation, JsonElement>,
        pResourceManager: ResourceManager,
        pProfiler: ProfilerFiller
    ) {
        geneRequirements.clear()

        for ((key: ResourceLocation, value: JsonElement) in pObject) {

            val geneRequirements: GeneRequirementsData = GeneRequirementsData.CODEC.parse(
                JsonOps.INSTANCE,
                value
            ).getOrThrow {
                IllegalArgumentException("Failed to decode entity genes for $key")
            }

            addGeneRequirements(
                geneRequirements.gene,
                geneRequirements.requirements
            )

            GeneticsResequenced.LOGGER.info("Loaded gene requirements for ${geneRequirements.gene}")
        }
    }
}