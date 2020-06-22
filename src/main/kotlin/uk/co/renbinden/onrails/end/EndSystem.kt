package uk.co.renbinden.onrails.end

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.collision.Collider
import uk.co.renbinden.onrails.train.Train

class EndSystem : IteratingSystem({
    it.has(End) && it.has(Collider)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val train = engine.entities.firstOrNull { it.has(Train) && it.has(Collider) } ?: return
        val endCollider = entity[Collider]
        val trainCollider = train[Collider]
        if (endCollider.collider.collidesWith(trainCollider.collider)) {
            entity[End].action()
        }
    }
}