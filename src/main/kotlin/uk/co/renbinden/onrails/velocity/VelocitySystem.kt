package uk.co.renbinden.onrails.velocity

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.position.Position

class VelocitySystem : IteratingSystem({
    it.has(Position) && it.has(Velocity)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val position = entity[Position]
        val velocity = entity[Velocity]
        position.x += velocity.dx
        position.y += velocity.dy
    }
}