package beepbeep.accordionView.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.accordionView.DataModel
import beepbeep.accordionView.R
import beepbeep.accordion_library.AccordionAdapter
import beepbeep.accordion_library.AccordionView
import kotlinx.android.synthetic.main.row_content.view.*
import kotlinx.android.synthetic.main.row_title.view.*

class RandomAdapter(val dataArray: List<DataModel>) : AccordionAdapter {
    override fun onCreateViewHolderForTitle(parent: ViewGroup): AccordionView.ViewHolder {
        return TitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): AccordionView.ViewHolder {
        return ContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(viewHolder: AccordionView.ViewHolder, position: Int, arrowDirection: AccordionAdapter.ArrowDirection) {
        val dataModel = dataArray[position]
        (viewHolder as TitleViewHolder).itemView.apply {
            titleTextView.text = dataModel.title
            when (arrowDirection) {
                AccordionAdapter.ArrowDirection.UP -> titleArrowIcon.text = "▲"
                AccordionAdapter.ArrowDirection.DOWN -> titleArrowIcon.text = "▼"
                AccordionAdapter.ArrowDirection.NONE -> titleArrowIcon.text = ""
            }
        }
    }

    override fun onBindViewForContent(viewHolder: AccordionView.ViewHolder, position: Int) {
        val dataModel = dataArray[position]
        (viewHolder as ContentViewHolder).itemView.apply {
            contentTextView.text = dataModel.desc
        }
    }

    override fun getItemCount() = dataArray.size
}

class TitleViewHolder(itemView: View) : AccordionView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): TitleViewHolder {
            return TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_title, parent, false))
        }
    }
}

class ContentViewHolder(itemView: View) : AccordionView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): ContentViewHolder {
            return ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_content, parent, false))
        }
    }
}