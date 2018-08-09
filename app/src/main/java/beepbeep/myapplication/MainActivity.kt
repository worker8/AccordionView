package beepbeep.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.toBeReplaced, CustomBottomSheetDialogFragment.newInstance())
//                addToBackStack(null)
//                commit()
//            }

            CustomBottomSheetDialogFragment.newInstance().show(supportFragmentManager,"the only one")
        }
    }
}
