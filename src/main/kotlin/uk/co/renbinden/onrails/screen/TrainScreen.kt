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
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.ilse.input.mapping.Mouse
import uk.co.renbinden.onrails.animation.AnimationSystem
import uk.co.renbinden.onrails.archetype.Steam
import uk.co.renbinden.onrails.archetype.WhistleEffect
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.camera.Camera
import uk.co.renbinden.onrails.camera.CameraSystem
import uk.co.renbinden.onrails.collision.DreamCollectSystem
import uk.co.renbinden.onrails.direction.Direction
import uk.co.renbinden.onrails.direction.Direction.*
import uk.co.renbinden.onrails.dreambubble.DreamBubbleEmotion
import uk.co.renbinden.onrails.levels.loadMap
import uk.co.renbinden.onrails.particle.ParticleSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.*
import uk.co.renbinden.onrails.steam.SteamRepelSystem
import uk.co.renbinden.onrails.timer.TimerSystem
import uk.co.renbinden.onrails.timer.TimerTask
import uk.co.renbinden.onrails.track.TrackDirection
import uk.co.renbinden.onrails.track.TrackOrientation
import uk.co.renbinden.onrails.track.TrackOrientation.*
import uk.co.renbinden.onrails.train.Train
import uk.co.renbinden.onrails.train.TrainSystem
import uk.co.renbinden.onrails.velocity.Velocity
import uk.co.renbinden.onrails.velocity.VelocitySystem
import kotlin.browser.document
import kotlin.browser.window
import kotlin.experimental.and
import kotlin.math.abs
import kotlin.math.sqrt

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class TrainScreen(val app: App, val assets: Assets) : Screen(engine {
    add(TimerSystem())
    add(VelocitySystem())
    add(AnimationSystem())
    add(ParticleSystem())
    add(CameraSystem())
    add(DreamCollectSystem())
    add(SteamRepelSystem())
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
        TiledBackgroundImageRenderer(canvas, ctx, engine, assets.images.backgroundStars2),
        ImageRenderer(canvas, ctx, engine),
        ParticleRenderer(ctx, engine)
    )

    private val keyDownListener = Listener<KeyDownEvent>({ event ->
        if (event.key == "a" || event.key == "ArrowLeft") {
            getNextPoints()?.switchPointsLeft()
        } else if (event.key == "d" || event.key == "ArrowRight") {
            getNextPoints()?.switchPointsRight()
        }
    })

    private val mouseDownListener = Listener<MouseDownEvent>({ event ->
        if (event.buttons and Mouse.PRIMARY != 0.toShort()) {
            val train = engine.entities.firstOrNull { it.has(Train) && it.has(Position) }
            val camera = engine.entities.firstOrNull { it.has(Camera) && it.has(Position) }
            if (train != null && camera != null) {
                if (engine.entities.none { it.has(uk.co.renbinden.onrails.steam.Steam) }) {
                    val trainPosition = train[Position]
                    val cameraPosition = camera[Position]
                    val mouseX = event.pageX - (canvas.getBoundingClientRect().left + window.scrollX)
                    val mouseY = event.pageY - (canvas.getBoundingClientRect().top + window.scrollY)
                    val smoke = Steam(engine, assets, trainPosition.x + 32.0, trainPosition.y + 32.0)
                    val velocity = smoke[Velocity]
                    val xDist = (mouseX - 32 + (cameraPosition.x - 400)) - (trainPosition.x + 32.0)
                    val yDist = (mouseY - 32 + (cameraPosition.y - 300)) - (trainPosition.y + 32.0)
                    val magnitude = sqrt((xDist * xDist) + (yDist * yDist))
                    val newMagnitude = 256
                    velocity.dx = xDist * (newMagnitude / magnitude)
                    velocity.dy = yDist * (newMagnitude / magnitude)
                    engine.add(smoke)
                }
            }
        }
        if (event.buttons and Mouse.SECONDARY != 0.toShort()) {
            val train = engine.entities.firstOrNull { it.has(Train) && it.has(Position) }
            if (train != null) {
                val trainPosition = train[Position]
                engine.add(WhistleEffect(ctx, train, 768.0))
                engine.entities.filter { it.has(DreamBubbleEmotion) && it.has(Position) && it.has(Velocity) }
                    .forEach { dreamBubble ->
                        val dreamBubblePosition = dreamBubble[Position]
                        if (dreamBubblePosition.distanceSquared(trainPosition) <= 147456) {
                            if (dreamBubble.has(TimerTask)) {
                                dreamBubble.remove(TimerTask)
                            }
                            val velocity = dreamBubble[Velocity]
                            velocity.dx = (trainPosition.x - dreamBubblePosition.x) * 2
                            velocity.dy = (trainPosition.y - dreamBubblePosition.y) * 2
                        }
                    }
            }
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
        Events.addListener(MouseDownEvent, mouseDownListener)
        canvas.oncontextmenu = { false }
    }

    private fun removeListeners() {
        Events.removeListener(KeyDownEvent, keyDownListener)
        Events.removeListener(MouseDownEvent, mouseDownListener)
        canvas.oncontextmenu = null
    }

    override fun onRender() {
        pipeline.onRender()
    }
}