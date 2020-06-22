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
import uk.co.renbinden.onrails.conversation.ConversationTimeline
import uk.co.renbinden.onrails.conversation.ShowTextWithOptionsConversationEvent
import uk.co.renbinden.onrails.depth.Depth
import uk.co.renbinden.onrails.hover.HoverSystem
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.*
import kotlin.browser.document
import kotlin.browser.window

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class ConversationScreen(val app: App, val assets: Assets, val conversationTimeline: ConversationTimeline, val next: () -> Unit) : Screen(engine {
    add(entity {
        add(Position(0.0, 480.0))
        add(Image(assets.images.textBoxBackground))
        add(Depth(-1))
    })
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
        BackgroundImageRenderer(canvas, ctx, assets.images.backgroundStation1),
        ImageRenderer(canvas, ctx, engine),
        TextRenderer(canvas, ctx, engine)
    )

    private val mouseDownListener = Listener<MouseDownEvent>({ event ->
        if (conversationTimeline.currentEvent !is ShowTextWithOptionsConversationEvent) {
            conversationTimeline.progress(engine)
        }
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
        assets.sounds.funktionJunktionStation.currentTime = 0.0
        assets.sounds.funktionJunktionStation.play()
        engine.add(HoverSystem(canvas))
        conversationTimeline.execute { removeListeners(); assets.sounds.funktionJunktionStation.pause(); next() }
        conversationTimeline.progress(engine)

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