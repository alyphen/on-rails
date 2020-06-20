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
import uk.co.renbinden.onrails.avatar.Avatars
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.conversation.timeline
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.path.Path
import uk.co.renbinden.onrails.path.PathSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.BaseRenderer
import uk.co.renbinden.onrails.renderer.ImageRenderer
import uk.co.renbinden.onrails.renderer.RenderPipeline
import uk.co.renbinden.onrails.renderer.SolidBackgroundRenderer
import kotlin.browser.document
import kotlin.browser.window
import kotlin.math.sin

class TitleScreen(val app: App, val assets: Assets) : Screen(engine {
    add(PathSystem())

    add(entity {
        add(Position(64.0, 96.0))
        add(Image(assets.images.title))
        add(Path(
            { 64.0 },
            { t -> (sin(t) * 16.0) + 64.0 }
        ))
    })
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
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
        engine.add(entity {
            add(Position(272.0, 236.0))
            add(Image(assets.images.buttonStart))
            add(Bounds(256.0, 128.0))
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