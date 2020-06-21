package uk.co.renbinden.onrails.hover

import uk.co.renbinden.ilse.asset.ImageAsset
import uk.co.renbinden.ilse.ecs.component.Component
import uk.co.renbinden.ilse.ecs.component.ComponentMapper

data class HoverImage(val baseAsset: ImageAsset, val hoverAsset: ImageAsset) : Component {
    companion object : ComponentMapper<HoverImage>(HoverImage::class)
}