package beepbeep.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

data class DataModel(val title: String, val desc: String)

class MainActivity : AppCompatActivity() {

    val CONSTANT = 999

    val dataArray: List<DataModel> = listOf(
            DataModel("Dinosaur", "this is a T-Rex"),
            DataModel("Fruit", "papaya avocado mango"),
            DataModel("Gadget", "mouse keyboard")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataArray.forEachIndexed { index, dataModel ->
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
    }
}
