package uk.co.renbinden.onrails.path

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Path(
    val determineX: (Double) -> Double,
    val determineY: (Double) -> Double
) : Component {
    companion object : ComponentMapper<Path>(Path::class)
}