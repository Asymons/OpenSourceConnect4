package ca.asymons.connect4

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by alessiosymons on 2017-12-31.
 */
class ResizeableImageView : ImageView {

    constructor(context : Context) : super(context) {

    }

    constructor(context : Context, attrs : AttributeSet) : super(context,attrs) {

    }


    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int) : super(context,attrs,defStyleAttr) {

    }

    constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int) : super(context,attrs,defStyleAttr, defStyleRes) {

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable
        if(drawable != null){
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = Math.ceil(width.toDouble() * (drawable.intrinsicHeight.toDouble())/(drawable.intrinsicWidth.toDouble()))
            setMeasuredDimension(width,height.toInt())
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}