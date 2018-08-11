package beepbeep.myapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_small.view.*


class SmallContentFragment : Fragment() {
    //    var view:View? = null
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_small, parent, false)
        view.smallTextView.text = "ASJDKLJSKDLAJSKLDAJSKDJSADKAJSKDLASJKLASJDLASJKDLASJDKLASJKDJKDL"

        return view
    }

    fun setupView(string: String) {
        view?.smallTextView?.text = string
    }

    companion object {
        fun newInstance() = SmallContentFragment()
    }
}