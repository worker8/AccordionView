package beepbeep.myapplication

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.content.view.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.view.*


class CustomBottomSheetDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme)

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, parent, false)
        view.multiRecipeView.adapter = CustomMultiRecipeViewAdapter(listOf(
                DataModel("Dinosaur", "this is a T-Rex"),
                DataModel("Fruit", "papaya avocado mango"), // <-- selectedPosition
                DataModel("Gadget", "mouse keyboard"),      // <-- selectedPosition + 1
                DataModel("Songs", "birthday music")        // <-- dataArray.size   - 1
        )) { view ->
            val transaction = childFragmentManager.beginTransaction()
            Log.d("ddw", "view:${view}")
            Log.d("ddw", "view:${view.contentFragment.id}")

            for (fragment in childFragmentManager.fragments) {
                transaction.remove(fragment)
            }
            transaction.replace(view.contentFragment.id+1, SmallContentFragment.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        // creating the fullscreen dialog
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        return dialog
    }

    companion object {
        fun newInstance() = CustomBottomSheetDialogFragment()
    }
}