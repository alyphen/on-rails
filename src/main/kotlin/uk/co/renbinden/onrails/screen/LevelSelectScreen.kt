package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.event.Events
import uk.co.renbinden.ilse.event.Listener
import uk.co.renbinden.ilse.input.event.MouseDownEvent
import uk.co.renbinden.onrails.action.Action
import uk.co.renbinden.onrails.archetype.LevelSelectButton
import uk.co.renbinden.onrails.archetype.LevelSelectButtonText
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.bounds.Bounds
import uk.co.renbinden.onrails.hover.HoverSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.*
import kotlin.browser.document
import kotlin.browser.window

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class LevelSelectScreen(val app: App, val assets: Assets) : Screen(engine {}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "#000000"),
        BackgroundImageRenderer(canvas, ctx, assets.images.backgroundStart),
        ImageRenderer(canvas, ctx, engine),
        TextRenderer(canvas, ctx, engine)
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

        engine.add(LevelSelectButtonText("1", 154.0, 320.0))
        engine.add(LevelSelectButton(assets, 128.0, 300.0) {
            removeListeners()
            app.screen = ConversationScreen(app, assets)
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