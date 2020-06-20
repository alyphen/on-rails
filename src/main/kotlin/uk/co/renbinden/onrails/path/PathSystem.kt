package uk.co.renbinden.onrails.path

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.onrails.position.Position

class PathSystem : IteratingSystem({
    it.has(Path) && it.has(Position)
}) {
    var t: Double = 0.0

    override fun onTick(dt: Double) {
        t += dt
        super.onTick(dt)
    }
    override fun processEntity(entity: Entity, dt: Double) {
        val path = entity[Path]
        val position = entity[Position]
        position.x = path.determineX(t)
        position.y = path.determineY(t)
    }
}