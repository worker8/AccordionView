package beepbeep.accordionView.constraint_layout

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import beepbeep.accordionView.R
import kotlinx.android.synthetic.main.activity_constraint_layout.*

class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)
        val textViewRow = LayoutInflater.from(this).inflate(R.layout.text_view_row, constraintLayout, false)
        constraintLayout.addView(textViewRow)

        val set = ConstraintSet()
        set.clone(constraintLayout)

        set.connect(textViewRow.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        set.connect(textViewRow.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
        set.connect(textViewRow.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        set.applyTo(constraintLayout)

    }
}