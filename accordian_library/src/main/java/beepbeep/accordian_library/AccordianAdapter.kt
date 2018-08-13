package beepbeep.accordian_library

import android.view.ViewGroup

interface AccordianAdapter {
    enum class ArrowDirection {
        UP, DOWN, NONE
    }

    fun onCreateViewHolderForTitle(parent: ViewGroup): AccordianView.ViewHolder
    fun onCreateViewHolderForContent(parent: ViewGroup): AccordianView.ViewHolder
    fun onBindViewForTitle(viewHolder: AccordianView.ViewHolder, position: Int, arrowDirection: ArrowDirection)
    fun onBindViewForContent(viewHolder: AccordianView.ViewHolder, position: Int)
    fun getItemCount(): Int
}
