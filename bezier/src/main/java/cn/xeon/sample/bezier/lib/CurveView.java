package cn.xeon.sample.bezier.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * on 2017/10/23.
 *
 * @author LinZaixiong
 */

public class CurveView extends BaseView {

	/**
	 *  模式
	 */
	public static enum ENUM_MODE{
		MODE_TWO_STEP,
		MODE_THREE_STEP
	}

	/**  路径 */
	private Path mPath = new Path();
	/** 模式 */
	private ENUM_MODE mEnumMode = ENUM_MODE.MODE_TWO_STEP;

	public CurveView(Context context) {
		super(context);
	}

	public CurveView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setMode(ENUM_MODE mode){
		reset();
		mEnumMode = mode;
		invalidate();
	}

	@Override
	protected void init(Context context) {
		super.init(context);
		mLinePaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawCurve(canvas);
	}

	private void drawCurve(Canvas canvas) {

		if(mEnumMode == ENUM_MODE.MODE_TWO_STEP){
			drawCurveTwoStep(canvas);
		}
		else if(mEnumMode == ENUM_MODE.MODE_THREE_STEP){
			drawCurveThreeStep(canvas);
		}

	}

	/**
	 *  画三阶
	 * @param canvas
	 */
	private void drawCurveThreeStep(Canvas canvas) {

		mPath.reset();
		for (int i = 0; i < mPoints.size(); i++) {
			drawCircle(canvas, mPoints.get(i).x, mPoints.get(i).y);


			int startLineIndex = i - 1;
			if (startLineIndex < 0) {
				startLineIndex = 0;
			}
			int stopLineIndex = i;

			float startLineX = mPoints.get(startLineIndex).x;
			float startLineY = mPoints.get(startLineIndex).y;
			float stopLineX = mPoints.get(stopLineIndex).x;
			float stopLineY = mPoints.get(stopLineIndex).y;
			drawLine(canvas, startLineX, startLineY, stopLineX, stopLineY);



			if(i != 0 && i >= 3 && i % 3 == 0){
				int startIndex = i - 3;
				if(startIndex < 0){
					startIndex = 0;
				}
				int stopIndex = i;
				int controlIndexFirst  = i - 2;
				int controlIndexSecond  = i - 1;

				float startX = mPoints.get(startIndex).x;
				float startY = mPoints.get(startIndex).y;
				float stopX = mPoints.get(stopIndex).x;
				float stopY = mPoints.get(stopIndex).y;

				float controlFirstX = mPoints.get(controlIndexFirst).x;
				float controlFirstY = mPoints.get(controlIndexFirst).y;
				float controlSecondX = mPoints.get(controlIndexSecond).x;
				float controlSecondY = mPoints.get(controlIndexSecond).y;
				if(startIndex == 0)
					mPath.moveTo(startX, startY);
				mPath.cubicTo(controlFirstX, controlFirstY, controlSecondX, controlSecondY, stopX, stopY);
				canvas.drawPath(mPath, mLinePaint);
			}
		}

	}

	/**
	 *  画二阶
	 * @param canvas
	 */
	private void drawCurveTwoStep(Canvas canvas) {
		mPath.reset();
		for(int i = 0; i < mPoints.size(); i++){
			drawCircle(canvas, mPoints.get(i).x, mPoints.get(i).y);


			int startLineIndex = i - 1;
			if(startLineIndex < 0){
				startLineIndex = 0;
			}
			int stopLineIndex = i;

			float startLineX = mPoints.get(startLineIndex).x;
			float startLineY = mPoints.get(startLineIndex).y;
			float stopLineX = mPoints.get(stopLineIndex).x;
			float stopLineY = mPoints.get(stopLineIndex).y;
			drawLine(canvas, startLineX, startLineY, stopLineX, stopLineY);

			if(i != 0 && i >= 2 && i % 2 == 0){
				int startIndex = i - 2;
				if(startIndex < 0){
					startIndex = 0;
				}
				int stopIndex = i;
				int controlIndex  = i - 1;

				float startX = mPoints.get(startIndex).x;
				float startY = mPoints.get(startIndex).y;
				float stopX = mPoints.get(stopIndex).x;
				float stopY = mPoints.get(stopIndex).y;

				float controlX = mPoints.get(controlIndex).x;
				float controlY = mPoints.get(controlIndex).y;
				mPath.moveTo(startX, startY);
				mPath.quadTo(controlX, controlY , stopX, stopY);
				canvas.drawPath(mPath, mLinePaint);
			}

		}
	}
}
