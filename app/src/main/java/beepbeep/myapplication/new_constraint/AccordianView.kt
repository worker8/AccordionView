package beepbeep.myapplication.new_constraint

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class AccordianView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    var selectedPosition = 0
        set(value) {
            adapter?.let {
                if (value < it.getItemCount() && value >= 0) {
                    field = value
                    render()
                }
            }
        }

    var adapter: AccordianAdapter? = null
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
        adapter?.let { _adapter ->
            for (index in 0..selectedPosition) {
                val rowViewHolder = _adapter.onCreateViewHolderForTitle(this)
                val row = rowViewHolder.itemView.apply {
                    id = index + ID_OFFSET
                    tag = id.toString()
                }
                row.setOnClickListener2 {
                    selectedPosition = index
                    render()
                }
                addView(row)

                val arrowDirection = if (selectedPosition == index) {
                    AccordianAdapter.ArrowDirection.NONE
                } else {
                    AccordianAdapter.ArrowDirection.DOWN
                }
                _adapter.onBindViewForTitle(rowViewHolder, index, arrowDirection)

                ConstraintSet().apply {
                    clone(this@AccordianView) // it is important to clone from the parent, not from the empty ConstraintSet(), becareful!
                    if (index == 0) {
                        connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    } else {
                        connect(row.id, ConstraintSet.TOP, row.id - 1, ConstraintSet.BOTTOM)
                    }
                }.applyTo(this)
            }
        }
    }

    private fun renderBottomSection() {
        adapter?.let { _adapter ->
            for (reversedIndex in _adapter.getItemCount() - 1 downTo selectedPosition + 1) {
                val rowViewHolder = _adapter.onCreateViewHolderForTitle(this)
                val row = rowViewHolder.itemView.apply {
                    id = reversedIndex + ID_OFFSET
                    tag = id.toString()
                }
                row.setOnClickListener2 {
                    selectedPosition = reversedIndex
                    render()
                }
                this.addView(row)
                val arrowDirection = if (selectedPosition == reversedIndex) {
                    AccordianAdapter.ArrowDirection.NONE
                } else {
                    AccordianAdapter.ArrowDirection.UP
                }
                _adapter.onBindViewForTitle(rowViewHolder, reversedIndex, arrowDirection)
                ConstraintSet().apply {
                    clone(this@AccordianView) // it is important to clone from the parent, not from the empty ConstraintSet(), becareful!
                    if (reversedIndex == _adapter.getItemCount() - 1) {
                        connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    } else {
                        connect(row.id, ConstraintSet.BOTTOM, row.id + 1, ConstraintSet.TOP)
                    }
                }.applyTo(this)
            }
        }
    }

    private fun renderContentSection() {
        adapter?.let { _adapter ->
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
                id = View.generateViewId()
                tag = id.toString()
            }

            addView(content)
            _adapter.onBindViewForContent(contentViewHolder, selectedPosition)

            ConstraintSet().apply {
                clone(this@AccordianView) // it is important to clone from the parent, not from the empty ConstraintSet(), becareful!
                connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
                connect(
                        content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == _adapter.getItemCount() - 1) {
                    ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
                } else {
                    ConstraintSet.TOP
                }
                )
            }.applyTo(this)
        }
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
