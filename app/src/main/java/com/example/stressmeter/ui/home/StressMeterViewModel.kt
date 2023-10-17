package com.example.stressmeter.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stressmeter.R
import com.example.stressmeter.managers.CsvFileManager
import com.example.stressmeter.managers.Store
import java.util.Date
import kotlin.time.TimeSource
import kotlin.time.measureTime

private val imageToStressLevelMap = mapOf(
    R.drawable.psm_alarm_clock to 5,
    R.drawable.fish_normal017 to 3,
    R.drawable.psm_alarm_clock2 to 6,
    R.drawable.psm_angry_face to 10,
    R.drawable.psm_anxious to 7,
    R.drawable.psm_baby_sleeping to 0,
    R.drawable.psm_exam4 to 9,
    R.drawable.psm_gambling4 to 8,
    R.drawable.psm_bar to 4,
    R.drawable.psm_barbed_wire2 to 9,
    R.drawable.psm_beach3 to 0,
    R.drawable.psm_bird3 to 1,
    R.drawable.psm_blue_drop to 2,
    R.drawable.psm_cat to 3,
    R.drawable.psm_clutter to 8,
    R.drawable.psm_clutter3 to 7,
    R.drawable.psm_dog_sleeping to 0,
    R.drawable.psm_headache to 7,
    R.drawable.psm_headache2 to 8,
    R.drawable.psm_hiking3 to 4,
    R.drawable.psm_kettle to 3,
    R.drawable.psm_lake3 to 1,
    R.drawable.psm_lawn_chairs3 to 3,
    R.drawable.psm_lonely to 8,
    R.drawable.psm_lonely2 to 7,
    R.drawable.psm_mountains11 to 4,
    R.drawable.psm_neutral_child to 1,
    R.drawable.psm_neutral_person2 to 1,
    R.drawable.psm_peaceful_person to 1,
    R.drawable.psm_puppy to 0,
    R.drawable.psm_puppy3 to 1,
    R.drawable.psm_reading_in_bed2 to 2,
    R.drawable.psm_running3 to 5,
    R.drawable.psm_running4 to 6,
    R.drawable.psm_sticky_notes2 to 3,
    R.drawable.psm_stressed_cat to 5,
    R.drawable.psm_stressed_person to 10,
    R.drawable.psm_stressed_person12 to 9,
    R.drawable.psm_stressed_person3 to 7,
    R.drawable.psm_stressed_person4 to 8,
    R.drawable.psm_stressed_person6 to 7,
    R.drawable.psm_stressed_person7 to 8,
    R.drawable.psm_stressed_person8 to 9,
    R.drawable.psm_talking_on_phone2 to 5,
    R.drawable.psm_to_do_list to 3,
    R.drawable.psm_to_do_list3 to 4,
    R.drawable.psm_wine3 to 6,
    R.drawable.psm_work4 to 6,
    R.drawable.psm_yoga4 to 2
)

class StressMeterViewModel : ViewModel() {
    private var _index = 0
    private var _activeImageIds = MutableLiveData<List<Int>>()
    private var _selectedImageId = MutableLiveData<Int>()
    private val _imageKeys = imageToStressLevelMap.keys

    val selectedImageMutable: LiveData<Int>
        get() = _selectedImageId

    val imageIdsMutable: LiveData<List<Int>>
        get() = _activeImageIds

    fun setSelectImage(id: Int) {
        _selectedImageId.value = id
    }

    fun saveSelectedImage() {
        if (selectedImageMutable.value == null) {
            throw UnsupportedOperationException("Attempted to save null as image")
        }
        // prepare data to be saved in csv
        val csvCol = emptyList<String>().toMutableList()
        val timestamp = Date().time.toString()
        val line =
            "${selectedImageMutable.value.toString()},${getStressScore(selectedImageMutable.value!!)},$timestamp"
        csvCol.add(line)
        CsvFileManager.createOrUpdateCSV(csvCol)
    }

    fun next(iterateIndex: Boolean = true) {
        // get next 16 images
        val curr = _imageKeys.toList()
        val size = curr.size

        if (iterateIndex) _index = (_index + 16) % size

        _activeImageIds.value = getPage(curr, _index, size)
    }

    private fun getPage(curr: List<Int>, index: Int, size: Int): List<Int> {
        val nextIndex = (index + 16) % size
        val items: List<Int> = if (nextIndex > index) {
            curr.subList(index, nextIndex)
        } else {
            curr.subList(index, size) + curr.subList(0, nextIndex)
        }
        return items
    }

    companion object {
        fun getStressScore(id: Int): Int {
            if (imageToStressLevelMap[id] == null) {
                throw IllegalAccessError("Image id not found")
            }
            return imageToStressLevelMap[id]!!
        }
    }
}