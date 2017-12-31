package ca.asymons.connect4

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by alessiosymons on 2017-12-31.
 */
class BoardAdapterDecorator(private val spaceInPixels : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        outRect?.left = spaceInPixels
//        outRect?.right = spaceInPixels
//        outRect?.bottom = spaceInPixels
//
//        if(parent.getChildLayoutPosition(view) == 0){
//            outRect.top = spaceInPixels
//        }else{
//            outRect.t
//        }

        super.getItemOffsets(outRect, view, parent, state)
        outRect?.setEmpty()
    }
}