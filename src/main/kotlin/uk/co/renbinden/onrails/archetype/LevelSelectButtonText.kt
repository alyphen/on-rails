package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.fillstyle.FillStyle
import uk.co.renbinden.onrails.font.Font
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.text.Text

object LevelSelectButtonText {
    operator fun invoke(text: String, x: Double, y: Double) = entity {
        add(Text(text, 32.0))
        add(FillStyle("rgb(255, 255, 255)"))
        add(Font("32px 'Chelsea Market', cursive"))
        add(Position(x, y))
    }
}