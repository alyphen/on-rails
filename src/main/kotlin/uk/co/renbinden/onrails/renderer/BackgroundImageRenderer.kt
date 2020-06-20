package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.asset.ImageAsset

class BackgroundImageRenderer(
    val canvas: HTMLCanvasElement,
    val ctx: CanvasRenderingContext2D,
    val asset: ImageAsset
) : Renderer {
    override fun onRender() {
        ctx.drawImage(asset.image, 0.0, 0.0)
    }
}