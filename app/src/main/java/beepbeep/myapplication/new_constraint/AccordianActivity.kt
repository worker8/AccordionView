package beepbeep.myapplication.new_constraint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.myapplication.DataModel
import beepbeep.myapplication.R
import kotlinx.android.synthetic.main.activity_accordian.*

class AccordianActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accordian)
        val adapter = ExampleAdapter(listOf(
                DataModel("title 1", "desc desc desc 1"),
                DataModel("title 2", "desc desc desc 2"),
                DataModel("title 3", "desc desc desc 3")
        ))
        accordian_view.adapter = adapter
    }
}