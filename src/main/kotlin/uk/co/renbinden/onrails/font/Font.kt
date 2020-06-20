package uk.co.renbinden.onrails.font

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Font(val font: String) : Component {
    companion object : ComponentMapper<Font>(Font::class)
}