package uk.co.renbinden.onrails.avatar

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class AvatarName(val name: String) : Component {
    companion object : ComponentMapper<AvatarName>(AvatarName::class)
}