package com.example.stressmeter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stressmeter.R

class StressMeterViewModel : ViewModel() {
    private val _imageIds = listOf(
        R.drawable.psm_alarm_clock,
        R.drawable.fish_normal017,
        R.drawable.psm_alarm_clock2,
        R.drawable.psm_angry_face,
        R.drawable.psm_anxious,
        R.drawable.psm_baby_sleeping,
        R.drawable.psm_exam4,
        R.drawable.psm_gambling4,
        R.drawable.psm_bar,
        R.drawable.psm_barbed_wire2,
        R.drawable.psm_beach3,
        R.drawable.psm_bird3,
        R.drawable.psm_blue_drop,
        R.drawable.psm_cat,
        R.drawable.psm_clutter,
        R.drawable.psm_clutter3,
        R.drawable.psm_dog_sleeping,
        R.drawable.psm_headache,
        R.drawable.psm_headache2,
        R.drawable.psm_clutter,
        R.drawable.psm_hiking3,
        R.drawable.psm_kettle,
        R.drawable.psm_lake3,
        R.drawable.psm_lawn_chairs3,
        R.drawable.psm_lonely,
        R.drawable.psm_lonely2,
        R.drawable.psm_mountains11,
        R.drawable.psm_neutral_child,
        R.drawable.psm_neutral_person2,
        R.drawable.psm_peaceful_person,
        R.drawable.psm_puppy,
        R.drawable.psm_puppy3,
        R.drawable.psm_reading_in_bed2,
        R.drawable.psm_running3,
        R.drawable.psm_running4,
        R.drawable.psm_sticky_notes2,
        R.drawable.psm_stressed_cat,
        R.drawable.psm_stressed_person,
        R.drawable.psm_stressed_person12,
        R.drawable.psm_stressed_person3,
        R.drawable.psm_stressed_person4,
        R.drawable.psm_stressed_person6,
        R.drawable.psm_stressed_person7,
        R.drawable.psm_stressed_person8,
        R.drawable.psm_talking_on_phone2,
        R.drawable.psm_to_do_list,
        R.drawable.psm_to_do_list3,
        R.drawable.psm_wine3,
        R.drawable.psm_work4,
        R.drawable.psm_yoga4,
    )

    private var index = 0

    private var _activeImageIds = MutableLiveData<List<Int>>()
    private var _selectedImageId = MutableLiveData<Int>()

    val selectedImageMutable: LiveData<Int>
        get() = _selectedImageId

    val imageIdsMutable: LiveData<List<Int>>
        get() = _activeImageIds

    fun setSelectImage(id: Int) {
        _selectedImageId.value = id
    }

    fun next() {
        // get next 16 images
        val curr = _imageIds
        val size = _imageIds.size
        val nextIndex = (index + 16) % size
        val items: List<Int> = if (nextIndex > index) {
            curr.subList(index, nextIndex)
        } else {
            curr.subList(index, size) + curr.subList(0, nextIndex)
        }

        _activeImageIds.value = items
        index = nextIndex
    }
}