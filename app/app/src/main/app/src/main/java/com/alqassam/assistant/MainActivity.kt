package com.alqassam.assistant

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

private val blue = Color.rgb(42, 83, 166)
private val dark = Color.rgb(25, 39, 65)
private val pale = Color.rgb(243, 246, 252)

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val root = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setBackgroundColor(Color.WHITE)
        setPadding(20, 20, 20, 12)
    }

    val header = TextView(this).apply {
        text = "ق  |  القسام AI"
        textSize = 25f
        setTextColor(dark)
        gravity = Gravity.CENTER
        setPadding(8, 18, 8, 18)
    }
    root.addView(header)

    val subtitle = TextView(this).apply {
        text = "مساعدك الذكي في مكان واحد"
        textSize = 14f
        setTextColor(Color.GRAY)
        gravity = Gravity.CENTER
        setPadding(8, 0, 8, 20)
    }
    root.addView(subtitle)

    val welcome = TextView(this).apply {
        text = "أهلًا بك في القسام AI\n\nاسأل، تعلّم، وابدأ أفكار تطبيقاتك."
        textSize = 19f
        setTextColor(Color.WHITE)
        setBackgroundColor(blue)
        setPadding(20, 24, 20, 24)
        gravity = Gravity.CENTER
    }
    root.addView(welcome)

    val section = TextView(this).apply {
        text = "المحادثة الذكية"
        textSize = 20f
        setTextColor(dark)
        setPadding(0, 24, 0, 12)
    }
    root.addView(section)

    val scroll = ScrollView(this)
    val messages = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(12, 12, 12, 12)
    }

    fun addMessage(text: String, user: Boolean) {
        val bubble = TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(if (user) Color.WHITE else dark)
            setBackgroundColor(if (user) blue else pale)
            setPadding(16, 12, 16, 12)
            gravity = Gravity.RIGHT
        }

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 12)
        messages.addView(bubble, params)
    }

    addMessage("مرحبًا! أنا القسام. اكتب رسالتك وسأساعدك.", false)
    scroll.addView(messages)

    root.addView(
        scroll,
        LinearLayout.LayoutParams(0, 0, 1f)
    )

    val inputRow = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
    }

    val input = EditText(this).apply {
        hint = "اكتب رسالتك هنا..."
        textSize = 15f
        setSingleLine(false)
        maxLines = 3
        setTextColor(dark)
    }

    inputRow.addView(
        input,
        LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
    )

    val send = Button(this).apply {
        text = "إرسال"
        setTextColor(Color.WHITE)
        setBackgroundColor(blue)
    }

    inputRow.addView(send)
    root.addView(inputRow)

    send.setOnClickListener {
        val message = input.text.toString().trim()
        if (message.isNotEmpty()) {
            addMessage(message, true)
            addMessage(
                "وصلتني رسالتك. سنربط القسام بالذكاء الاصطناعي في الخطوة التالية.",
                false
            )
            input.text.clear()
            scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }
        }
    }

    setContentView(root)
}

}
