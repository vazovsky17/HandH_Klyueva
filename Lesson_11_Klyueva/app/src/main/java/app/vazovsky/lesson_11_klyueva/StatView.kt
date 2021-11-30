package app.vazovsky.lesson_11_klyueva

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import app.vazovsky.lesson_11_klyueva.model.ColumnWithDate
import java.text.SimpleDateFormat

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
    private var titleColor = Color.GRAY

    private var columns = emptyList<Column>()
    private var maxColumn = 0

    private var valueAnim = 0F
    private var animation: ValueAnimator? = null

    //Размеры
    private val cornerInPixels = resources.getDimensionPixelSize(R.dimen.column_corners_radius).toFloat()
    private val textSizeInPixels = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        12F, resources.displayMetrics
    )
    private val marginInPixels = resources.getDimensionPixelSize(R.dimen.column_margin).toFloat()
    private val halfWidthColumn = resources.getDimensionPixelSize(R.dimen.column_half_width).toFloat()

    //Paints
    private val columnPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = columnColor
        style = Paint.Style.FILL_AND_STROKE
        val cornerEffect = CornerPathEffect(cornerInPixels)
        pathEffect = cornerEffect
        strokeWidth = halfWidthColumn
    }
    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = valueColor
        textSize = textSizeInPixels
        textAlign = Paint.Align.CENTER
    }
    private val valueTextBound = Rect()
    private val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = titleColor
        textSize = textSizeInPixels
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
            setColors(a)
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

    private fun setColors(a: TypedArray) {
        columnColor = a.getColor(R.styleable.StatView_stat_columnColor, Color.YELLOW)
        columnPaint.color = columnColor
        valueColor = a.getColor(R.styleable.StatView_stat_valueColor, Color.YELLOW)
        valuePaint.color = valueColor
        titleColor = a.getColor(R.styleable.StatView_stat_descColor, Color.GRAY)
        titlePaint.color = titleColor
    }

    private fun drawStatistic(canvas: Canvas) {
        val realWidthGraph = width - paddingStart - paddingEnd
        val size = if (columns.size <= 9) columns.size else 9
        val widthColumn = realWidthGraph / size

        var startX = paddingStart.toFloat()
        val startY = paddingTop.toFloat()
        val endY = (height - paddingBottom).toFloat()

        for (i in 0 until size) {
            valuePaint.getTextBounds(columns[i].value.toString(), 0, columns[i].value.toString().length, valueTextBound)
            titlePaint.getTextBounds(columns[i].value.toString(), 0, columns[i].value.toString().length, titleTextBound)
            drawColumn(
                canvas = canvas,
                column = columns[i],
                startX = startX,
                endX = startX + widthColumn,
                startY = startY + valueTextBound.height(),
                endY = endY - titleTextBound.height()
            )
            startX += widthColumn
        }
    }

    private fun drawColumn(canvas: Canvas, column: Column, startX: Float, endX: Float, startY: Float, endY: Float) {
        val centerX = startX + (endX - startX) / 2
        val heightColumn =
            if (valueAnim in 0F..0.1F) halfWidthColumn * 2 else (endY - startY) * column.value / maxColumn * valueAnim
        canvas.drawText(column.date, centerX, endY + titleTextBound.height(), titlePaint)

        val rect = RectF()
        rect.set(
            centerX - halfWidthColumn,
            endY - heightColumn - marginInPixels,
            centerX + halfWidthColumn,
            endY - marginInPixels
        )
        canvas.drawRoundRect(rect, 0F, 50F, columnPaint)
        canvas.drawText(column.value.toString(), centerX, endY - heightColumn - marginInPixels * 2, valuePaint)
    }

    fun setData(data: List<ColumnWithDate>) {
        columns = data.map {
            val format = SimpleDateFormat("dd.MM").format(it.date)
            Column(format, it.value)
        }
        maxColumn = columns.maxByOrNull { it.value }?.value ?: 100
    }

    fun startMyAnimation() {
        animation?.cancel()
        animation = ValueAnimator.ofFloat(0F, 1F).apply {
            duration = 1000
            addUpdateListener { animation ->
                valueAnim = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    data class Column(
        val date: String,
        val value: Int
    )
}