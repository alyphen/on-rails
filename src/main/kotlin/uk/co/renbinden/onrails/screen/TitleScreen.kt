package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.onrails.action.Action
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.hover.HoverImage
import uk.co.renbinden.onrails.hover.HoverSystem
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.path.Path
import uk.co.renbinden.onrails.path.PathSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.*
import kotlin.browser.document
import kotlin.browser.window
import kotlin.math.sin

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class TitleScreen(val app: App, val assets: Assets) : Screen(engine {
    add(PathSystem())

    add(entity {
        add(Position(237.0, 64.0))
        add(Bounds(326.0, 256.0))
        add(Image(assets.images.title))
        add(HoverImage(assets.images.title, assets.images.titleHover))
        add(Depth(0))
        add(Path(
            { 237.0 },
            { t -> (sin(t) * 16.0) + 32.0 }
        ))
    })
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
        BackgroundImageRenderer(canvas, ctx, assets.images.backgroundStart),
        ImageRenderer(canvas, ctx, engine)
    )

    private val mouseDownListener = Listener<MouseDownEvent>({ event ->
        val mouseX = event.pageX - (canvas.getBoundingClientRect().left + window.scrollX)
        val mouseY = event.pageY - (canvas.getBoundingClientRect().top + window.scrollY)
        engine.entities
            .filter { entity ->
                if (!entity.has(Position) || !entity.has(Bounds) || !entity.has(Action)) return@filter false
                val position = entity[Position]
                val bounds = entity[Bounds]
                return@filter position.x <= mouseX && position.y <= mouseY
                        && position.x + bounds.width >= mouseX && position.y + bounds.height >= mouseY
            }
            .forEach { entity -> entity[Action].onAction() }
    })

    init {
        engine.add(HoverSystem(canvas))

        engine.add(entity {
            add(Position(118.0, 325.0))
            add(Image(assets.images.buttonStart))
            add(HoverImage(assets.images.buttonStart, assets.images.buttonStartHover))
            add(Depth(0))
            add(Bounds(565.0, 243.0))
            add(Action {
                removeListeners()
                app.screen = ConversationScreen(app, assets)
            })
        })

        addListeners()
    }

    fun addListeners() {
        Events.addListener(MouseDownEvent, mouseDownListener)
    }

    fun removeListeners() {
        Events.removeListener(MouseDownEvent, mouseDownListener)
    }

    override fun onRender() {
        pipeline.onRender()
    }

}