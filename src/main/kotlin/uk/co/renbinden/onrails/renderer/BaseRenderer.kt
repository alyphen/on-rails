package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

class BaseRenderer(val canvas: HTMLCanvasElement, val ctx: CanvasRenderingContext2D) : Renderer {
    override fun onRender() {
        ctx.clearRect(0.0, 0.0, canvas.width.toDouble(), canvas.height.toDouble())
    }
}