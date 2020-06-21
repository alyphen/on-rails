package uk.co.renbinden.onrails.train

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

class Train : Component {
    companion object : ComponentMapper<Train>(Train::class)
}