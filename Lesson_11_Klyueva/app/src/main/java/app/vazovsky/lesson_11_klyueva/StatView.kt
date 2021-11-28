package app.vazovsky.lesson_11_klyueva

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
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
    private var maxColumn = 0
    private var valueAnim = 1F
    private var animation: ValueAnimator? = null

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

    private fun drawStatistic(canvas: Canvas) {
        val realWidthGraph = width - paddingStart - paddingEnd
        val size = if (columns.size <= 9) columns.size else 9
        val widthColumn = realWidthGraph / size

        var startX = paddingStart.toFloat()
        var startY = paddingTop.toFloat()
        val endY = (height - paddingBottom).toFloat()

        for (i in 1..size) {
            valuePaint.getTextBounds(columns[i].value.toString(), 0, columns[i].value.toString().length, valueTextBound)
            titlePaint.getTextBounds(columns[i].value.toString(), 0, columns[i].value.toString().length, titleTextBound)
            drawColumn(
                canvas = canvas,
                column = columns[i - 1],
                startX = startX,
                endX = startX + widthColumn,
                startY = startY + valueTextBound.height(),
                endY = endY - titleTextBound.height()
            )
            startX += widthColumn
        }
    }

    private fun drawColumn(canvas: Canvas, column: Column, startX: Float, endX: Float, startY: Float, endY: Float) {
        val marginInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            4F, resources.displayMetrics
        )
        val halfWidthColumn = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2F, resources.displayMetrics
        )

        val centerX = startX + (endX - startX) / 2
        val heightColumn = (endY - startY) * column.value / maxColumn * valueAnim
        canvas.drawText(column.title, centerX, endY + titleTextBound.height(), titlePaint)

        val path = Path().apply {
            moveTo(centerX - halfWidthColumn, endY - marginInPixels)
            lineTo(centerX + halfWidthColumn, endY - marginInPixels)
            lineTo(centerX + halfWidthColumn, endY - heightColumn)
            lineTo(centerX - halfWidthColumn, endY - heightColumn)
            close()
        }
        canvas.drawPath(path, columnPaint)
        canvas.drawText(column.value.toString(), centerX, endY - heightColumn - marginInPixels, valuePaint)
    }

    fun setData(data: List<Column>) {
        columns = data
        maxColumn = columns.maxByOrNull { it.value }?.value ?: 100
    }

    fun startMyAnimation() {
        if (animation != null) {
            animation?.cancel()
            animation = null
        } else {
            animation = ValueAnimator.ofFloat(0.2F, 1F).apply {
                duration = 1000
                addUpdateListener { animation ->
                    valueAnim = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }
    }
}