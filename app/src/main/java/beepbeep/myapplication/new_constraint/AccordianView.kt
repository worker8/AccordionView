package beepbeep.myapplication.new_constraint

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class AccordianView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    var selectedPosition = 1
        set(value) {
            adapter?.let {
                if (value < it.getItemCount() && value >= 0) {
                    field = value
                    removeAllViews()
                    titleViewHolderArray.removeAll { true }
                    contentViewHolder = null
                    render()
//                    applyConstraint()
                }
            }
        }

    var adapter: AccordianAdapter? = null
        set(value) {
            field = value
            render()
        }
    private val titleViewHolderArray = mutableListOf<ViewHolder>()
    private var contentViewHolder: ViewHolder? = null

    private fun render() {
        createTitleViews()
        createContent()
        addAllViews()
        applyConstraint()
    }

    private fun addAllViews() {
        adapter?.let { _adapter ->
            titleViewHolderArray.forEach {
                addView(it.itemView)
            }
            contentViewHolder?.apply { addView(itemView) }
        }
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
                        selectedPosition = index
                    }
                    val arrowDirection = if (selectedPosition == index) {
                        AccordianAdapter.ArrowDirection.NONE
                    } else if (index < selectedPosition) {
                        AccordianAdapter.ArrowDirection.DOWN
                    } else {
                        AccordianAdapter.ArrowDirection.UP
                    }
                    _adapter.onBindViewForTitle(viewHolder, index, arrowDirection)

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

    private fun applyConstraint() {
        val set = ConstraintSet()
        set.clone(this)
        for (index in 0..selectedPosition) {
            val row = titleViewHolderArray[index].itemView

            if (index == 0) {
                set.connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                val previousRow = titleViewHolderArray[index - 1].itemView
                set.connect(row.id, ConstraintSet.TOP, previousRow.id, ConstraintSet.BOTTOM)
            }
        }

        for (reversedIndex in titleViewHolderArray.size - 1 downTo selectedPosition + 1) {
            val row = titleViewHolderArray[reversedIndex].itemView
            if (reversedIndex == titleViewHolderArray.size - 1) {
                set.connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                val previousRow = titleViewHolderArray[reversedIndex + 1].itemView
                set.connect(row.id, ConstraintSet.BOTTOM, previousRow.id, ConstraintSet.TOP)
            }
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

            set2.connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
            set2.connect(
                    content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == titleViewHolderArray.size - 1) {
                ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
            } else {
                ConstraintSet.TOP
            }
            )
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
}
