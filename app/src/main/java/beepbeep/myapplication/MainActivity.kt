package beepbeep.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val row = layoutInflater.inflate(R.layout.row, container, false)
        row.id = 1
        val row2 = layoutInflater.inflate(R.layout.row, container, false)
        row2.id = 2
        row2.titleTextView.text = "LALAL"

        val content = layoutInflater.inflate(R.layout.content, container, false)
        content.id = 3

        container.addView(row)
        container.addView(row2)
        container.addView(content)

        val set = ConstraintSet()
        set.clone(container)
        set.connect(1, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        set.connect(2, ConstraintSet.BOTTOM, 1, ConstraintSet.TOP)
        set.connect(3, ConstraintSet.BOTTOM, 2, ConstraintSet.TOP)
        set.connect(3, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        set.applyTo(container)


    }
}
