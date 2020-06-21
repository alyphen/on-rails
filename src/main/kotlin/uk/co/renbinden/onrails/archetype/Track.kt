package uk.co.renbinden.onrails.archetype

import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.direction.Direction.*
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.track.TrackDirection
import uk.co.renbinden.onrails.track.TrackOrientation
import uk.co.renbinden.onrails.track.TrackOrientation.*

object Track {
    operator fun invoke(assets: Assets, x: Double, y: Double, orientation: TrackOrientation) = entity {
        when (orientation) {
            HORIZONTAL -> {
                add(Image(assets.images.horizontalTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to LEFT,
                    RIGHT to RIGHT,
                    UP to DOWN,
                    DOWN to UP
                )))
            }
            VERTICAL -> {
                add(Image(assets.images.verticalTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to RIGHT,
                    RIGHT to LEFT,
                    UP to UP,
                    DOWN to DOWN
                )))
            }
            NORTH_OR_EAST_POINTS -> {
                add(Image(assets.images.northOrEastPoints))
                add(TrackDirection(mutableMapOf(
                    LEFT to DOWN,
                    RIGHT to LEFT,
                    UP to RIGHT,
                    DOWN to DOWN
                )))
            }
            NORTH_OR_EAST_POINTS_B -> {
                add(Image(assets.images.northOrEastPointsB))
                add(TrackDirection(mutableMapOf(
                    LEFT to LEFT,
                    RIGHT to UP,
                    UP to DOWN,
                    DOWN to LEFT
                )))
            }
            NORTH_OR_WEST_POINTS -> {
                add(Image(assets.images.northOrWestPoints))
                add(TrackDirection(mutableMapOf(
                    LEFT to RIGHT,
                    RIGHT to DOWN,
                    UP to LEFT,
                    DOWN to DOWN
                )))
            }
            NORTH_OR_WEST_POINTS_B -> {
                add(Image(assets.images.northOrWestPointsB))
                add(TrackDirection(mutableMapOf(
                    LEFT to UP,
                    RIGHT to RIGHT,
                    UP to DOWN,
                    DOWN to RIGHT
                )))
            }
            NORTH_TO_EAST -> {
                add(Image(assets.images.northToEastTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to UP,
                    RIGHT to LEFT,
                    UP to DOWN,
                    DOWN to RIGHT
                )))
            }
            NORTH_TO_WEST -> {
                add(Image(assets.images.northToWestTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to RIGHT,
                    RIGHT to UP,
                    UP to DOWN,
                    DOWN to LEFT
                )))
            }
            SOUTH_OR_EAST_POINTS -> {
                add(Image(assets.images.southOrEastPoints))
                add(TrackDirection(mutableMapOf(
                    LEFT to UP,
                    RIGHT to LEFT,
                    UP to UP,
                    DOWN to RIGHT
                )))
            }
            SOUTH_OR_EAST_POINTS_B -> {
                add(Image(assets.images.southOrEastPointsB))
                add(TrackDirection(mutableMapOf(
                    LEFT to LEFT,
                    RIGHT to DOWN,
                    UP to LEFT,
                    DOWN to UP
                )))
            }
            SOUTH_OR_WEST_POINTS -> {
                add(Image(assets.images.southOrWestPoints))
                add(TrackDirection(mutableMapOf(
                    LEFT to RIGHT,
                    RIGHT to UP,
                    UP to UP,
                    DOWN to LEFT
                )))
            }
            SOUTH_OR_WEST_POINTS_B -> {
                add(Image(assets.images.southOrWestPointsB))
                add(TrackDirection(mutableMapOf(
                    LEFT to DOWN,
                    RIGHT to RIGHT,
                    UP to RIGHT,
                    DOWN to UP
                )))
            }
            SOUTH_TO_EAST -> {
                add(Image(assets.images.southToEastTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to DOWN,
                    RIGHT to LEFT,
                    UP to RIGHT,
                    DOWN to LEFT
                )))
            }
            SOUTH_TO_WEST -> {
                add(Image(assets.images.southToWestTrackTile))
                add(TrackDirection(mutableMapOf(
                    LEFT to RIGHT,
                    RIGHT to DOWN,
                    UP to LEFT,
                    DOWN to UP
                )))
            }
        }
        add(Depth(0))
        add(Position(x, y))
        add(orientation)
    }
}