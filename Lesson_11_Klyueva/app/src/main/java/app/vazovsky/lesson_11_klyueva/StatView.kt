package app.vazovsky.lesson_11_klyueva

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import app.vazovsky.lesson_11_klyueva.model.Column

class StatView : View {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var columnColor = Color.YELLOW
    private var valueColor = Color.YELLOW
    private var tilteColor = Color.GRAY

    private var columns = emptyList<Column>()

    private val columnPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = columnColor
        style = Paint.Style.FILL_AND_STROKE
        val cornerInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2F, resources.displayMetrics
        )
        val cornerEffect = CornerPathEffect(cornerInPixels)
        pathEffect = cornerEffect
    }
    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = valueColor
        val sizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            12F, resources.displayMetrics
        )
        textSize = sizeInPixels
        textAlign = Paint.Align.CENTER
    }
    private val valueTextBound = Rect()
    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = tilteColor
        val sizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            12F, resources.displayMetrics
        )
        textSize = sizeInPixels
        textAlign = Paint.Align.CENTER
    }
    private val titleTextBound = Rect()

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatView,
            0,
            0
        )
        try {
            columnColor = a.getColor(R.styleable.StatView_stat_columnColor, Color.YELLOW)
            valueColor = a.getColor(R.styleable.StatView_stat_valueColor, Color.YELLOW)
            tilteColor = a.getColor(R.styleable.StatView_stat_descColor, Color.GRAY)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = this.measuredHeight;
        val width = this.measuredWidth;

        setMeasuredDimension(width, height);
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStatistic(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_MOVE -> {
                startMyAnimation()
                true
            }
            else -> false
        }
    }

    private fun drawStatistic(canvas: Canvas) {
        val maxColumn: Int = columns.maxByOrNull { it.value }?.value ?: 100
        //запрет на использование более 9 столбцов
        val size = if (columns.size <= 9) columns.size else 9
        val currentWidth = width - paddingStart - paddingEnd
        val distance = currentWidth / size
        var startX = paddingStart.toFloat()

        for (i in 1..size) {
            drawColumn(canvas, columns[i - 1], startX to startX + distance, maxColumn)
            startX += distance
        }
    }

    private fun drawColumn(canvas: Canvas, column: Column, distance: Pair<Float, Float>, maxColumn: Int) {
        val startX = distance.first
        val endX = distance.second
        val centerX = startX + (endX - startX) / 2

        valuePaint.getTextBounds(column.value.toString(), 0, column.value.toString().length, valueTextBound)
        titlePaint.getTextBounds(column.value.toString(), 0, column.value.toString().length, titleTextBound)

        val marginInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8F, resources.displayMetrics
        )

        val currentStartY = paddingTop + valueTextBound.height()
        val currentEndY = (height - paddingBottom - titleTextBound.height()).toFloat()
        val currentMaxHeight = currentEndY - currentStartY
        val currentColumnHeight = (currentMaxHeight * column.value) / maxColumn

        canvas.drawText(column.value.toString(), centerX, currentEndY - currentColumnHeight, valuePaint)
        canvas.drawText(column.title, centerX, currentEndY, titlePaint)

        val halfWidthColumn = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2F, resources.displayMetrics
        )

        val path = Path().apply {
            moveTo(centerX - halfWidthColumn, currentEndY - currentColumnHeight + marginInPixels)
            lineTo(centerX + halfWidthColumn, currentEndY - currentColumnHeight + marginInPixels)
            lineTo(centerX + halfWidthColumn, currentEndY - titleTextBound.height() - marginInPixels)
            lineTo(centerX - halfWidthColumn, currentEndY - titleTextBound.height() - marginInPixels)
            close()
        }
        canvas.drawPath(path, columnPaint)
    }

    fun setData(data: List<Column>) {
        columns = data
    }

    fun startMyAnimation() {

    }
}