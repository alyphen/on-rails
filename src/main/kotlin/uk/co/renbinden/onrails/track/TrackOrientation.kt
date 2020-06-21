package uk.co.renbinden.onrails.track

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

enum class TrackOrientation : Component {

    VERTICAL,
    HORIZONTAL,
    NORTH_OR_EAST_POINTS,
    NORTH_OR_WEST_POINTS,
    NORTH_TO_EAST,
    NORTH_TO_WEST,
    SOUTH_OR_EAST_POINTS,
    SOUTH_OR_WEST_POINTS,
    SOUTH_TO_EAST,
    SOUTH_TO_WEST;

    companion object : ComponentMapper<TrackOrientation>(TrackOrientation::class)
}