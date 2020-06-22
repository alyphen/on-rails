package uk.co.renbinden.onrails.particle.points

import org.w3c.dom.CanvasRenderingContext2D
import uk.co.renbinden.ilse.particle.Particle
import uk.co.renbinden.ilse.particle.particleSystem
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.direction.Direction.*
import kotlin.random.Random

object PointsParticleEffect {
    operator fun invoke(ctx: CanvasRenderingContext2D, startX: Double, startY: Double, direction: Direction) = particleSystem(0.5) {
        emitter(0.01, 0.03, 3, 5) {
            val endX = when (direction) {
                UP -> startX + Random.nextDouble(-4.0, 4.0)
                DOWN -> startX + Random.nextDouble(-4.0, 4.0)
                LEFT -> startX - 64
                RIGHT -> startX + 64
            }
            val endY = when(direction) {
                UP -> startY - 64
                DOWN -> startY + 64
                LEFT -> startY + Random.nextDouble(-4.0, 4.0)
                RIGHT -> startY + Random.nextDouble(-4.0, 4.0)
            }
            var t = 0.0
            var x = startX
            var y = startY
            Particle(
                0.2,
                { dt ->
                    t += dt
                    x = (startX * ((0.2 - t) / 0.2)) + (endX * (t / 0.2))
                    y = (startY * ((0.2 - t) / 0.2)) + (endY * (t / 0.2))
                },
                {
                    ctx.fillStyle = "#ffffff"
                    ctx.fillRect(x - 1, y - 1, 2.0, 2.0)
                }
            )
        }
    }
}