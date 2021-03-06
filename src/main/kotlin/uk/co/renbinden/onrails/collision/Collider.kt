package uk.co.renbinden.onrails.collision

import uk.co.renbinden.ilse.collision.RectangleCollider
import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Collider(val collider: RectangleCollider) : Component {
    companion object : ComponentMapper<Collider>(Collider::class)
}