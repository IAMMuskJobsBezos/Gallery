package org.fossify.gallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.fossify.commons.activities.BaseSimpleActivity
import org.fossify.commons.extensions.applyColorFilter
import org.fossify.commons.extensions.beGone
import org.fossify.commons.extensions.beVisible
import org.fossify.commons.extensions.beVisibleIf
import org.fossify.commons.extensions.getProperPrimaryColor
import org.fossify.gallery.R
import org.fossify.gallery.databinding.ItemPhotosGridBinding
import org.fossify.gallery.extensions.loadImage
import org.fossify.gallery.helpers.ROUNDED_CORNERS_NONE
import org.fossify.gallery.models.Medium

/**
 * Purpose-built grid adapter for the elderly-friendly "Photos" redesign (see
 * docs/elderly-spec/photos-grid.md). Selection is driven entirely by [isSelectionModeActive] -
 * there is no long-press entry point, matching decision #6 in docs/elderly-spec/decisions.md.
 */
class PhotosGridAdapter(
    private val activity: BaseSimpleActivity,
    private var media: ArrayList<Medium>,
    private val onItemClick: (Medium) -> Unit,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<PhotosGridAdapter.ViewHolder>() {

    var isSelectionModeActive = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (!value) {
                selectedPaths.clear()
            }
            notifyDataSetChanged()
        }

    private val selectedPaths = LinkedHashSet<String>()

    fun getSelectedCount() = selectedPaths.size

    fun getSelectedMedia() = media.filter { selectedPaths.contains(it.path) } as ArrayList<Medium>

    fun getMediumAt(position: Int) = media.getOrNull(position)

    fun clearSelection() {
        selectedPaths.clear()
        notifyDataSetChanged()
    }

    fun updateMedia(newMedia: ArrayList<Medium>) {
        media = newMedia
        selectedPaths.retainAll(media.map { it.path }.toSet())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(media[position])
    }

    override fun getItemCount() = media.size

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        if (!activity.isDestroyed) {
            Glide.with(activity).clear(holder.binding.photosGridThumbnail)
        }
    }

    inner class ViewHolder(val binding: ItemPhotosGridBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medium: Medium) {
            binding.apply {
                photosGridThumbnail.setBackgroundResource(R.drawable.placeholder_square)
                activity.loadImage(
                    type = medium.type,
                    path = medium.path,
                    target = photosGridThumbnail,
                    horizontalScroll = false,
                    animateGifs = true,
                    cropThumbnails = true,
                    roundCorners = ROUNDED_CORNERS_NONE,
                    signature = medium.getKey()
                )

                photosGridPlayBadge.beVisibleIf(medium.isVideo())

                val isSelected = selectedPaths.contains(medium.path)
                photosGridSelectionCircle.beVisibleIf(isSelectionModeActive)
                if (isSelectionModeActive) {
                    if (isSelected) {
                        photosGridSelectionCircle.setBackgroundResource(R.drawable.circle_background)
                        photosGridSelectionCircle.background?.applyColorFilter(activity.getProperPrimaryColor())
                        photosGridSelectionCircle.setImageResource(org.fossify.commons.R.drawable.ic_check_vector)
                        photosGridSelectionCircle.beVisible()
                    } else {
                        photosGridSelectionCircle.setBackgroundResource(R.drawable.circle_outline_background)
                        photosGridSelectionCircle.setImageDrawable(null)
                    }
                }

                root.setOnClickListener {
                    val currentMedium = media.getOrNull(bindingAdapterPosition) ?: return@setOnClickListener
                    if (isSelectionModeActive) {
                        if (!selectedPaths.remove(currentMedium.path)) {
                            selectedPaths.add(currentMedium.path)
                        }
                        notifyItemChanged(bindingAdapterPosition)
                        onSelectionChanged()
                    } else {
                        onItemClick(currentMedium)
                    }
                }
            }
        }
    }
}
