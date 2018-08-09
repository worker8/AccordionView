package beepbeep.myapplication

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet

class MultiRecipeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    val CONTENT_ID = 999 + 1000 // this is arbitrary

    var selectedPosition = 1

    val adapter = CustomMultiRecipeViewAdapter()

    fun render() {
        for (index in 0..selectedPosition) {
            val rowViewHolder = adapter.onCreateViewHolderForTitle(this)
            val row = rowViewHolder.itemView.apply {
                id = index + ID_OFFSET
                adapter.onBindViewForTitle(rowViewHolder, index)
            }
            row.setOnClickListener {
                selectedPosition = index
                render()
            }
            addView(row)

            val set = ConstraintSet()
            set.clone(this)
            if (index == 0) {
                set.connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                set.connect(row.id, ConstraintSet.TOP, row.id - 1, ConstraintSet.BOTTOM)
            }

            set.applyTo(this)

        }
        for (reversedIndex in adapter.getItemCount() - 1 downTo selectedPosition + 1) {
            val rowViewHolder = adapter.onCreateViewHolderForTitle(this)
            val row = rowViewHolder.itemView.apply {
                id = reversedIndex + ID_OFFSET
                adapter.onBindViewForTitle(rowViewHolder, reversedIndex)
            }
            row.setOnClickListener {
                selectedPosition = reversedIndex
                render()
            }
            this.addView(row)

            val set = ConstraintSet()
            set.clone(this)
            if (reversedIndex == adapter.getItemCount() - 1) {
                set.connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                set.connect(row.id, ConstraintSet.BOTTOM, row.id + 1, ConstraintSet.TOP)
            }

            set.applyTo(this)
        }

        val positionAboveContent = selectedPosition
        val positionBelowContent = selectedPosition + 1

        val rowIdAboveContent = positionAboveContent + ID_OFFSET
        val rowIdBelowContent = if (selectedPosition == adapter.getItemCount() - 1) {
            ConstraintSet.PARENT_ID
        } else {
            positionBelowContent + ID_OFFSET
        }

        val contentViewHolder = adapter.onCreateViewHolderForContent(this)
        val content = contentViewHolder.itemView.apply {
            id = CONTENT_ID
            adapter.onBindViewForContent(contentViewHolder, selectedPosition)
        }

        addView(content)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
        set.connect(content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == adapter.getItemCount() - 1) {
            ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
        } else {
            ConstraintSet.TOP
        })

        set.applyTo(this)
    }

    init {
        render()
    }
}