package uk.co.renbinden.onrails.timer

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem

class TimerSystem : IteratingSystem({
    it.has(TimerTask)
}) {
    override fun processEntity(entity: Entity, dt: Double) {
        val timerTask = entity[TimerTask]
        timerTask.time -= dt
        if (timerTask.time <= 0.0) {
            entity.remove(TimerTask)
            timerTask.task()
        }
    }
}