package uk.co.renbinden.onrails.steam

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

class Steam : Component {
    companion object : ComponentMapper<Steam>(Steam::class)
}