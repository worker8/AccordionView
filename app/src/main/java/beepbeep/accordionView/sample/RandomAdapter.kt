package beepbeep.accordionView.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.accordionView.DataModel
import beepbeep.accordionView.R
import beepbeep.accordian_library.AccordianAdapter
import beepbeep.accordian_library.AccordionView
import kotlinx.android.synthetic.main.row_content.view.*
import kotlinx.android.synthetic.main.row_title.view.*

class RandomAdapter(val dataArray: List<DataModel>) : AccordianAdapter {
    override fun onCreateViewHolderForTitle(parent: ViewGroup): AccordionView.ViewHolder {
        return TitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): AccordionView.ViewHolder {
        return ContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(viewHolder: AccordionView.ViewHolder, position: Int, arrowDirection: AccordianAdapter.ArrowDirection) {
        val dataModel = dataArray[position]
        (viewHolder as TitleViewHolder).itemView.apply {
            titleTextView.text = dataModel.title
            when (arrowDirection) {
                AccordianAdapter.ArrowDirection.UP -> titleArrowIcon.text = "▲"
                AccordianAdapter.ArrowDirection.DOWN -> titleArrowIcon.text = "▼"
                AccordianAdapter.ArrowDirection.NONE -> titleArrowIcon.text = ""
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