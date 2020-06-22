package uk.co.renbinden.onrails.timer

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class TimerTask(var time: Double, var task: () -> Unit) : Component {
    companion object : ComponentMapper<TimerTask>(TimerTask::class)
}