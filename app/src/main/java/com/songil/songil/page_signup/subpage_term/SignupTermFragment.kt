package com.songil.songil.page_signup.subpage_term

import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.SignUpProcess
import com.songil.songil.databinding.SignupFragmentTermBinding
import com.songil.songil.page_signup.SignupActivity
import com.songil.songil.page_signup.models.SignUpInfo
import com.songil.songil.recycler.adapter.PdfTermAdapter
import com.songil.songil.utils.copyInputStreamToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SignupTermFragment(private val signUpInfo: SignUpInfo) : BaseFragment<SignupFragmentTermBinding>(SignupFragmentTermBinding::bind, R.layout.signup_fragment_term) {

    private val viewModel : SignupTermViewModel by lazy { ViewModelProvider(this, SignupTermViewModel.SignupTermViewModelFactory(signUpInfo))[SignupTermViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setRecyclerView()
        setButton()
    }

    private fun setRecyclerView(){
        binding.rvTerm.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTerm.adapter = PdfTermAdapter(null, getWindowSize()[0])
    }

    private fun setButton(){
        binding.tvTermsRequiredUsage.setOnClickListener {

            showTermLayout()
            lifecycleScope.launch(Dispatchers.Default) {
                val result = kotlin.runCatching {
                    val inputStream = resources.assets.open("songil_terms_of_use.pdf")
                    val file = File.createTempFile(inputStream.hashCode().toString(), ".pdf")
                    copyInputStreamToFile(inputStream, file)

                    val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    val renderer = PdfRenderer(input)
                    withContext(Dispatchers.Main){
                        (binding.rvTerm.adapter as PdfTermAdapter).changeRenderer(renderer)
                    }
                    file.delete()
                }
                /*if (result.isFailure){
                    //에러처리 어떻게
                }*/
            }
        }

        binding.tvTermsRequiredPrivacyPolicy.setOnClickListener {
            showTermLayout()
            lifecycleScope.launch(Dispatchers.Default) {
                val result = kotlin.runCatching {
                    val inputStream = resources.assets.open("songil_privacy_policy.pdf")
                    val file = File.createTempFile(inputStream.hashCode().toString(), ".pdf")
                    copyInputStreamToFile(inputStream, file)

                    val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    val renderer = PdfRenderer(input)
                    withContext(Dispatchers.Main){
                        (binding.rvTerm.adapter as PdfTermAdapter).changeRenderer(renderer)
                    }
                    file.delete()
                }
                /*if (result.isFailure){
                    //에러처리 어떻게
                }*/
            }
        }

        binding.btnNext.setOnClickListener {
            viewModel.setAgreeState()
            (activity as SignupActivity).changeFragment(SignUpProcess.EMAIL)
        }

        binding.btnCancel.setOnClickListener {
            (activity as SignupActivity).changeFragment(SignUpProcess.CANCEL)
        }

        binding.btnConfirmation.setOnClickListener {
            hideTermLayout()
        }

        binding.backgroundTerm.setOnClickListener {
            hideTermLayout()
        }
    }

    private fun showTermLayout() {
        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        val anim = TranslateAnimation(0f, 0f, binding.layoutTerm.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true

        binding.backgroundTerm.animation = backgroundAnim
        binding.backgroundTerm.visibility = View.VISIBLE
        binding.layoutTerm.animation = anim
        binding.layoutTerm.visibility = View.VISIBLE
    }

    private fun hideTermLayout(){
        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        val anim = TranslateAnimation(0f, 0f, 0f, binding.layoutTerm.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false

        binding.backgroundTerm.animation = backgroundAnim
        binding.backgroundTerm.visibility = View.INVISIBLE
        binding.layoutTerm.animation = anim
        binding.layoutTerm.visibility = View.INVISIBLE
        (binding.rvTerm).scrollToPosition(0)
    }
}