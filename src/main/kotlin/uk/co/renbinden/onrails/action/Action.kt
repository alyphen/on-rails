package uk.co.renbinden.onrails.action

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Action(val onAction: () -> Unit) : Component {
    companion object : ComponentMapper<Action>(Action::class)
}