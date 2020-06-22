package uk.co.renbinden.onrails.particle.whistle

import org.w3c.dom.CanvasRenderingContext2D
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.particle.Particle
import uk.co.renbinden.ilse.particle.particleSystem
import uk.co.renbinden.onrails.position.Position
import kotlin.math.PI

object WhistleParticleEffect {
    operator fun invoke(ctx: CanvasRenderingContext2D, train: Entity, startRadius: Double) = particleSystem(0.5) {
        var radius = startRadius
        var t = 0.0
        particles.add(Particle(
            0.5,
            { dt ->
                t += dt
                radius = (0.5 - t) * startRadius
            },
            {
                ctx.lineWidth = 4.0
                ctx.strokeStyle = "#ffffff"
                ctx.beginPath()
                ctx.arc(train[Position].x + 64, train[Position].y + 64, radius, 0.0, 2 * PI, false)
                ctx.stroke()
            }
        ))
    }
}