package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

class SolidBackgroundRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val color: String
) : Renderer {
    override fun onRender() {
        ctx.fillStyle = color
        ctx.fillRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }
}