package com.example.sobesgbusmmap.carApp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.sobesgbusmmap.databinding.CarAnimationActivityBinding
import kotlin.math.absoluteValue
import kotlin.math.atan
import kotlin.math.pow

class CarMainActivity : AppCompatActivity() {

    private var prevAngle: Float = 0f
    private var startPoint = true

    private lateinit var binding: CarAnimationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarAnimationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - 130
        val height = displayMetrics.heightPixels - 130

        binding.ivCar.setOnClickListener { carView ->

            val pointList: MutableList<Pair<Int, Int>> = getRandomPath(width, height)
            val animationList: MutableList<ObjectAnimator> = mutableListOf()
            val animatorSet = AnimatorSet()
            startPoint = !startPoint

            for (i in 1 until pointList.size) {
                animationList.add(setRotationAnimation(carView, pointList[i - 1], pointList[i]))
                animationList.add(setTranslationAnimation(carView, pointList[i], pointList[i - 1]))
            }

            animatorSet.playSequentially(animationList as List<ObjectAnimator>)
            animatorSet.start()
        }
    }

    private fun setTranslationAnimation(
        carView: View,
        endPoint: Pair<Int, Int>,
        startPoint: Pair<Int, Int>
    ) = ObjectAnimator
        .ofFloat(
            carView,
            View.TRANSLATION_X,
            View.TRANSLATION_Y,
            getPath(endPoint, startPoint)
        ).apply {
            duration = getDurationOfPath(endPoint, startPoint)
            interpolator = LinearInterpolator()
        }

    private fun getPath(endPoint: Pair<Int, Int>, startPoint: Pair<Int, Int>): Path {

        val path = Path().apply {
            moveTo(
                startPoint.first.toFloat(),
                startPoint.second.toFloat()
            )
            rLineTo(
                endPoint.first.toFloat() - startPoint.first.toFloat(),
                endPoint.second.toFloat() - startPoint.second.toFloat()
            )
        }
        return path
    }

    private fun getRandomPath(width: Int, height: Int): MutableList<Pair<Int, Int>> {
        val randomPointList: MutableList<Pair<Int, Int>> = mutableListOf()
        if (startPoint) {
            randomPointList.add(Pair(0, 0))
            for (point in 0..(0..3).random()) {
                val deltaX = (0..width - 130).random()
                val deltaY = (0..height - 130).random()
                randomPointList.add(
                    Pair(
                        deltaX,
                        deltaY
                    )
                )
            }
            randomPointList.add(Pair(width, height))
        } else {
            randomPointList.add(Pair(width, height))
            for (point in 0..(0..3).random()) {
                val deltaX = (0..width - 130).random()
                val deltaY = (0..height - 130).random()
                randomPointList.add(
                    Pair(
                        deltaX,
                        deltaY
                    )
                )
            }
            randomPointList.add(Pair(0, 0))
        }
        return randomPointList
    }

    private fun getDurationOfPath(
        triple: Pair<Int, Int>,
        triple1: Pair<Int, Int>
    ): Long {
        return kotlin.math.sqrt(
            (triple.first.toDouble() - triple1.first.toDouble()).pow(2.0) +
               (triple.second.toDouble() - triple1.second.toDouble()).pow(2.0)
        ).toLong() * 2
    }

    private fun setRotationAnimation(
        carView: View,
        startPoint: Pair<Int, Int>,
        endPoint: Pair<Int, Int>
    ): ObjectAnimator {
        val startAngle = prevAngle
        var rotationAngle: Float
        if (startPoint.second == endPoint.second) {
            rotationAngle = if (endPoint.first >= startPoint.first) {
                0f
            } else {
                180f
            }
        } else {
            if (startPoint.first == endPoint.first) {
                rotationAngle = if (endPoint.second >= startPoint.second) {
                    90f
                } else {
                    -90f
                }
            } else {
                rotationAngle =
                    atan((startPoint.second - endPoint.second).toFloat() / (startPoint.first - endPoint.first).toFloat())
                rotationAngle = (rotationAngle * 180f / Math.PI).toFloat()
                if (startPoint.first > endPoint.first) {
                    rotationAngle =
                        if ((prevAngle - (rotationAngle - 180f)).absoluteValue < (prevAngle - (rotationAngle + 180f)).absoluteValue) {
                            rotationAngle - 180f
                        } else {
                            rotationAngle + 180f
                        }
                }
            }
        }
        prevAngle = rotationAngle
        return ObjectAnimator.ofFloat(carView, View.ROTATION, startAngle, rotationAngle)
    }

}