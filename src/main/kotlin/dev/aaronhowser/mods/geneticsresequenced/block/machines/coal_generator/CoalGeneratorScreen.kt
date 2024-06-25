package dev.aaronhowser.mods.geneticsresequenced.block.machines.coal_generator

import com.mojang.blaze3d.systems.RenderSystem
import dev.aaronhowser.mods.geneticsresequenced.block.base.menu.ScreenTextures
import dev.aaronhowser.mods.geneticsresequenced.util.MouseUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.joml.Vector2i

class CoalGeneratorScreen(
    pMenu: CoalGeneratorMenu,
    pPlayerInventory: Inventory,
    pTitle: Component
) : AbstractContainerScreen<CoalGeneratorMenu>(pMenu, pPlayerInventory, pTitle) {

    override fun renderBg(pGuiGraphics: GuiGraphics, pPartialTick: Float, pMouseX: Int, pMouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, ScreenTextures.Backgrounds.COAL_GENERATOR)
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2

        pGuiGraphics.blit(
            ScreenTextures.Backgrounds.COAL_GENERATOR,
            x,
            y,
            0,
            0,
            ScreenTextures.Backgrounds.DIMENSIONS.x,
            ScreenTextures.Backgrounds.DIMENSIONS.y
        )

        renderProgressArrow(pGuiGraphics, x, y)
        renderBurnProgress(pGuiGraphics, x, y)
        renderEnergyInfo(pGuiGraphics, x, y)
    }

    private fun renderEnergyInfo(pGuiGraphics: GuiGraphics, x: Int, y: Int) {
        val energyStorage = menu.blockEntity.energyStorage
        val percent = energyStorage.energyStored.toFloat() / energyStorage.maxEnergyStored.toFloat()

        pGuiGraphics.blitSprite(
            ScreenTextures.Elements.Energy.TEXTURE,
            ScreenTextures.Elements.Energy.DIMENSIONS.x,
            ScreenTextures.Elements.Energy.DIMENSIONS.y,
            0,
            0,
            x + ScreenTextures.Elements.Energy.LOCATION_COAL_GEN.x,
            y + ScreenTextures.Elements.Energy.LOCATION_COAL_GEN.y,
            0,
            ScreenTextures.Elements.Energy.DIMENSIONS.x,
            (ScreenTextures.Elements.Energy.DIMENSIONS.y * percent).toInt()
        )
    }

    override fun renderLabels(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int) {

        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2
        renderEnergyAreaTooltip(pGuiGraphics, x, y, pMouseX, pMouseY)

        super.renderLabels(pGuiGraphics, pMouseX, pMouseY)
    }

    //FIXME: This is way too wide of an area
    private fun renderEnergyAreaTooltip(pGuiGraphics: GuiGraphics, x: Int, y: Int, pMouseX: Int, pMouseY: Int) {
        if (isMouseOver(
                pMouseX, pMouseY,
                x, y,
                ScreenTextures.Elements.Energy.LOCATION_COAL_GEN,
                ScreenTextures.Elements.Energy.DIMENSIONS
            )
        ) {
            val energy = menu.blockEntity.energyStorage

            pGuiGraphics.renderComponentTooltip(
                Minecraft.getInstance().font,
                Component
                    .literal(energy.energyStored.toString() + "/" + energy.maxEnergyStored + " FE")
                    .toFlatList(),
                pMouseX - x,
                pMouseY - y
            )
        }

    }

    private fun isMouseOver(
        mouseX: Int, mouseY: Int,
        x: Int, y: Int,
        topLeft: Vector2i,
        dimensions: Vector2i
    ): Boolean {
        return MouseUtil.isMouseOver(
            mouseX, mouseY,
            x + topLeft.x, y + topLeft.y,
            dimensions.x, dimensions.y
        )
    }

    private fun renderProgressArrow(pGuiGraphics: GuiGraphics, x: Int, y: Int) {
        if (!menu.isBurning) return

        pGuiGraphics.blitSprite(
            ScreenTextures.Elements.ArrowRight.TEXTURE,
            ScreenTextures.Elements.ArrowRight.DIMENSIONS.x,
            ScreenTextures.Elements.ArrowRight.DIMENSIONS.y,
            0,
            0,
            x + ScreenTextures.Elements.ArrowRight.POSITION.x,
            y + ScreenTextures.Elements.ArrowRight.POSITION.y,
            menu.getScaledProgressArrow(),
            ScreenTextures.Elements.ArrowRight.DIMENSIONS.y
        )
    }

    //TODO: Goes up instead of down
    private fun renderBurnProgress(pGuiGraphics: GuiGraphics, x: Int, y: Int) {
        if (menu.isBurning) {
            val fuelRemaining = menu.getScaledFuelRemaining()
            pGuiGraphics.blitSprite(
                ScreenTextures.Elements.Burn.TEXTURE,
                ScreenTextures.Elements.Burn.DIMENSIONS.x,
                ScreenTextures.Elements.Burn.DIMENSIONS.y,
                0,
                0,
                x + ScreenTextures.Elements.Burn.POSITION.x,
                y + ScreenTextures.Elements.Burn.POSITION.y,
                ScreenTextures.Elements.Burn.DIMENSIONS.x,
                fuelRemaining
            )
        }
    }

    override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick)
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick)
        renderTooltip(pGuiGraphics, pMouseX, pMouseY)
    }

}