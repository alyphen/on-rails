package uk.co.renbinden.onrails.text

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Text(val text: String, val maxWidth: Double) : Component {
    companion object : ComponentMapper<Text>(Text::class)
}