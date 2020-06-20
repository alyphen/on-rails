package uk.co.renbinden.onrails.fillstyle

import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class FillStyle(val color: String) : Component {
    companion object : ComponentMapper<FillStyle>(FillStyle::class)
}