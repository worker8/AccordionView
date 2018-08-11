package beepbeep.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.row.view.*


class CustomMultiRecipeViewAdapter(val dataArray: List<DataModel>, val callback: (View) -> Unit) : MultiRecipeViewAdapter {

    override fun onCreateViewHolderForTitle(parent: ViewGroup): ViewHolder {
        return CustomTitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): ViewHolder {
        return CustomContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(viewHolder: ViewHolder, position: Int, arrowDirection: MultiRecipeViewAdapter.ArrowDirection) {
        val customTitleViewHolder = viewHolder as CustomTitleViewHolder
        val dataModel = dataArray[position]
        customTitleViewHolder.itemView.apply {
            titleTextView.text = dataModel.title

            when (arrowDirection) {
                MultiRecipeViewAdapter.ArrowDirection.UP -> titleArrowIcon.text = "▲"
                MultiRecipeViewAdapter.ArrowDirection.DOWN -> titleArrowIcon.text = "▼"
                MultiRecipeViewAdapter.ArrowDirection.NONE -> titleArrowIcon.text = ""
            }
        }
    }

    override fun onBindViewForContent(viewHolder: ViewHolder, position: Int) {
        val customContentViewHolder = viewHolder as CustomContentViewHolder
        val dataModel = dataArray[position]
        customContentViewHolder.itemView.apply {
            contentTextView.text = dataModel.desc
            contentFragment.id = contentFragment.id + 1
            callback.invoke(this)
        }
    }

    override fun getItemCount() = dataArray.size
}

class CustomTitleViewHolder(itemView: View) : ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): CustomTitleViewHolder {
            return CustomTitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
        }
    }
}

class CustomContentViewHolder(itemView: View) : ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): CustomContentViewHolder {
            return CustomContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content, parent, false))
        }
    }
}