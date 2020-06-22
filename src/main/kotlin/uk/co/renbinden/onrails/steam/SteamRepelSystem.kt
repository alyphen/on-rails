package uk.co.renbinden.onrails.steam

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.collision.Collider
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.timer.TimerTask
import uk.co.renbinden.onrails.velocity.Velocity

class SteamRepelSystem : IteratingSystem({
    it.has(Steam) && it.has(Position)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val position = entity[Position]
        val others = engine.entities.filter {
            it != entity
                    && it.has(Collider)
                    && it.has(Position)
                    && it.has(Velocity)
                    && it.has(DreamBubbleEmotion)
        }.associateBy { it[Collider].collider }
        val collider = entity[Collider]
        val collisions = collider.collider.getCollisions(others.keys.toList()).mapNotNull(others::get)
        if (collisions.isNotEmpty()) {
            engine.remove(entity)
            collisions.forEach { dreamBubble ->
                val dreamBubblePosition = dreamBubble[Position]
                val dreamBubbleVelocity = dreamBubble[Velocity]
                dreamBubbleVelocity.dx = (dreamBubblePosition.x - position.x) * 2
                dreamBubbleVelocity.dy = (dreamBubblePosition.y - position.y) * 2
                if (dreamBubble.has(TimerTask)) {
                    dreamBubble.remove(TimerTask)
                }
            }
        }
    }
}