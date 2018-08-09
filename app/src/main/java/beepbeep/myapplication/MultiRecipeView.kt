package beepbeep.myapplication

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class MultiRecipeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    val CONTENT_ID = 999 + 1000 // this is arbitrary

    var selectedPosition = 1

    var adapter: MultiRecipeViewAdapter? = null
        set(value) {
            field = value
            render()
        }

    private fun render() {
        renderTopSection()
        renderBottomSection()
        renderContentSection()
    }

    private fun renderTopSection() {
        if (adapter == null) {
            return
        }
        val _adapter = adapter!!
        for (index in 0..selectedPosition) {
            val rowViewHolder = _adapter.onCreateViewHolderForTitle(this)
            val row = rowViewHolder.itemView.apply {
                id = index + ID_OFFSET
                val arrowDirection = if (selectedPosition == index) {
                    MultiRecipeViewAdapter.ArrowDirection.NONE
                } else {
                    MultiRecipeViewAdapter.ArrowDirection.DOWN
                }
                _adapter.onBindViewForTitle(rowViewHolder, index, arrowDirection)
            }
            row.setOnClickListener2 {
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
    }

    private fun renderBottomSection() {
        if (adapter == null) {
            return
        }
        val _adapter = adapter!!
        for (reversedIndex in _adapter.getItemCount() - 1 downTo selectedPosition + 1) {
            val rowViewHolder = _adapter.onCreateViewHolderForTitle(this)
            val row = rowViewHolder.itemView.apply {
                id = reversedIndex + ID_OFFSET
                val arrowDirection = if (selectedPosition == reversedIndex) {
                    MultiRecipeViewAdapter.ArrowDirection.NONE
                } else {
                    MultiRecipeViewAdapter.ArrowDirection.UP
                }
                _adapter.onBindViewForTitle(rowViewHolder, reversedIndex, arrowDirection)
            }
            row.setOnClickListener2 {
                selectedPosition = reversedIndex
                render()
            }
            this.addView(row)

            val set = ConstraintSet()
            set.clone(this)
            if (reversedIndex == _adapter.getItemCount() - 1) {
                set.connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                set.connect(row.id, ConstraintSet.BOTTOM, row.id + 1, ConstraintSet.TOP)
            }

            set.applyTo(this)
        }
    }

    private fun renderContentSection() {
        if (adapter == null) {
            return
        }
        val _adapter = adapter!!
        val positionAboveContent = selectedPosition
        val positionBelowContent = selectedPosition + 1

        val rowIdAboveContent = positionAboveContent + ID_OFFSET
        val rowIdBelowContent = if (selectedPosition == _adapter.getItemCount() - 1) {
            ConstraintSet.PARENT_ID
        } else {
            positionBelowContent + ID_OFFSET
        }

        val contentViewHolder = _adapter.onCreateViewHolderForContent(this)
        val content = contentViewHolder.itemView.apply {
            id = CONTENT_ID
            _adapter.onBindViewForContent(contentViewHolder, selectedPosition)
        }

        addView(content)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
        set.connect(content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == _adapter.getItemCount() - 1) {
            ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
        } else {
            ConstraintSet.TOP
        })

        set.applyTo(this)
    }

    private fun View.setOnClickListener2(callback: (View) -> Unit) {
        setOnTouchListener { view, event ->
            if (event.actionMasked == MotionEvent.ACTION_UP) {
                callback.invoke(view)
                callOnClick()
            }
            true
        }
    }

}