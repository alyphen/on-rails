package uk.co.renbinden.onrails.end

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class End(val action: () -> Unit) : Component {
    companion object : ComponentMapper<End>(End::class)
}