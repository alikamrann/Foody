package ir.world.number.foody.ui.fragment.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import ir.world.number.foody.R
import ir.world.number.foody.databinding.FragmentInstructionsBinding
import ir.world.number.foody.models.Result
import ir.world.number.foody.util.Constans

class InstructionsFragment : Fragment() {

    private var _binding :FragmentInstructionsBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constans.RECIPE_RESULT_KEY)

        binding.instructionsWebView.webViewClient= object : WebViewClient(){}
        val websiteUrl :String =myBundle!!.sourceUrl
        binding.instructionsWebView.loadUrl(websiteUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}

