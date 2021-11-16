package com.example.songil.page_search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SearchActivityBinding
import com.example.songil.page_search.subpage_result.SearchResultFragment
import com.example.songil.page_search.subpage_searching.SearchingFragment

class SearchActivity : BaseActivity<SearchActivityBinding>(R.layout.search_activity) {

    private lateinit var searchingFragment: SearchingFragment
    private lateinit var resultFragment : SearchResultFragment
    private var isClickChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        searchingFragment = SearchingFragment()
        resultFragment = SearchResultFragment()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, resultFragment).commit()
        supportFragmentManager.beginTransaction().hide(resultFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, searchingFragment).commit()

        setEditTextView()
    }

    private fun setEditTextView(){
        binding.etSearchBar.setOnClickListener {
            supportFragmentManager.beginTransaction().hide(resultFragment).commit()
            supportFragmentManager.beginTransaction().show(searchingFragment).commit()
        }
        /*binding.etSearchBar.setOnKeyListener { v, keyCode, event ->
            Log.d("keycode", "${keyCode}}")
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                supportFragmentManager.beginTransaction().hide(searchingFragment).commit()
                supportFragmentManager.beginTransaction().show(resultFragment).commit()
            }
            true
        }*/
        binding.etSearchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                Log.d("test", v.text.toString())
                supportFragmentManager.beginTransaction().hide(searchingFragment).commit()
                supportFragmentManager.beginTransaction().show(resultFragment).commit()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}