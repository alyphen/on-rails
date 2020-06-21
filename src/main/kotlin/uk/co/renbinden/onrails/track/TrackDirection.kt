package uk.co.renbinden.onrails.track

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper
import uk.co.renbinden.onrails.direction.Direction

data class TrackDirection(private val directionMap: MutableMap<Direction, Direction>) : Component {
    fun getNewDirection(oldDirection: Direction) = directionMap[oldDirection]
    fun setNewDirection(oldDirection: Direction, newDirection: Direction) {
        directionMap[oldDirection] = newDirection
    }
    companion object : ComponentMapper<TrackDirection>(TrackDirection::class)
}