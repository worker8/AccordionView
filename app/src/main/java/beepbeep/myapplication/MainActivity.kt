package beepbeep.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.row.view.*

data class DataModel(val title: String, val desc: String)

class MainActivity : AppCompatActivity() {

    /**
     * this is needed, because ConstraintSet.PARENT_ID is 0, this can be any number
     */
    val ID_OFFSET = 999

    val CONTENT_ID = 999 + 1000 // this is arbitrary

    val dataArray: List<DataModel> = listOf(
            DataModel("Dinosaur", "this is a T-Rex")
//            DataModel("Fruit", "papaya avocado mango"), // <-- selectedPosition
//            DataModel("Gadget", "mouse keyboard"),      // <-- selectedPosition + 1
//            DataModel("Songs", "birthday music")        // <-- dataArray.size   - 1
    )

    val selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (selectedPosition > dataArray.size - 1) {
            return
        }

        for (index in 0..selectedPosition) {
            val dataModel = dataArray[index]
            val row = layoutInflater.inflate(R.layout.row, container, false).apply {
                id = index + ID_OFFSET
                titleTextView.text = dataModel.title
            }

            container.addView(row)

            val set = ConstraintSet().apply {
                clone(container)
                if (index == 0) {
                    connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                } else {
                    connect(row.id, ConstraintSet.TOP, row.id - 1, ConstraintSet.BOTTOM)
                }
            }
            set.applyTo(container)
        }

        for (reversedIndex in dataArray.size - 1 downTo selectedPosition + 1) {
            Log.d("ddw", "second: ${reversedIndex}")
            val dataModel = dataArray[reversedIndex]
            val row = layoutInflater.inflate(R.layout.row, container, false).apply {
                id = reversedIndex + ID_OFFSET
                titleTextView.text = dataModel.title
            }
            container.addView(row)

            val set = ConstraintSet().apply {
                clone(container)
                if (reversedIndex == dataArray.size - 1) {
                    connect(row.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                } else {
                    connect(row.id, ConstraintSet.BOTTOM, row.id + 1, ConstraintSet.TOP)
                }
            }
            set.applyTo(container)
        }

        val positionAboveContent = selectedPosition
        val positionBelowContent = selectedPosition + 1

        val rowIdAboveContent = positionAboveContent + ID_OFFSET
        val rowIdBelowContent = if (selectedPosition == dataArray.size - 1) {
            ConstraintSet.PARENT_ID
        } else {
            positionBelowContent + ID_OFFSET
        }

        val content = layoutInflater.inflate(R.layout.content, container, false).apply {
            id = CONTENT_ID
            contentTextView.text = "CONTENT!! yay"
        }

        container.addView(content)

        val set = ConstraintSet().apply {
            clone(container)
            connect(content.id, ConstraintSet.TOP, rowIdAboveContent, ConstraintSet.BOTTOM)
            connect(content.id, ConstraintSet.BOTTOM, rowIdBelowContent, if (selectedPosition == dataArray.size - 1) {
                ConstraintSet.BOTTOM // align to parent's bottom if it is the last one
            } else {
                ConstraintSet.TOP
            })
        }
        set.applyTo(container)
    }
}
