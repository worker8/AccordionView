package beepbeep.accordion_library

import android.view.ViewGroup

interface AccordionAdapter {
    enum class ArrowDirection {
        UP, DOWN, NONE
    }

    fun onCreateViewHolderForTitle(parent: ViewGroup): AccordionView.ViewHolder
    fun onCreateViewHolderForContent(parent: ViewGroup): AccordionView.ViewHolder
    fun onBindViewForTitle(viewHolder: AccordionView.ViewHolder, position: Int, arrowDirection: ArrowDirection)
    fun onBindViewForContent(viewHolder: AccordionView.ViewHolder, position: Int)
    fun getItemCount(): Int
}
