package uk.co.renbinden.onrails.levels

import uk.co.renbinden.ilse.asset.TextAsset
import uk.co.renbinden.ilse.asset.event.AssetLoadEvent
import uk.co.renbinden.ilse.ecs.Engine
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.tiled.TiledMapLoader
import uk.co.renbinden.onrails.archetype.Camera
import uk.co.renbinden.onrails.archetype.Track
import uk.co.renbinden.onrails.archetype.Train
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.track.TrackOrientation.*

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class LevelLoadListener(
    engine: Engine,
    assets: Assets,
    map: TextAsset
) : Listener<AssetLoadEvent>(handler@{
    loadMapNow(engine, assets, map)
})

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
private fun loadMapNow(
    engine: Engine,
    assets: Assets,
    map: TextAsset
) {
    val tiledMap = TiledMapLoader.loadMap(map) ?: return
    tiledMap.layers.forEach { layer ->
        layer.data.tiles.chunked(tiledMap.width).forEachIndexed { y, col ->
            col.forEachIndexed { x, tileInstance ->
                when (tileInstance?.get(tiledMap)?.image?.source) {
                    "../images/horizontaltracktile.png" -> HORIZONTAL
                    "../images/verticaltracktile.png" -> VERTICAL
                    "../images/northoreastpoints.png" -> NORTH_OR_EAST_POINTS
                    "../images/northorwestpoints.png" -> NORTH_OR_WEST_POINTS
                    "../images/northtoeasttracktile.png" -> NORTH_TO_EAST
                    "../images/northtowesttracktile.png" -> NORTH_TO_WEST
                    "../images/southoreastpoints.png" -> SOUTH_OR_EAST_POINTS
                    "../images/southorwestpoints.png" -> SOUTH_OR_WEST_POINTS
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
                    console.log("Loaded train")
                    val train = Train(assets, obj.x, obj.y)
                    engine.add(train)
                    val camera = Camera(train)
                    engine.add(camera)
                }
            }
        }
    }
}

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
fun Engine.loadMap(assets: Assets, map: TextAsset) {
    if (!map.isLoaded) {
        val levelLoadListener = LevelLoadListener(this, assets, map)
        Events.listenOnce(AssetLoadEvent, { event -> event.asset == map }, levelLoadListener)
    } else {
        loadMapNow(this, assets, map)
    }
}