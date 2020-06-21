package uk.co.renbinden.onrails

import uk.co.renbinden.ilse.app.App
import uk.co.renbinden.onrails.assets.Assets
import uk.co.renbinden.onrails.screen.TitleScreen

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
fun main() {
    val assets = Assets()
    val app = App()
    app.screen = TitleScreen(app, assets)
}