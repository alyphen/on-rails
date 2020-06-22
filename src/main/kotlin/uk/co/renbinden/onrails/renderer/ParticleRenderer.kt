package uk.co.renbinden.onrails.renderer

import org.w3c.dom.CanvasRenderingContext2D
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.onrails.camera.Camera
import uk.co.renbinden.onrails.particle.Particle
import uk.co.renbinden.onrails.position.Position

class ParticleRenderer(val ctx: CanvasRenderingContext2D, val engine: Engine) : Renderer {
    override fun onRender() {
        val camera = engine.entities.firstOrNull { it.has(Camera) && it.has(Position) }
        if (camera != null) {
            val cameraPosition = camera[Position]
            ctx.translate(-(cameraPosition.x - 400), -(cameraPosition.y - 300))
        }
        engine.entities.filter { it.has(Particle) }
            .forEach { it[Particle].effect.onRender() }
        if (camera != null) {
            val cameraPosition = camera[Position]
            ctx.translate(cameraPosition.x - 400, cameraPosition.y - 300)
        }
    }
}