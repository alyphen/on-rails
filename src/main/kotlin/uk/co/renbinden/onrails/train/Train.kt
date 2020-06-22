package uk.co.renbinden.onrails.train

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.direction.Direction.UP

class Train(var lastTrack: Entity? = null, var lastDirection: Direction = UP) : Component {
    companion object : ComponentMapper<Train>(Train::class)
}