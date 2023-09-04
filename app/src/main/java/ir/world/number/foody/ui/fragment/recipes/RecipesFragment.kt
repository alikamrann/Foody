package ir.world.number.foody.ui.fragment.recipes

import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.world.number.foody.viewmodels.MainViewModel
import ir.world.number.foody.R
import ir.world.number.foody.adapter.RecipesAdapter
import ir.world.number.foody.databinding.FragmentRecipesBinding
import ir.world.number.foody.util.NetworkListener
import ir.world.number.foody.util.NetworkResult
import ir.world.number.foody.util.observeOnce
import ir.world.number.foody.viewmodels.RecipesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint

class RecipesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val args by  navArgs<RecipesFragmentArgs>()
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!


    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var  networkListener: NetworkListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel=ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel


        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setHasOptionsMenu(true)
        setUpRecyclerview()
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner,{
            recipesViewModel.backOnline= it
        })


        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailablility(requireContext())
                .collect { status ->
                    Log.d("NetworkListener",status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }
        binding.recipesFab.setOnClickListener {
            if(recipesViewModel.networkStatus){
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)}
        else{
            recipesViewModel.showNetworkStatus()
        }
        }
        return binding.root
    }


    private fun setUpRecyclerview() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)

        val  search =menu.findItem(R.id.menu_search)
        val  searchView =search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled =true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query !=null){
            searchApiData(query)
        }
        return  true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return  true
    }


    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase called!")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimerEffect()
                } else {
                    requestApiData()
                }

            })
        }
    }


    private fun requestApiData() {
        //Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        Log.d("sss",recipesViewModel.applyQueries().toString())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimerEffect()
                    loadDataFromCash()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading->{
                    showShimerEffect()
                }
            }
        })
    }

    private fun searchApiData(searchQuery: String){
        showShimerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner,{response ->
            when(response){
                is NetworkResult.Success->{
                    hideShimerEffect()
                    val  foodRecipes =response.data
                    foodRecipes?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    hideShimerEffect()
                    loadDataFromCash()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                    is NetworkResult.Loading ->{
                        showShimerEffect()

                }
            }
        })
    }

    private fun loadDataFromCash() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

//    private fun applyQueries(): HashMap<String, String> {
//        val queries: HashMap<String, String> = HashMap()
//        queries[Constans.QUERY_NUMBER] = "50"
//        queries[QUERY_API_KEY] = Constans.API_KEY
//        queries[QUERY_TYPE] = "snack"
//        queries[QUERY_DIET] = "vegan"
//        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
//        queries[QUERY_FILL_INGREDIENTS] = "true"
//
//        return queries
//
//    }

    private fun showShimerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimerEffect() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}