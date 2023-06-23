package com.example.qr_scanner.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.qr_scanner.R

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    private val initialText = "   "
    private val spacesCount = initialText.length

    private val hintPaint: Paint = Paint().apply {
        color = Color.GRAY
        textSize = 54f // Set the font size of the hint text
    }

    private var isInitialTextDisplayed = true

    init {
        setText(initialText)
        setSelection(spacesCount)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        val hintText = typedArray.getString(R.styleable.CustomEditText_customHintText)
        typedArray.recycle()

        if (!hintText.isNullOrEmpty()) {
            hint = hintText
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()

                if (isInitialTextDisplayed && text.length < spacesCount) {
                    setText(initialText)
                    setSelection(spacesCount)
                } else {
                    isInitialTextDisplayed = text == initialText
                    invalidate()
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isInitialTextDisplayed) {
            canvas?.drawText(hint.toString(), paddingLeft.toFloat(), baseline.toFloat(), hintPaint)
        }
    }
}
