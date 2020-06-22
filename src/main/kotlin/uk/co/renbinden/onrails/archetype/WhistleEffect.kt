package uk.co.renbinden.onrails.archetype

import org.w3c.dom.CanvasRenderingContext2D
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.particle.Particle
import uk.co.renbinden.onrails.particle.whistle.WhistleParticleEffect

object WhistleEffect {
    operator fun invoke(ctx: CanvasRenderingContext2D, train: Entity, startRadius: Double) = entity {
        add(Particle(WhistleParticleEffect(ctx, train, startRadius)))
    }
}