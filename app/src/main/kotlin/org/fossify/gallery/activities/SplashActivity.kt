package org.fossify.gallery.activities

import android.content.Intent
import org.fossify.commons.activities.BaseSplashActivity
import org.fossify.commons.helpers.ensureBackgroundThread
import org.fossify.gallery.extensions.config
import org.fossify.gallery.extensions.favoritesDB
import org.fossify.gallery.extensions.getFavoriteFromPath
import org.fossify.gallery.extensions.mediaDB
import org.fossify.gallery.helpers.DIRECTORY
import org.fossify.gallery.models.Favorite

class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        // check if previously selected favorite items have been properly migrated into the new Favorites table
        if (config.wereFavoritesMigrated) {
            launchActivity()
        } else {
            if (config.appRunCount == 0) {
                config.wereFavoritesMigrated = true
                launchActivity()
            } else {
                config.wereFavoritesMigrated = true
                ensureBackgroundThread {
                    val favorites = ArrayList<Favorite>()
                    val favoritePaths = mediaDB.getFavorites().map { it.path }.toMutableList() as ArrayList<String>
                    favoritePaths.forEach {
                        favorites.add(getFavoriteFromPath(it))
                    }
                    favoritesDB.insertAll(favorites)

                    runOnUiThread {
                        launchActivity()
                    }
                }
            }
        }
    }

    private fun launchActivity() {
        // this app is a flat "Photos" grid only (elderly-friendly redesign) — folder
        // browsing (MainActivity) is unreachable, launch straight into the SHOW_ALL grid
        config.showAll = true
        Intent(this, MediaActivity::class.java).apply {
            putExtra(DIRECTORY, "")
            startActivity(this)
        }
        finish()
    }
}
