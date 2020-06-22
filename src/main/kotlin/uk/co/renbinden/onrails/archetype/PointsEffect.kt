package uk.co.renbinden.onrails.archetype

import org.w3c.dom.CanvasRenderingContext2D
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.particle.Particle
import uk.co.renbinden.onrails.particle.points.PointsParticleEffect
import uk.co.renbinden.onrails.timer.TimerTask

object PointsEffect {
    operator fun invoke(
        engine: Engine,
        ctx: CanvasRenderingContext2D,
        startX: Double,
        startY: Double,
        direction: Direction
    ) = entity {
        add(Particle(PointsParticleEffect(ctx, startX, startY, direction)))
        add(TimerTask(0.5) {
            engine.remove(this)
        })
    }
}