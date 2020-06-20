package uk.co.renbinden.onrails.screen

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.ilse.app.screen.Screen
import uk.co.renbinden.ilse.ecs.engine
import uk.co.renbinden.ilse.ecs.entity.entity
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.image.Image
import uk.co.renbinden.onrails.path.Path
import uk.co.renbinden.onrails.path.PathSystem
import uk.co.renbinden.onrails.position.Position
import uk.co.renbinden.onrails.renderer.BaseRenderer
import uk.co.renbinden.onrails.renderer.ImageRenderer
import uk.co.renbinden.onrails.renderer.RenderPipeline
import uk.co.renbinden.onrails.renderer.SolidBackgroundRenderer
import kotlin.browser.document
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

    add(entity {
        add(Position(272.0, 236.0))
        add(Image(assets.images.buttonStart))
    })
}) {

    val canvas = document.getElementById("canvas") as HTMLCanvasElement
    val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

    val pipeline = RenderPipeline(
        BaseRenderer(canvas, ctx),
        SolidBackgroundRenderer(canvas, ctx, "rgb(0, 0, 0)"),
        ImageRenderer(canvas, ctx, engine)
    )

    override fun onRender() {
        pipeline.onRender()
    }

}