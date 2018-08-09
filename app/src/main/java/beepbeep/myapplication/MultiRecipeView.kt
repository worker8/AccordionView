package beepbeep.myapplication

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.row.view.*

class MultiRecipeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    val CONTENT_ID = 999 + 1000 // this is arbitrary

    var selectedPosition = 1

    val dataArray: List<DataModel> = listOf(
            DataModel("Dinosaur", "this is a T-Rex"),
            DataModel("Fruit", "papaya avocado mango"), // <-- selectedPosition
            DataModel("Gadget", "mouse keyboard"),      // <-- selectedPosition + 1
            DataModel("Songs", "birthday music")        // <-- dataArray.size   - 1
    )

    fun render() {
        for (index in 0..selectedPosition) {
            val dataModel = dataArray[index]
            val row = LayoutInflater.from(context).inflate(R.layout.row, this, false).apply {
                id = index + ID_OFFSET
                titleTextView.text = dataModel.title
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
        for (reversedIndex in dataArray.size - 1 downTo selectedPosition + 1) {
            val dataModel = dataArray[reversedIndex]
            val row = LayoutInflater.from(context).inflate(R.layout.row, this, false).apply {
                id = reversedIndex + ID_OFFSET
                titleTextView.text = dataModel.title
            }
            row.setOnClickListener {
                selectedPosition = reversedIndex
                render()
            }
            this.addView(row)

            val set = ConstraintSet()
            set.clone(this)
            if (reversedIndex == dataArray.size - 1) {
                set.connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            } else {
                set.connect(row.id, ConstraintSet.BOTTOM, row.id + 1, ConstraintSet.TOP)
            }

            set.applyTo(this)
        }

        val positionAboveContent = selectedPosition
        val positionBelowContent = selectedPosition + 1

        val rowIdAboveContent = positionAboveContent + ID_OFFSET
        val rowIdBelowContent = if (selectedPosition == dataArray.size - 1) {
            ConstraintSet.PARENT_ID
        } else {
            positionBelowContent + ID_OFFSET
        }

        val content = LayoutInflater.from(context).inflate(R.layout.content, this, false).apply {
            id = CONTENT_ID
            contentTextView.text = "CONTENT!! yay"
        }

        addView(content)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
        set.connect(content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == dataArray.size - 1) {
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