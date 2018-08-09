package beepbeep.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataArray: List<DataModel> = listOf(
                DataModel("Dinosaur", "this is a T-Rex"),
                DataModel("Fruit", "papaya avocado mango"),
                DataModel("Gadget", "mouse keyboard")
        )

        val CONSTANT = 999
        dataArray.forEachIndexed { index, dataModel ->
            val row = layoutInflater.inflate(R.layout.row, container, false)
            row.id = index + CONSTANT
            row.titleTextView.text = dataModel.title
            container.addView(row)
            Log.d("ddw", "${index} here")

            val set = ConstraintSet()
            set.clone(container)
            if (index == 0) {
                Log.d("ddw", "${index} here A")
                set.connect(row.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                Log.d("ddw", "${index} here B")
                set.connect(row.id, ConstraintSet.TOP, row.id - 1, ConstraintSet.BOTTOM)
            }
            set.applyTo(container)
        }
//        val row = layoutInflater.inflate(R.layout.row, container, false)
//        row.id = 1
//        container.addView(row)
////        if (index == 0) {
//        val set = ConstraintSet()
//        set.clone(container)
//            set.connect(1, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
//        set.applyTo(container)
//        val row = layoutInflater.inflate(R.layout.row, container, false)
//        row.id = 1
//        val row2 = layoutInflater.inflate(R.layout.row, container, false)
//        row2.id = 2
//        row2.titleTextView.text = "LALAL"

//        val content = layoutInflater.inflate(R.layout.content, container, false)
//        content.id = 3

//        container.addView(row)
//        container.addView(row2)
//        container.addView(content)

//        val set = ConstraintSet()
//        set.clone(container)
//        set.connect(1, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
//        set.connect(2, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
//        set.connect(3, ConstraintSet.TOP, 1, ConstraintSet.BOTTOM)
//        set.connect(3, ConstraintSet.BOTTOM, 2, ConstraintSet.TOP)
//        set.applyTo(container)
    }

    data class DataModel(val title: String, val desc: String)
}
