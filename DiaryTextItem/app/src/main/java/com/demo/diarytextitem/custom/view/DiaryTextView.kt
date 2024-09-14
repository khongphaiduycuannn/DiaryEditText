package com.demo.diarytextitem.custom.view

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat.getFont
import kotlin.math.max
import kotlin.math.min

class DiaryTextView : AppCompatEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var onSelectCharacter: (() -> Unit)? = null

    var onNonSelectCharacter: (() -> Unit)? = null

    private var listBullet = listOf<Bullet>()

    fun loadListBullet(listBullet: List<Bullet>) {
        this.listBullet = listBullet
    }

    fun getBulletById(id: Int): Bullet? {
        return listBullet.find {
            it.bulletId == id
        }
    }

    fun encode(): String {
        var result = ""
        text?.forEachIndexed { index, char ->
            val spans = text!!.getSpans(index, index + 1, Object::class.java)
            val hashMap = hashSpan(spans)

            result += OPEN
            hashMap.forEach { entry ->
                result += encodeSpan(entry.key, entry.value)
            }
            result += CLOSE

            result += char
        }
        return result
    }

    fun decode(s: String) {
        val spannableStringBuilder = SpannableStringBuilder()
        s.forEachIndexed { index, value ->
            if (value.toString() == OPEN) {
                var i = index
                var span = ""
                while (i < s.length - 1 && s[i].toString() != CLOSE) {
                    span += s[i]
                    i++
                }
                spannableStringBuilder.append(decodeCharSpan(s[i + 1].toString(), span))
            }
        }
        text = spannableStringBuilder
    }

    fun setStyle(align: Int?, fontFamilyRes: Int?, textColor: Int?, header: Int?) {
        when (align) {
            ALIGN_LEFT -> setAlignLeft()
            ALIGN_RIGHT -> setAlignRight()
            ALIGN_CENTER -> setAlignCenter()
        }
        fontFamilyRes?.let { setFontFamily(it) }
        textColor?.let { setTextColor(it) }
        header?.let { textSize = it.toFloat() }
    }

    fun setAlignLeft() {
        gravity = Gravity.START
    }

    fun setAlignCenter() {
        gravity = Gravity.CENTER_HORIZONTAL
    }

    fun setAlignRight() {
        gravity = Gravity.END
    }

    fun setFontFamily(fontRes: Int) {
        setTypeface(
            getFont(context, fontRes),
            typeface.style
        )
    }

    fun upTextSize(start: Int, end: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(RelativeSizeSpan(UP_SIZE), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun downTextSize(start: Int, end: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(RelativeSizeSpan(DOWN_SIZE), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun setTextColor(start: Int, end: Int, color: Int) {
        val spannable = SpannableString(text)
        spannable.setSpan(ForegroundColorSpan(color), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun setBold(start: Int, end: Int) {
        var spannable = SpannableString(text)
        val newSpannable = removeSpanInRange(spannable, BOLD, start, end)

        if (newSpannable == null)
            spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        else spannable = newSpannable

        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun setItalic(start: Int, end: Int) {
        var spannable = SpannableString(text)
        val newSpannable = removeSpanInRange(spannable, ITALIC, start, end)

        if (newSpannable == null)
            spannable.setSpan(StyleSpan(Typeface.ITALIC), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        else spannable = newSpannable

        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun setUnderLine(start: Int, end: Int) {
        var spannable = SpannableString(text)
        val newSpannable = removeSpanInRange(spannable, UNDERLINE, start, end)

        if (newSpannable == null)
            spannable.setSpan(UnderlineSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        else spannable = newSpannable

        setText(spannable)
        text?.let { setSelection(start, end) }
    }

    fun setBullet(bullet: Bullet) {
        if (!hasFocus() || text == null) return
        val oldPosition = selectionStart
        var i = selectionStart - 1
        while (i >= 0 && text!![i] != '\n') {
            i--
        }

        val isBullet = text!!.getSpans(i + 1, i + 2, DiaryBulletSpan::class.java).isNotEmpty()
        if (!isBullet) {
            val spannable = SpannableString(text!!.insert(i + 1, " "))
            spannable.setSpan(
                DiaryBulletSpan(bullet, BULLET_MARGIN),
                i + 1,
                i + 2,
                SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setText(spannable)
        } else {
            val spannable = SpannableStringBuilder(text)
            spannable.delete(i + 1, i + 2)
            text = spannable
        }
        setSelection(min(oldPosition, text.toString().length))
    }

    private fun hashSpan(spans: Array<Object>): HashMap<String, Object> {
        val result = HashMap<String, Object>()
        spans.forEach { span ->
            if (span is StyleSpan && span.style == Typeface.BOLD) result[BOLD] = span

            if (span is StyleSpan && span.style == Typeface.ITALIC) result[ITALIC] = span

            if (span is UnderlineSpan) result[UNDERLINE] = span

            if (span is ForegroundColorSpan) result[TEXT_COLOR] = span

            if (span is RelativeSizeSpan) {
                val sizeSpan = result[TEXT_SIZE]
                if (sizeSpan == null) result[TEXT_SIZE] = span
                else {
                    val sizeChange = (sizeSpan as RelativeSizeSpan).sizeChange
                    result[TEXT_SIZE] =
                        RelativeSizeSpan(sizeChange * (span as RelativeSizeSpan).sizeChange) as Object
                }
            }

            if (span is DiaryBulletSpan) {
                result[BULLET] = span
            }
        }
        return result
    }

    private fun encodeBold(span: Object): String =
        if (span is StyleSpan && span.style == Typeface.BOLD) BOLD else ""

    private fun encodeItalic(span: Object): String =
        if (span is StyleSpan && span.style == Typeface.ITALIC) ITALIC else ""

    private fun encodeUnderLine(span: Object): String =
        if (span is UnderlineSpan) UNDERLINE else ""

    private fun encodeTextColor(span: Object): String =
        if (span is ForegroundColorSpan) {
            TEXT_COLOR + span.foregroundColor
        } else ""

    private fun encodeTextSize(span: Object): String =
        if (span is RelativeSizeSpan) {
            TEXT_SIZE + span.sizeChange
        } else ""

    private fun encodeBullet(span: Object): String =
        if (span is DiaryBulletSpan) {
            BULLET + span.bulletId
        } else ""

    private fun encodeSpan(key: String, value: Object): String =
        when (key) {
            BOLD -> encodeBold(value)
            ITALIC -> encodeItalic(value)
            UNDERLINE -> encodeUnderLine(value)
            TEXT_COLOR -> encodeTextColor(value)
            TEXT_SIZE -> encodeTextSize(value)
            BULLET -> encodeBullet(value)
            else -> ""
        }

    private fun decodeCharSpan(char: String, spans: String): SpannableString {
        val result = SpannableString(char)
        spans.forEachIndexed { index, value ->
            when (val span = value.toString()) {
                BOLD -> {
                    result.setSpan(StyleSpan(Typeface.BOLD), 0, 1, SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                ITALIC -> {
                    result.setSpan(StyleSpan(Typeface.ITALIC), 0, 1, SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                UNDERLINE -> {
                    result.setSpan(UnderlineSpan(), 0, 1, SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                TEXT_COLOR -> {
                    var i = index + 1
                    var color = ""
                    while (i < spans.length && !isSpan(spans[i].toString())) {
                        color += spans[i]
                        i++
                    }
                    result.setSpan(
                        ForegroundColorSpan(color.toInt()),
                        0,
                        1,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                TEXT_SIZE -> {
                    var i = index + 1
                    var color = ""
                    while (i < spans.length && !isSpan(spans[i].toString())) {
                        color += spans[i]
                        i++
                    }
                    result.setSpan(
                        RelativeSizeSpan(color.toFloat()),
                        0,
                        1,
                        SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                BULLET -> {
                    var i = index + 1
                    var bulletIdStr = ""
                    while (i < spans.length && !isSpan(spans[i].toString())) {
                        bulletIdStr += spans[i]
                        i++
                    }
                    val bulletId = bulletIdStr.toInt()
                    getBulletById(bulletId)?.let {
                        result.setSpan(
                            DiaryBulletSpan(it, BULLET_MARGIN),
                            0,
                            1,
                            SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
        return result
    }

    private fun isSpan(span: String): Boolean =
        (span == BOLD || span == ITALIC || span == UNDERLINE
                || span == TEXT_COLOR || span == TEXT_SIZE
                || span == BULLET
                || span == OPEN || span == CLOSE)

    private fun removeSpanInRange(
        spannable: SpannableString,
        spanType: String,
        start: Int,
        end: Int
    ): SpannableString? {
        var check = false
        spannable.getSpans(start, end, Object::class.java).forEach { span ->
            val filteredSpan = when (spanType) {
                BOLD -> {
                    var result: Object? = null
                    if (span is StyleSpan && span.style == Typeface.BOLD)
                        result = span
                    result
                }

                ITALIC -> {
                    var result: Object? = null
                    if (span is StyleSpan && span.style == Typeface.ITALIC)
                        result = span
                    result
                }

                UNDERLINE -> {
                    var result: Object? = null
                    if (span is UnderlineSpan)
                        result = span
                    result
                }

                else -> null
            }

            filteredSpan?.let {
                val spanStart = spannable.getSpanStart(span)
                val spanEnd = spannable.getSpanEnd(span)

                spannable.removeSpan(span)

                val spanLeft = when (spanType) {
                    BOLD -> StyleSpan(Typeface.BOLD)
                    ITALIC -> StyleSpan(Typeface.ITALIC)
                    UNDERLINE -> UnderlineSpan()
                    else -> null
                }

                val spanRight = when (spanType) {
                    BOLD -> StyleSpan(Typeface.BOLD)
                    ITALIC -> StyleSpan(Typeface.ITALIC)
                    UNDERLINE -> UnderlineSpan()
                    else -> null
                }

                if (spanStart < start) {
                    spannable.setSpan(spanLeft, spanStart, start, SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                if (spanEnd > end) {
                    spannable.setSpan(spanRight, end, spanEnd, SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                check = true
            }
        }
        return if (check)
            spannable
        else null
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            text?.let {
                var i = selectionStart - 1
                while (i >= 0 && it[i] != '\n') {
                    i--
                }
                val bullets = it.getSpans(i + 1, i + 2, DiaryBulletSpan::class.java)

                it.insert(selectionStart, "\n")
                if (bullets.isNotEmpty()) {
                    setBullet(bullets[0].bullet)
                }
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        text?.let {
            val start = min(selStart, selEnd)
            val end = max(selStart, selEnd)
            if (start < it.length) {
                val isBullet =
                    it.getSpans(start, start + 1, DiaryBulletSpan::class.java).isNotEmpty()
                if (isBullet) setSelection(start + 1, max(start + 1, end))
            }

            if (start == end) {
                onNonSelectCharacter?.invoke()
            } else {
                onSelectCharacter?.invoke()
            }
        }
    }

    companion object {

        const val BOLD = "\uDC00"
        const val ITALIC = "\uDC01"
        const val UNDERLINE = "\uDC02"
        const val TEXT_COLOR = "\uDC03"
        const val TEXT_SIZE = "\uDC04"

        const val BULLET = "\uDC05"

        const val OPEN = "\uDC06"
        const val CLOSE = "\uDC07"

        const val UP_SIZE = 1.15f
        const val DOWN_SIZE = 1 / 1.15f

        const val BULLET_MARGIN = 10

        const val ALIGN_STYLE = "align"
        const val ALIGN_LEFT = -1
        const val ALIGN_CENTER = -2
        const val ALIGN_RIGHT = -3

        const val FONT_FAMILY_STYLE = "fontFamily"
        const val TEXT_COLOR_STYLE = "textColor"

        const val HEADER_STYLE = "header"
    }
}