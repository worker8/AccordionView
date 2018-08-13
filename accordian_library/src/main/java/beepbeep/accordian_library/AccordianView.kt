package beepbeep.accordian_library

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation


class AccordianView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    private val titleViewHolderArray = mutableListOf<ViewHolder>()

    private var contentViewHolder: ViewHolder? = null

    var selectedPosition = 0
        set(value) {
            adapter?.let { _adapter ->
                if (value < _adapter.getItemCount() && value >= 0) {
                    field = value
                    applyConstraint()
                }
            }
        }

    var adapter: AccordianAdapter? = null
        set(value) {
            field = value
            render()
        }

    private fun render() {
        createTitleViews()
        createContent()
        addAllViews()
        applyConstraint()
    }

    private fun createTitleViews() {
        adapter?.let { _adapter ->
            // loop and make bunch of Rows: store in array
            for (index in 0 until _adapter.getItemCount()) {
                var viewHolder = titleViewHolderArray.getOrNull(index)
                if (viewHolder == null) {
                    viewHolder = _adapter.onCreateViewHolderForTitle(this)
                    val row = viewHolder.itemView.apply {
                        id = View.generateViewId()
                        tag = id.toString()
                    }
                    row.setOnClickListener2 {
                        contentViewHolder?.let { _contentViewHolder ->
                            _contentViewHolder.itemView.visibility = View.INVISIBLE
                            selectedPosition = index
                            titleViewHolderArray.forEachIndexed { innerIndex, innerViewHolder ->
                                _adapter.onBindViewForTitle(innerViewHolder, innerIndex, arrowDirection(innerIndex))
                            }

                            _adapter.onBindViewForContent(_contentViewHolder, index)
                            _contentViewHolder.itemView.visibility = View.VISIBLE
                        }
                    }
                    _adapter.onBindViewForTitle(viewHolder, index, arrowDirection(index))
                    titleViewHolderArray.add(viewHolder)
                }
            }
        }
    }

    private fun createContent() {
        adapter?.let { _adapter ->
            if (contentViewHolder == null) {
                contentViewHolder = _adapter.onCreateViewHolderForContent(this)
                contentViewHolder?.itemView.apply {
                    id = View.generateViewId()
                    tag = id.toString()

                }
                contentViewHolder?.let { _adapter.onBindViewForContent(it, selectedPosition) }
            }
        }
    }

    private fun addAllViews() {
        adapter?.let { _adapter ->
            titleViewHolderArray.forEach {
                addView(it.itemView)
            }
            contentViewHolder?.apply { addView(itemView) }
        }
    }


    private fun arrowDirection(index: Int): AccordianAdapter.ArrowDirection {
        return if (selectedPosition == index) {
            AccordianAdapter.ArrowDirection.NONE
        } else if (index < selectedPosition) {
            AccordianAdapter.ArrowDirection.DOWN
        } else {
            AccordianAdapter.ArrowDirection.UP
        }
    }


    private fun applyConstraint() {
        val set = ConstraintSet()
        set.clone(this)
        for (index in 0..selectedPosition) {
            val row = titleViewHolderArray[index].itemView
            set.clear(row.id, ConstraintSet.TOP)
            set.clear(row.id, ConstraintSet.BOTTOM)
            if (index == 0) {
                set.connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                val previousRow = titleViewHolderArray[index - 1].itemView
                set.connect(row.id, ConstraintSet.TOP, previousRow.id, ConstraintSet.BOTTOM)
            }
            set.connect(row.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            set.connect(row.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

        }

        for (reversedIndex in titleViewHolderArray.size - 1 downTo selectedPosition + 1) {
            val row = titleViewHolderArray[reversedIndex].itemView
            set.clear(row.id, ConstraintSet.TOP)
            set.clear(row.id, ConstraintSet.BOTTOM)
            if (reversedIndex == titleViewHolderArray.size - 1) {
                set.connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                val previousRow = titleViewHolderArray[reversedIndex + 1].itemView
                set.connect(row.id, ConstraintSet.BOTTOM, previousRow.id, ConstraintSet.TOP)
            }
            set.connect(row.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            set.connect(row.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }
        TransitionManager.beginDelayedTransition(this)
        set.applyTo(this)

        val set2 = ConstraintSet()
        set2.clone(this)
        contentViewHolder?.let {
            val content = it.itemView
            val positionAboveContent = selectedPosition
            val positionBelowContent = selectedPosition + 1

            val rowIdAboveContent = titleViewHolderArray[positionAboveContent].itemView.id
            val rowIdBelowContent = if (selectedPosition == titleViewHolderArray.size - 1) {
                ConstraintSet.PARENT_ID
            } else {
                titleViewHolderArray[positionBelowContent].itemView.id
            }
            set.clear(content.id, ConstraintSet.TOP)
            set.clear(content.id, ConstraintSet.BOTTOM)
            set2.connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
            set2.connect(
                    content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == titleViewHolderArray.size - 1) {
                ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
            } else {
                ConstraintSet.TOP
            }
            )
            set.connect(content.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            set.connect(content.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        }
        TransitionManager.beginDelayedTransition(this)
        set2.applyTo(this)
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

    open class ViewHolder(val itemView: View)
}
