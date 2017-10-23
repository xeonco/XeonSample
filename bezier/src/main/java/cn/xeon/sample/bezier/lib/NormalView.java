package cn.xeon.sample.bezier.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * on 2017/10/23.
 *
 * @author LinZaixiong
 */

public class NormalView extends BaseView {



	public NormalView(Context context) {
		super(context);

	}

	public NormalView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

	}

	public NormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawLine(canvas);
	}

	/**
	 *  画线
	 * @param canvas
	 */
	private void drawLine(Canvas canvas) {

		for(int i = 0; i < mPoints.size(); i++){

			int startIndex = i - 1;
			if(startIndex < 0){
				startIndex = 0;
			}
			int stopIndex = i;
			float startX = mPoints.get(startIndex).x;
			float startY = mPoints.get(startIndex).y;
			float stopX = mPoints.get(stopIndex).x;
			float stopY = mPoints.get(stopIndex).y;

			drawCircle(canvas, stopX, stopY);
			drawLine(canvas, startX, startY, stopX, stopY);
		}
	}








}
