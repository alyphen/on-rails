package uk.co.renbinden.onrails.levels

import uk.co.renbinden.ilse.asset.TextAsset
import uk.co.renbinden.ilse.asset.event.AssetLoadEvent
import uk.co.renbinden.ilse.collision.RectangleCollider
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.tiled.TiledMapLoader
import uk.co.renbinden.onrails.archetype.Camera
import uk.co.renbinden.onrails.archetype.DreamBubbleSpawner
import uk.co.renbinden.onrails.archetype.Track
import uk.co.renbinden.onrails.archetype.Train
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.collision.Collider
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.end.End
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.track.TrackOrientation.*

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class LevelLoadListener(
    engine: Engine,
    assets: Assets,
    map: TextAsset,
    showEndConversation: () -> Unit
) : Listener<AssetLoadEvent>(handler@{
    loadMapNow(engine, assets, map, showEndConversation)
})

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
private fun loadMapNow(
    engine: Engine,
    assets: Assets,
    map: TextAsset,
    showEndConversation: () -> Unit
) {
    val tiledMap = TiledMapLoader.loadMap(map) ?: return
    tiledMap.layers.forEach { layer ->
        layer.data.tiles.chunked(tiledMap.width).forEachIndexed { y, col ->
            col.forEachIndexed { x, tileInstance ->
                when (tileInstance?.get(tiledMap)?.image?.source) {
                    "../images/horizontaltracktile.png" -> HORIZONTAL
                    "../images/verticaltracktile.png" -> VERTICAL
                    "../images/northoreastpoints.png" -> NORTH_OR_EAST_POINTS
                    "../images/northoreastpointsB.png" -> NORTH_OR_EAST_POINTS_B
                    "../images/northorwestpoints.png" -> NORTH_OR_WEST_POINTS
                    "../images/northorwestpointsB.png" -> NORTH_OR_WEST_POINTS_B
                    "../images/northtoeasttracktile.png" -> NORTH_TO_EAST
                    "../images/northtowesttracktile.png" -> NORTH_TO_WEST
                    "../images/southoreastpoints.png" -> SOUTH_OR_EAST_POINTS
                    "../images/southoreastBpoints.png" -> SOUTH_OR_EAST_POINTS_B
                    "../images/southorwestpoints.png" -> SOUTH_OR_WEST_POINTS
                    "../images/westorsouthpoints.png" -> SOUTH_OR_WEST_POINTS_B
                    "../images/southtoeasttracktile.png" -> SOUTH_TO_EAST
                    "../images/southtowesttracktile.png" -> SOUTH_TO_WEST
                    else -> null
                }
                    ?.let { orientation -> Track(assets, x * 64.0, y * 64.0, orientation) }
                    ?.let(engine::add)
            }
        }
    }
    tiledMap.objectGroups.forEach { objectGroup ->
        objectGroup.objects.forEach { obj ->
            when (obj.type) {
                "train" -> {
                    val train = Train(assets, obj.x, obj.y)
                    engine.add(train)
                    val camera = Camera(train)
                    engine.add(camera)
                }
                "dream_bubble_spawner" -> {
                    engine.add(DreamBubbleSpawner(
                        engine,
                        assets,
                        DreamBubbleEmotion.valueOf(obj.properties["emotion"] as String),
                        obj.x,
                        obj.y,
                        (obj.properties["time"] as Float).toDouble()
                    ))
                }
                "blue_station" -> {
                    engine.add(entity {
                        add(Image(assets.images.blueStation))
                        add(Depth(0))
                        add(Position(obj.x, obj.y))
                    })
                }
                "green_station" -> {
                    engine.add(entity {
                        add(Image(assets.images.greenStation))
                        add(Depth(0))
                        add(Position(obj.x, obj.y))
                    })
                }
                "red_station" -> {
                    engine.add(entity {
                        add(Image(assets.images.redStation))
                        add(Depth(0))
                        add(Position(obj.x, obj.y))
                    })
                }
                "yellow_station" -> {
                    engine.add(entity {
                        add(Image(assets.images.yellowStation))
                        add(Depth(0))
                        add(Position(obj.x, obj.y))
                    })
                }
                "end" -> {
                    engine.add(entity {
                        add(Position(obj.x, obj.y))
                        add(Bounds(obj.width, obj.height))
                        add(Collider(RectangleCollider(
                            this[Position]::x,
                            this[Position]::y,
                            this[Bounds]::width,
                            this[Bounds]::height
                        )))
                        add(End(showEndConversation))
                    })
                }
            }
        }
    }
}

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
fun Engine.loadMap(assets: Assets, map: TextAsset, showEndConversation: () -> Unit) {
    if (!map.isLoaded) {
        val levelLoadListener = LevelLoadListener(this, assets, map, showEndConversation)
        Events.listenOnce(AssetLoadEvent, { event -> event.asset == map }, levelLoadListener)
    } else {
        loadMapNow(this, assets, map, showEndConversation)
    }
}