package com.songil.songil.custom_view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.Term
import com.songil.songil.databinding.ViewBusinessInformationBinding
import com.songil.songil.page_term.TermActivity

class BusinessInformationView(context : Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ViewBusinessInformationBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        binding.tvbtnConstPrivacyTerm.setOnClickListener {
            val intent = Intent(context, TermActivity::class.java)
            intent.putExtra("TERM", Term.PRIVACY)
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }

        binding.tvbtnConstUseTerm.setOnClickListener {
            val intent = Intent(context, TermActivity::class.java)
            intent.putExtra("TERM", Term.USE)
            (context as BaseActivity<*>).startActivityHorizontal(intent)
        }

        binding.btnShowBusinessInfo.setOnClickListener {
            if (binding.layoutBusinessInformation.visibility == View.GONE) {
                binding.layoutBusinessInformation.visibility = View.VISIBLE
                binding.ivVarArrow.setImageResource(R.drawable.ic_up)
            } else {
                binding.layoutBusinessInformation.visibility = View.GONE
                binding.ivVarArrow.setImageResource(R.drawable.ic_down)
            }
        }
    }

}