package club.sk1er.elementa.components

import club.sk1er.elementa.UIComponent
import club.sk1er.elementa.dsl.asConstraint
import club.sk1er.elementa.dsl.pixels
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class UICircle @JvmOverloads constructor(radius: Float = 0f, color: Color = Color(0, 0, 0, 0), var steps: Int = 40) : UIComponent() {
    init {
        setColor(color.asConstraint())
        setRadius(radius.pixels())
    }

    override fun isHovered(): Boolean {
        val res = Window.of(this).scaledResolution
        val mc = Minecraft.getMinecraft()

        val mouseX = Mouse.getX() * res.scaledWidth / mc.displayWidth
        val mouseY = res.scaledHeight - Mouse.getY() * res.scaledHeight / mc.displayHeight - 1f

        return (mouseX > getLeft() - getRadius()
                && mouseY > getTop() - getRadius()
                && mouseX < getLeft() + getRadius() 
                && mouseY < getTop() + getRadius())
    }

    override fun draw() {
        beforeDraw()

        val x = getLeft().toDouble()
        val y = getTop().toDouble()
        val r = getRadius().toDouble()

        val color = getColor()
        if (color.alpha == 0) return super.draw()


        GL11.glPushMatrix()

        GlStateManager.enableBlend()
        GlStateManager.disableTexture2D()

        val tessellator = Tessellator.getInstance()
        val worldRenderer = tessellator.worldRenderer

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)

        GlStateManager.color(color.red.toFloat() / 255f, color.green.toFloat() / 255f, color.blue.toFloat() / 255f, color.alpha.toFloat() / 255f)

        worldRenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION)

        var theta = 0.0
        for (i in 0 until steps) {
            worldRenderer.pos(cos(theta) * r + x,  -sin(theta) * r + y, 0.0).endVertex()
            theta += (2 * PI) / steps
        }

        tessellator.draw()

        GlStateManager.color(1f, 1f, 1f, 1f)

        GlStateManager.enableTexture2D()
        GlStateManager.disableBlend()

        GL11.glPopMatrix()

        super.draw()
    }
}