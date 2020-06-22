package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.action.Action
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.hover.HoverImage
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position

object LevelSelectButton {
    operator fun invoke(assets: Assets, x: Double, y: Double, action: () -> Unit) = entity {
        add(Position(x, y))
        add(Image(assets.images.levelSelectButton))
        add(HoverImage(assets.images.levelSelectButton, assets.images.levelSelectButtonHover))
        add(Depth(-1))
        add(Bounds(64.0, 64.0))
        add(Action(action))
    }
}