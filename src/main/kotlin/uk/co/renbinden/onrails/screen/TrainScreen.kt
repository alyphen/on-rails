package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.KeyDownEvent
import uk.co.renbinden.onrails.animation.AnimationSystem
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.camera.CameraSystem
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.direction.Direction.*
import uk.co.renbinden.onrails.levels.loadMap
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.BaseRenderer
import uk.co.renbinden.onrails.renderer.ImageRenderer
import uk.co.renbinden.onrails.renderer.RenderPipeline
import uk.co.renbinden.onrails.renderer.SolidBackgroundRenderer
import uk.co.renbinden.onrails.track.TrackDirection
import uk.co.renbinden.onrails.track.TrackOrientation
import uk.co.renbinden.onrails.track.TrackOrientation.*
import uk.co.renbinden.onrails.train.Train
import uk.co.renbinden.onrails.train.TrainSystem
import uk.co.renbinden.onrails.velocity.VelocitySystem
import kotlin.browser.document
import kotlin.math.abs

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class TrainScreen(val app: App, val assets: Assets) : Screen(engine {
    add(VelocitySystem())
    add(AnimationSystem())
    add(CameraSystem())
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
        ImageRenderer(canvas, ctx, engine)
    )

    private val keyDownListener = Listener<KeyDownEvent>({ event ->
        if (event.key == "a" || event.key == "ArrowLeft") {
            getNextPoints()?.switchPointsLeft()
        } else if (event.key == "d" || event.key == "ArrowRight") {
            getNextPoints()?.switchPointsRight()
        } else if (event.key == "s") {
//            val train = engine.entities.firstOrNull { it.has(Train) && it.has(Position) }?.get(Train)
//            train?.lastTrack?.nextTrack(train.lastDirection)?.let(engine::remove)
            getNextPoints()?.let(engine::remove)
        }
    })

    private val pointsOrientations = listOf(
        NORTH_OR_EAST_POINTS,
        NORTH_OR_EAST_POINTS_B,
        NORTH_OR_WEST_POINTS,
        NORTH_OR_WEST_POINTS_B,
        SOUTH_OR_EAST_POINTS,
        SOUTH_OR_EAST_POINTS_B,
        SOUTH_OR_WEST_POINTS,
        SOUTH_OR_WEST_POINTS_B
    )

    fun getNextPoints(): Entity? {
        val train = engine.entities.firstOrNull { it.has(Train) && it.has(Position) }
        if (train != null) {
            val trainDirection = train[Train].lastDirection
            val currentTrack = train[Train].lastTrack
            var direction = trainDirection
            var track = currentTrack?.nextTrack(direction) ?: return null
            while (!pointsOrientations.contains(track[TrackOrientation])) {
                direction = track[TrackDirection].getNewDirection(direction) ?: return null
                track = track.nextTrack(direction) ?: return null
            }
            return track
        }
        return null
    }

    fun Entity.nextTrack(direction: Direction): Entity? {
        val next = engine.entities.firstOrNull { track ->
            track.has(Position)
                    && track.has(TrackDirection)
                    && when(direction) {
                UP -> abs(this[Position].y - 64 - track[Position].y) < 2.0 && abs(this[Position].x - track[Position].x) < 2.0
                DOWN -> abs(this[Position].y + 64 - track[Position].y) < 2.0 && abs(this[Position].x - track[Position].x) < 2.0
                LEFT -> abs(this[Position].x - 64 - track[Position].x) < 2.0 && abs(this[Position].y - track[Position].y) < 2.0
                RIGHT -> abs(this[Position].x + 64 - track[Position].x) < 2.0 && abs(this[Position].y - track[Position].y) < 2.0
            }
        }
        return next
    }

    fun Entity.switchPointsLeft() {
        val trackDirection = get(TrackDirection)
        when (get(TrackOrientation)) {
            NORTH_OR_EAST_POINTS -> {
                trackDirection.setNewDirection(UP, UP)
                trackDirection.setNewDirection(LEFT, DOWN)
                trackDirection.setNewDirection(DOWN, DOWN)
            }
            NORTH_OR_EAST_POINTS_B -> {
                trackDirection.setNewDirection(RIGHT, UP)
                trackDirection.setNewDirection(LEFT, LEFT)
                trackDirection.setNewDirection(DOWN, LEFT)
            }
            NORTH_OR_WEST_POINTS -> {
                trackDirection.setNewDirection(UP, LEFT)
                trackDirection.setNewDirection(RIGHT, UP)
                trackDirection.setNewDirection(DOWN, LEFT)
            }
            NORTH_OR_WEST_POINTS_B -> {
                trackDirection.setNewDirection(LEFT, LEFT)
                trackDirection.setNewDirection(RIGHT, UP)
                trackDirection.setNewDirection(DOWN, LEFT)
            }
            SOUTH_OR_EAST_POINTS -> {
                trackDirection.setNewDirection(DOWN, DOWN)
                trackDirection.setNewDirection(LEFT, DOWN)
                trackDirection.setNewDirection(UP, UP)
            }
            SOUTH_OR_EAST_POINTS_B -> {
                trackDirection.setNewDirection(RIGHT, RIGHT)
                trackDirection.setNewDirection(UP, LEFT)
                trackDirection.setNewDirection(LEFT, LEFT)
            }
            SOUTH_OR_WEST_POINTS -> {
                trackDirection.setNewDirection(DOWN, LEFT)
                trackDirection.setNewDirection(UP, LEFT)
                trackDirection.setNewDirection(RIGHT, UP)
            }
            SOUTH_OR_WEST_POINTS_B -> {
                trackDirection.setNewDirection(LEFT, LEFT)
                trackDirection.setNewDirection(UP, LEFT)
                trackDirection.setNewDirection(RIGHT, RIGHT)
            }
            else -> Unit
        }
    }

    fun Entity.switchPointsRight() {
        val trackDirection = get(TrackDirection)
        when (get(TrackOrientation)) {
            NORTH_OR_EAST_POINTS -> {
                trackDirection.setNewDirection(UP, RIGHT)
                trackDirection.setNewDirection(DOWN, RIGHT)
                trackDirection.setNewDirection(LEFT, UP)
            }
            NORTH_OR_EAST_POINTS_B -> {
                trackDirection.setNewDirection(RIGHT, RIGHT)
                trackDirection.setNewDirection(LEFT, UP)
                trackDirection.setNewDirection(DOWN, RIGHT)
            }
            NORTH_OR_WEST_POINTS -> {
                trackDirection.setNewDirection(UP, UP)
                trackDirection.setNewDirection(RIGHT, DOWN)
                trackDirection.setNewDirection(DOWN, DOWN)
            }
            NORTH_OR_WEST_POINTS_B -> {
                trackDirection.setNewDirection(LEFT, LEFT)
                trackDirection.setNewDirection(RIGHT, UP)
                trackDirection.setNewDirection(DOWN, RIGHT)
            }
            SOUTH_OR_EAST_POINTS -> {
                trackDirection.setNewDirection(DOWN, RIGHT)
                trackDirection.setNewDirection(UP, RIGHT)
                trackDirection.setNewDirection(LEFT, UP)
            }
            SOUTH_OR_EAST_POINTS_B -> {
                trackDirection.setNewDirection(RIGHT, RIGHT)
                trackDirection.setNewDirection(UP, RIGHT)
                trackDirection.setNewDirection(LEFT, DOWN)
            }
            SOUTH_OR_WEST_POINTS -> {
                trackDirection.setNewDirection(DOWN, DOWN)
                trackDirection.setNewDirection(UP, UP)
                trackDirection.setNewDirection(RIGHT, DOWN)
            }
            SOUTH_OR_WEST_POINTS_B -> {
                trackDirection.setNewDirection(LEFT, DOWN)
                trackDirection.setNewDirection(RIGHT, DOWN)
                trackDirection.setNewDirection(UP, RIGHT)
            }
            else -> Unit
        }
    }

    init {
        engine.add(TrainSystem(assets))
        engine.loadMap(assets, assets.maps.overworld)
        addListeners()
    }

    private fun addListeners() {
        Events.addListener(KeyDownEvent, keyDownListener)
    }

    private fun removeListeners() {
        Events.removeListener(KeyDownEvent, keyDownListener)
    }

    override fun onRender() {
        pipeline.onRender()
    }
}