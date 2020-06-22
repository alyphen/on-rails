package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.camera.Camera
import uk.co.renbinden.onrails.position.Position

class TiledBackgroundImageRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val engine: Engine,
    val asset: ImageAsset
) : Renderer {
    override fun onRender() {
        val camera = engine.entities.firstOrNull { it.has(Camera) && it.has(Position) }
        val cameraPosition = camera?.get(Position)
        var x = if (cameraPosition != null) {
            -(cameraPosition.x / 4.0)
        } else {
            0.0
        }
        while (x <= 800) {
            var y = if (cameraPosition != null) {
                -(cameraPosition.y / 4.0)
            } else {
                0.0
            }
            while (y <= 600) {
                ctx.drawImage(asset.image, x, y)
                y += asset.image.height - 1.0
            }
            x += asset.image.width - 1.0
        }
    }
}