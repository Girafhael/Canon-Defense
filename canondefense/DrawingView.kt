package com.example.canondefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.fragment.app.FragmentActivity


class DrawingView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0):
    SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback,Runnable {

    var drawing = false
    var screenWidth = 0f
    var screenHeight = 0f
    private val backgroundPaint = Paint()
    private val canon = Canon(0f, 0f, 0f, 0f, this)
    val Ennemis = ArrayList<Ennemi>()
    lateinit var thread: Thread
    lateinit var canvas: Canvas
    init {
        backgroundPaint.color = Color.WHITE
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}
    override fun surfaceCreated(holder: SurfaceHolder) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    fun pause() {
        drawing = false
        thread.join()
    }
    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }
    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        canon.canonBaseRadius = (h / 24f)
        canon.canonLongueur = (w / 46f)
        canon.largeur = (w / 24f)
        canon.setFinCanon(h/2f)
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                canvas.height.toFloat(), backgroundPaint)
            canon.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        while (drawing) {
            draw()
        }
    }

}