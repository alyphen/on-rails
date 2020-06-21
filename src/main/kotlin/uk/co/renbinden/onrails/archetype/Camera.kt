package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.position.Position

object Camera {
    operator fun invoke(following: Entity) = entity {
        val followingPosition = following[Position]
        add(Position(followingPosition.x, followingPosition.y))
        add(uk.co.renbinden.onrails.camera.Camera(following))
    }
}