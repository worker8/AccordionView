package beepbeep.myapplication.new_constraint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.myapplication.DataModel
import beepbeep.myapplication.R
import kotlinx.android.synthetic.main.row_content.view.*
import kotlinx.android.synthetic.main.row_title.view.*

class ExampleAdapter(val dataArray: List<DataModel>) : AccordianAdapter {
    override fun onCreateViewHolderForTitle(parent: ViewGroup): ViewHolder {
        return TitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): ViewHolder {
        return ContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(viewHolder: ViewHolder, position: Int, arrowDirection: AccordianAdapter.ArrowDirection) {
        val dataModel = dataArray[position]
        (viewHolder as TitleViewHolder).itemView.apply {
            titleTextView.text = dataModel.title
        }
    }

    override fun onBindViewForContent(viewHolder: ViewHolder, position: Int) {
        val dataModel = dataArray[position]
        (viewHolder as ContentViewHolder).itemView.apply {
            contentTextView.text = dataModel.desc
        }
    }

    override fun getItemCount() = dataArray.size

}

class TitleViewHolder(itemView: View) : ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): TitleViewHolder {
            return TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_title, parent, false))
        }
    }
}

class ContentViewHolder(itemView: View) : ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): ContentViewHolder {
            return ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_content, parent, false))
        }
    }
}