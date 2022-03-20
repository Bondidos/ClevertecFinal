package com.bondidos.clevertecfinal.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bondidos.clevertecfinal.R
import com.bondidos.clevertecfinal.databinding.PostResponceDialogBinding


const val KEY = "key"

class PostDialog : DialogFragment(R.layout.post_responce_dialog) {

    private val binding by viewBinding(PostResponceDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener { dismiss() }
        arguments?.let {
            binding.textView.text = it.getString(KEY)
        }
    }

    companion object {
        fun newInstance(message: String): PostDialog {
            val args = Bundle()
            args.putString(KEY, message)
            val fragment = PostDialog()
            fragment.arguments = args
            return fragment
        }
    }
}