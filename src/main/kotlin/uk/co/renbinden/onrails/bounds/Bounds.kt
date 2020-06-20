package uk.co.renbinden.onrails.bounds

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Bounds(val width: Double, val height: Double) : Component {
    companion object : ComponentMapper<Bounds>(Bounds::class)
}