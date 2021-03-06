package uk.co.renbinden.onrails.position

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Position(var x: Double, var y: Double) : Component {
    fun distanceSquared(position: Position): Double {
        val xDist = position.x - x
        val yDist = position.y - y
        return (xDist * xDist) + (yDist * yDist)
    }

    companion object : ComponentMapper<Position>(Position::class)
}