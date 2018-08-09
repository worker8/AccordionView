package beepbeep.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.row.view.*


class CustomMultiRecipeViewAdapter : MultiRecipeViewAdapter<CustomTitleViewHolder, CustomContentViewHolder> {
    val dataArray: List<DataModel> = listOf(
            DataModel("Dinosaur", "this is a T-Rex"),
            DataModel("Fruit", "papaya avocado mango"), // <-- selectedPosition
            DataModel("Gadget", "mouse keyboard"),      // <-- selectedPosition + 1
            DataModel("Songs", "birthday music")        // <-- dataArray.size   - 1
    )

    override fun onCreateViewHolderForTitle(parent: ViewGroup): CustomTitleViewHolder {
        return CustomTitleViewHolder.create(parent)
    }

    override fun onCreateViewHolderForContent(parent: ViewGroup): CustomContentViewHolder {
        return CustomContentViewHolder.create(parent)
    }

    override fun onBindViewForTitle(view: CustomTitleViewHolder, position: Int) {
        val dataModel = dataArray[position]
        view.itemView.apply {
            titleTextView.text = dataModel.title
        }
    }

    override fun onBindViewForContent(view: CustomContentViewHolder, position: Int) {
        val dataModel = dataArray[position]
        view.itemView.apply {
            contentTextView.text = dataModel.desc
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