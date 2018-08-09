package beepbeep.myapplication

import android.view.View
import android.view.ViewGroup

interface MultiRecipeViewAdapter<TitleViewHolder : ViewHolder, ContentViewHolder : ViewHolder> {

    fun onCreateViewHolderForTitle(parent: ViewGroup): TitleViewHolder
    fun onCreateViewHolderForContent(parent: ViewGroup): ContentViewHolder
    fun onBindViewForTitle(view: TitleViewHolder, position: Int)
    fun onBindViewForContent(view: ContentViewHolder, position: Int)
    fun getItemCount(): Int
}

open class ViewHolder(val itemView: View)