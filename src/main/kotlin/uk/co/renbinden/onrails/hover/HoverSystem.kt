package uk.co.renbinden.onrails.hover

import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.ilse.input.Input
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import kotlin.browser.window

class HoverSystem(val canvas: HTMLCanvasElement) : IteratingSystem({
    it.has(Image) && it.has(HoverImage) && it.has(Position) && it.has(Bounds)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val position = entity[Position]
        val bounds = entity[Bounds]
        val image = entity[Image]
        val hoverImage = entity[HoverImage]
        val mouseX = Input.mouseX - (canvas.getBoundingClientRect().left + window.scrollX)
        val mouseY = Input.mouseY - (canvas.getBoundingClientRect().top + window.scrollY)
        if (mouseX >= position.x && mouseY >= position.y && mouseX <= position.x + bounds.width && mouseY <= position.y + bounds.height) {
            image.asset = hoverImage.hoverAsset
        } else {
            image.asset = hoverImage.baseAsset
        }
    }
}