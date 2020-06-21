package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.track.TrackOrientation
import uk.co.renbinden.onrails.track.TrackOrientation.*

object Track {
    operator fun invoke(assets: Assets, x: Double, y: Double, orientation: TrackOrientation) = entity {
        when (orientation) {
            HORIZONTAL -> {
                add(Image(assets.images.horizontalTrackTile))
            }
            VERTICAL -> {
                add(Image(assets.images.verticalTrackTile))
            }
            NORTH_OR_EAST_POINTS -> {
                add(Image(assets.images.northOrEastPoints))
            }
            NORTH_OR_WEST_POINTS -> {
                add(Image(assets.images.northOrWestPoints))
            }
            NORTH_TO_EAST -> {
                add(Image(assets.images.northToEastTrackTile))
            }
            NORTH_TO_WEST -> {
                add(Image(assets.images.northToWestTrackTile))
            }
            SOUTH_OR_EAST_POINTS -> {
                add(Image(assets.images.southOrEastPoints))
            }
            SOUTH_OR_WEST_POINTS -> {
                add(Image(assets.images.southOrWestPoints))
            }
            SOUTH_TO_EAST -> {
                add(Image(assets.images.southToEastTrackTile))
            }
            SOUTH_TO_WEST -> {
                add(Image(assets.images.southToWestTrackTile))
            }
        }
        add(Depth(0))
        add(Position(x, y))
        add(orientation)
    }
}