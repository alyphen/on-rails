package uk.co.renbinden.onrails.particle

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem

class ParticleSystem : IteratingSystem({
    it.has(Particle)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        entity[Particle].effect.onTick(dt)
    }
}