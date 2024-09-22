package dev.aaronhowser.mods.geneticsresequenced.datagen

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider
import com.klikli_dev.modonomicon.datagen.EnUsProvider
import dev.aaronhowser.mods.geneticsresequenced.GeneticsResequenced
import dev.aaronhowser.mods.geneticsresequenced.datagen.loot.ModLootTableProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.model.ModBlockStateProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.model.ModItemModelProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.modonomicon.ModModonomiconProvider
import dev.aaronhowser.mods.geneticsresequenced.datagen.tag.*
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.AdvancementProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

@EventBusSubscriber(
    modid = GeneticsResequenced.ID,
    bus = EventBusSubscriber.Bus.MOD
)
object ModDataGen {

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        val generator: DataGenerator = event.generator
        val output: PackOutput = generator.packOutput
        val existingFileHelper: ExistingFileHelper = event.existingFileHelper
        val lookupProvider: CompletableFuture<HolderLookup.Provider> = event.lookupProvider

        val datapackRegistrySets = generator.addProvider(
            event.includeServer(),
            ModDatapackBuiltinEntriesProvider(output, lookupProvider)
        )

        val languageProvider = generator.addProvider(event.includeClient(), ModLanguageProvider(output))
        val itemModelProvider = generator.addProvider(
            event.includeClient(),
            ModItemModelProvider(output, existingFileHelper)
        )
        val blockModelProvider = generator.addProvider(
            event.includeClient(),
            ModBlockStateProvider(output, existingFileHelper)
        )

        val recipeProvider = generator.addProvider(event.includeServer(), ModRecipeProvider(output, lookupProvider))

        val blockTagProvider = generator.addProvider(
            event.includeServer(),
            ModBlockTagsProvider(output, lookupProvider, existingFileHelper)
        )
        val itemTagProvider = generator.addProvider(
            event.includeServer(),
            ModItemTagsProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
        )
        val geneTagProvider = generator.addProvider(
            event.includeServer(),
            ModGeneTagsProvider(output, datapackRegistrySets.registryProvider, existingFileHelper)
        )
        val entityTypeTagProvider = generator.addProvider(
            event.includeServer(),
            ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper)
        )
        val enchantmentTagProvider = generator.addProvider(
            event.includeServer(),
            ModEnchantmentTagsProvider(output, lookupProvider, existingFileHelper)
        )

        val advancementProvider = generator.addProvider(
            event.includeServer(),
            AdvancementProvider(
                output,
                lookupProvider,
                existingFileHelper,
                listOf(ModAdvancementSubProvider())
            )
        )

        val lootTableProvider = generator.addProvider(
            event.includeServer(),
            ModLootTableProvider.create(output, lookupProvider)
        )

        val modonomiconEnUsCache = LanguageProviderCache("en_us")
        val modonomiconBookProvider = generator.addProvider(
            event.includeClient(),
            NeoBookProvider.of(
                event,
                ModModonomiconProvider(modonomiconEnUsCache, datapackRegistrySets.registryProvider)
            )
        )
        val modonomiconEnUsProvider = generator.addProvider(
            event.includeClient(),
            EnUsProvider(output, modonomiconEnUsCache)
        )

    }

}