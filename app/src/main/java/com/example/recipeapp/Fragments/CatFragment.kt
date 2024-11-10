package com.example.recipeapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.Activities.CategoryWiseRecipeActivity
import com.example.recipeapp.Fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.recipeapp.Models.Category
import com.example.recipeapp.R
import com.example.recipeapp.adapter.CategoryAdapter
import com.example.recipeapp.databinding.FragmentCatBinding
import com.example.recipeapp.databinding.FragmentHomeBinding
import com.example.recipeapp.viewModels.HomeViewModel

class CatFragment : Fragment(), CategoryAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by viewModels()


    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding!! // Use binding reference to access views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategoriesRecyclerView()
    }

    private fun setUpCategoriesRecyclerView() {
//        call the method in viewmodel to get the trending meals
        viewModel.getCategories()
//        observe the livedate
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { catList ->
            if (catList != null) {
                binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                binding.categoryRecyclerView.adapter = CategoryAdapter(catList, this)
            }
        }
    }

    override fun onItemClick(category: Category) {
        val intent = Intent(requireContext(), CategoryWiseRecipeActivity::class.java)
        intent.putExtra(CATEGORY_NAME, category.strCategory)
        startActivity(intent)
    }
}