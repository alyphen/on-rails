package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position

class ImageRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val engine: Engine
) : Renderer {
    override fun onRender() {
        engine.entities
            .filter { it.has(Position) && it.has(Image) }
            .forEach { entity ->
                val image = entity[Image]
                val position = entity[Position]
                ctx.drawImage(image.asset.image, position.x, position.y)
            }
    }
}