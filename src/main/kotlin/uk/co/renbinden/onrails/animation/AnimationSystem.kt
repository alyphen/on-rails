package uk.co.renbinden.onrails.animation

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem

class AnimationSystem : IteratingSystem({
    it.has(Animation)
}) {

    override fun processEntity(entity: Entity, dt: Double) {
        entity[Animation].t += dt
    }

}