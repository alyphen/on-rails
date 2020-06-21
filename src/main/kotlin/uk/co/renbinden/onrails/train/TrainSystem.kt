package uk.co.renbinden.onrails.train

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.direction.Direction.Companion.move
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.track.TrackDirection
import uk.co.renbinden.onrails.track.TrackOrientation
import uk.co.renbinden.onrails.velocity.Velocity
import kotlin.math.abs

class TrainSystem : IteratingSystem({
    it.has(Train)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val trainPosition = entity[Position]
        val track = engine.entities.firstOrNull { trackEntity ->
            if (!trackEntity.has(TrackOrientation) || !trackEntity.has(TrackDirection) || !trackEntity.has(Position)) return@firstOrNull false
            val trackPosition = trackEntity[Position]
            return@firstOrNull abs(trackPosition.x - (trainPosition.x + 32)) < 2.0
                    && abs(trackPosition.y - (trainPosition.y + 32)) < 2.0
        }
        if (track != null) {
            val oldDirection = Direction.fromVelocity(entity[Velocity])
            if (oldDirection != null) {
                val newDirection = track[TrackDirection].getNewDirection(oldDirection)
                if (newDirection != null) {
                    entity.move(newDirection, 128.0)
                }
            }
        }
    }
}