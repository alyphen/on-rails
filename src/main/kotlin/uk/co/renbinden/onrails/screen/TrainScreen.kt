package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.onrails.animation.AnimationSystem
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.camera.CameraSystem
import uk.co.renbinden.onrails.levels.loadMap
import uk.co.renbinden.onrails.renderer.BaseRenderer
import uk.co.renbinden.onrails.renderer.ImageRenderer
import uk.co.renbinden.onrails.renderer.RenderPipeline
import uk.co.renbinden.onrails.renderer.SolidBackgroundRenderer
import uk.co.renbinden.onrails.train.TrainSystem
import uk.co.renbinden.onrails.velocity.VelocitySystem
import kotlin.browser.document

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class TrainScreen(val app: App, val assets: Assets) : Screen(engine {
    add(VelocitySystem())
    add(TrainSystem())
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

    init {
        engine.loadMap(assets, assets.maps.overworld)
    }

    override fun onRender() {
        pipeline.onRender()
    }
}