package uk.co.renbinden.onrails.renderer

class RenderPipeline(private vararg val renderers: Renderer) {

    fun onRender() {
        renderers.forEach(Renderer::onRender)
    }

}