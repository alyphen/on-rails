package uk.co.renbinden.onrails.camera

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper
import uk.co.renbinden.ilse.ecs.entity.Entity

data class Camera(val following: Entity) : Component {
    companion object : ComponentMapper<Camera>(Camera::class)
}