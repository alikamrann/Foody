package ir.world.number.foody.ui.fragment.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import ir.world.number.foody.R
import ir.world.number.foody.databinding.FragmentOverviewBinding
import ir.world.number.foody.models.Result
import ir.world.number.foody.util.Constans.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    private var _binding:FragmentOverviewBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)



        binding.mainImageview.load(myBundle?.image)
        binding.favoriteTitleTextView.text = myBundle?.title.toString()
        binding.likesTextview.text = myBundle?.aggregateLikes.toString()
        binding.timeTextView.text = myBundle?.readyInMinutes.toString()
        binding.summeryTextView.text = myBundle?.summary.toString()
        try {
            myBundle?.summary.toString().let {
                val summery = Jsoup.parse(it).text()
                binding.summeryTextView.text = summery.toString()
            }
        }catch (e  :Exception){Log.d("ssswqww",e.toString())}

        if (myBundle?.vegetarian == true) {
            binding.vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.vegan == true) {
            binding.veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.glutenFree == true) {
            binding.glutenfreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.glutenfreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.dairyFree == true) {
            binding.dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            binding.healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true) {
            binding.cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            binding.cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}