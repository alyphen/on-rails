package uk.co.renbinden.onrails.direction

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.onrails.velocity.Velocity

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    companion object {
        fun fromVelocity(velocity: Velocity) = when {
            velocity.dx > 0 -> RIGHT
            velocity.dx < 0 -> LEFT
            velocity.dy > 0 -> DOWN
            velocity.dy < 0 -> UP
            else -> null
        }

        fun Entity.move(direction: Direction, speed: Double) {
            val velocity = get(Velocity)
            when (direction) {
                UP -> {
                    velocity.dx = 0.0
                    velocity.dy = -speed
                }
                DOWN -> {
                    velocity.dx = 0.0
                    velocity.dy = speed
                }
                LEFT -> {
                    velocity.dx = -speed
                    velocity.dy = 0.0
                }
                RIGHT -> {
                    velocity.dx = speed
                    velocity.dy = 0.0
                }
            }
        }

        val Entity.direction: Direction?
            get() = fromVelocity(this[Velocity])
    }
}