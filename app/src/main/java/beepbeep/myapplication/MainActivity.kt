package beepbeep.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

data class DataModel(val title: String, val desc: String)

class MainActivity : AppCompatActivity() {

    val CONSTANT = 999

    val dataArray: List<DataModel> = listOf(
            DataModel("Dinosaur", "this is a T-Rex"),
            DataModel("Fruit", "papaya avocado mango"),
            DataModel("Gadget", "mouse keyboard"),
            DataModel("Songs", "birthday music")
    )

    val selectedPosition = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (index in 0..selectedPosition) {
            val dataModel = dataArray[index]
            val row = layoutInflater.inflate(R.layout.row, container, false).apply {
                id = index + CONSTANT
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
                id = reversedIndex + CONSTANT
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
    }
}
