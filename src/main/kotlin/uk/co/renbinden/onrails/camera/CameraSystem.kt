package uk.co.renbinden.onrails.camera

import uk.co.renbinden.ilse.ecs.system.System
import uk.co.renbinden.onrails.position.Position

class CameraSystem() : System() {

    override fun onTick(dt: Double) {
        engine.entities.filter { it.has(Camera) && it.has(Position) }
            .forEach { entity ->
                val position = entity[Position]
                val camera = entity[Camera]
                val followingPosition = camera.following[Position]
                position.x = followingPosition.x
                position.y = followingPosition.y
            }
    }

}