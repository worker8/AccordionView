package beepbeep.myapplication

import android.view.View
import android.view.ViewGroup

interface MultiRecipeViewAdapter {

    fun onCreateViewHolderForTitle(parent: ViewGroup): ViewHolder
    fun onCreateViewHolderForContent(parent: ViewGroup): ViewHolder
    fun onBindViewForTitle(viewHolder: ViewHolder, position: Int)
    fun onBindViewForContent(viewHolder: ViewHolder, position: Int)
    fun getItemCount(): Int
}

open class ViewHolder(val itemView: View)