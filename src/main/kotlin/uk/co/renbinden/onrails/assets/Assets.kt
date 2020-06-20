package uk.co.renbinden.onrails.assets

import uk.co.renbinden.ilse.asset.ImageAsset

class Assets {

    inner class Images {
        val title = ImageAsset("static/images/title.png")
        val buttonStart = ImageAsset("static/images/button_start.png")
    }

    val images = Images()

}