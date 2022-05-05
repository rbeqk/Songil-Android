package com.songil.songil.page_term

import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.DisplayMetrics
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.Term
import com.songil.songil.databinding.SimpleBaseActivityBinding
import com.songil.songil.recycler.adapter.PdfTermAdapter
import com.songil.songil.utils.copyInputStreamToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class TermActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    var term : Term ?= null
    var termFileName : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = PdfTermAdapter(null, getWindowSize()[0])

        setButton()

        term = intent.getSerializableExtra("TERM") as Term?

        when (term) {
            Term.USE -> {
                termFileName = "songil_terms_of_use.pdf"
                binding.tvTitle.text = getString(R.string.use_term)
            }
            Term.PRIVACY -> {
                termFileName = "songil_privacy_policy.pdf"
                binding.tvTitle.text = getString(R.string.terms_privacy_policy)
            }
            null -> {
                showSimpleToastMessage(getString(R.string.wrong_access))
                finish()
            }
        }

        if (termFileName != null){
            showTerm(termFileName!!)
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    @Suppress("UNUSED_VARIABLE")
    private fun showTerm(fileName : String) {
        lifecycleScope.launch(Dispatchers.Default) {
            val result = kotlin.runCatching {
                val inputStream = resources.assets.open(fileName)
                val file = File.createTempFile(inputStream.hashCode().toString(), ".pdf")
                copyInputStreamToFile(inputStream, file)
                val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = PdfRenderer(input)
                withContext(Dispatchers.Main) {
                    (binding.rvContent.adapter as PdfTermAdapter).changeRenderer(renderer)
                }
                file.delete()
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getWindowSize() : ArrayList<Int> {
        val height : Int
        val width : Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val displayMetrics = resources.displayMetrics
            height = displayMetrics.heightPixels//windowMetrics.bounds.height()
            width = displayMetrics.widthPixels//windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        return arrayListOf(width, height)
    }
}