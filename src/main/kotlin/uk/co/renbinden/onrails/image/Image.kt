package uk.co.renbinden.onrails.image

import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class Image(val asset: ImageAsset) : Component {
    companion object : ComponentMapper<Image>(Image::class)
}