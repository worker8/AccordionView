package beepbeep.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        multiRecipeView.adapter = CustomMultiRecipeViewAdapter(listOf(
                DataModel("Dinosaur", "this is a T-Rex"),
                DataModel("Fruit", "papaya avocado mango"), // <-- selectedPosition
                DataModel("Gadget", "mouse keyboard"),      // <-- selectedPosition + 1
                DataModel("Songs", "birthday music")        // <-- dataArray.size   - 1
        ))
    }
}
