package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.animation.Animation
import uk.co.renbinden.onrails.camera.Camera
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position

class ImageRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val engine: Engine
) : Renderer {
    override fun onRender() {
        val camera = engine.entities.firstOrNull { it.has(Camera) && it.has(Position) }
        if (camera != null) {
            val cameraPosition = camera[Position]
            ctx.translate(-(cameraPosition.x - 400), -(cameraPosition.y - 300))
        }
        engine.entities
            .filter { it.has(Position) && it.has(Depth) && (it.has(Image) || it.has(Animation)) }
            .sortedByDescending { entity -> entity[Depth].depth }
            .forEach { entity ->
                if (entity.has(Image)) {
                    val image = entity[Image]
                    val position = entity[Position]
                    if (image.asset.isLoaded) {
                        ctx.drawImage(image.asset.image, position.x, position.y)
                    }
                } else if (entity.has(Animation)) {
                    val animation = entity[Animation]
                    val position = entity[Position]
                    if (animation.asset.isLoaded) {
                        animation.asset.drawFrame(animation.getFrame(), ctx, position.x, position.y)
                    }
                }
            }
        if (camera != null) {
            val cameraPosition = camera[Position]
            ctx.translate(cameraPosition.x - 400, cameraPosition.y - 300)
        }
    }
}