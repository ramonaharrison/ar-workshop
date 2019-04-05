package com.nytimes.android.ramonaharrison

import android.R
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.text.Editable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.security.AccessController.getContext


class ResolveDialogFragment : DialogFragment() {

    interface OkListener {
        fun onOkPressed(dialogValue: String)
    }

    private var okListener: OkListener? = null
    private var shortCodeField: EditText? = null

    /** Sets a listener that is invoked when the OK button on this dialog is pressed.  */
    fun setOkListener(okListener: OkListener) {
        this.okListener = okListener
    }

    /**
     * Creates a simple layout for the dialog. This contains a single user-editable text field whose
     * input type is retricted to numbers only, for simplicity.
     */
    private fun getDialogLayout(): LinearLayout {
        val layout = LinearLayout(context)
        shortCodeField = EditText(context)
        shortCodeField!!.inputType = InputType.TYPE_CLASS_NUMBER
        shortCodeField!!.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        shortCodeField!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(8))
        layout.addView(shortCodeField)
        layout.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return layout
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder
            .setView(getDialogLayout())
            .setTitle("Resolve")
            .setPositiveButton(
                R.string.ok
            ) { _, _ ->
                val shortCodeText = shortCodeField!!.text
                if (okListener != null && shortCodeText != null && shortCodeText.isNotEmpty()) {
                    // Invoke the callback with the current checked item.
                    okListener!!.onOkPressed(shortCodeText.toString())
                }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
        return builder.create()
    }
}