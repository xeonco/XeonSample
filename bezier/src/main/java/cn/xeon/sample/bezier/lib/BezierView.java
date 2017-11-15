package cn.xeon.sample.bezier.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * on 2017/10/23.
 *
 * @author LinZaixiong
 */

public class BezierView extends BaseView implements Runnable {
	/** 黄色 */
	protected static final int COLOR_YELLOW = 0xffffff00;
	private final long TIMES_MILLIS = 100;
	private final float STEP_INC = 0.01f;
	/**
	 *  模式
	 */
	public static enum ENUM_MODE{
		MODE_ONE_STEP,
		MODE_TWO_STEP,
		MODE_THREE_STEP,
		MODE_FOUR_STEP,
		MODE_FIVE_STEP,
		MODE_SIX_STEP,
		MODE_SEVEN_STEP,
		MODE_N_STEP
	}

	public static interface OnAutoValueCallback{
		void onAutoValueCallback(float value);
	}

	/** t value, 0...1 */
	private float tValue = 0.0f;
	/**  模式 */
	private ENUM_MODE mMode = ENUM_MODE.MODE_ONE_STEP;
	/** 用于t的刷新 */
	private Handler handler = new Handler();
	/** 是否开始自动 */
	private boolean isStart = false;
	/** 值的回调 */
	private OnAutoValueCallback mCallback;
	/** 切点值 */
	private PointF mResultPoint;
	/** 上个切点值 */
	private PointF mResultLastPoint;

	private Paint mCutOffPaint;

	public BezierView(Context context) {
		super(context);
	}

	public BezierView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	/**
	 *  设置callback
	 * @param callback
	 */
	public void setOnAutoValueCallback(OnAutoValueCallback callback){
		mCallback = callback;
	}

	/**
	 *  设置t value
	 * @param t
	 */
	public void setT(float t){
		tValue = t;
		if(mCallback != null){
			mCallback.onAutoValueCallback(tValue);
		}
		invalidate();
	}

	/**
	 *  t value auto increment, max value is 1
	 */
	public void start(){
		isStart = true;
		handler.post(this);
	}

	/**
	 *  stop auto increment
	 */
	public void stop(){
		isStart = false;
		handler.removeCallbacks(this);
	}

	/**
	 *  设置mode
	 * @param mode
	 */
	public void setMode(ENUM_MODE mode){
		reset();
		mMode = mode;
	}


	@Override
	public void run() {

		if(isStart){

			tValue += STEP_INC;
			if(tValue >= 1.0f){
				tValue = 0.0f;
				setT(tValue);
			}

			if(tValue > 0.0f){
				setT(tValue);
				handler.post(this);
			}
			else{
				isStart = false;
			}

		}
	}

	@Override
	protected void init(Context context) {
		super.init(context);
		mCutOffPaint = new Paint();
		mCutOffPaint.setAntiAlias(true);
		mCutOffPaint.setColor(COLOR_YELLOW);
		mResultLastPoint = new PointF();
		mResultLastPoint.x = -1;
		mResultLastPoint.y = -1;

		mResultPoint = new PointF();
		mResultPoint.x = -1;
		mResultPoint.y = -1;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBezier(canvas);
	}

	private void drawBezier(Canvas canvas) {

		for (int i = 0; i < mPoints.size(); i++) {
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
			if(mMode == ENUM_MODE.MODE_ONE_STEP){
				drawOneStep(canvas, i);
			}
			else if(mMode == ENUM_MODE.MODE_TWO_STEP){
				drawTwoStep(canvas, i);
			}
		}
	}

	private void drawOneStep(Canvas canvas, int i){

		if(i != 0 && i >= 1 && i % 1 == 0){
			int startIndex = i - 1;
			if(startIndex < 0){
				startIndex = 0;
			}
			int stopIndex = i;
			int controlIndex  = i - 1;
			mResultLastPoint.x = mResultPoint.x;
			mResultLastPoint.y = mResultPoint.y;
			Bezier.getCutOffPoint(tValue,  mPoints.get(startIndex), mPoints.get(stopIndex), mResultPoint);

			if(mResultLastPoint.x != -1 && mResultLastPoint.y != -1){
				canvas.drawCircle(mResultPoint.x, mResultPoint.y, mRadius, mCutOffPaint);
//				canvas.drawLine(mResultLastPoint.x, mResultLastPoint.y,  mResultPoint.x, mResultPoint.y, mCutOffPaint);
			}

		}
	}


	private void drawTwoStep(Canvas canvas, int i){

		if(i != 0 && i >= 2 && i % 2 == 0){
			int startIndex = i - 2;
			if(startIndex < 0){
				startIndex = 0;
			}
			int stopIndex = i;
			int controlIndex  = i - 1;
			mResultLastPoint.x = mResultPoint.x;
			mResultLastPoint.y = mResultPoint.y;
			Bezier.getTwoStepCutOffPoint2(tValue,  mPoints.get(startIndex), mPoints.get(stopIndex), mPoints.get(controlIndex), mResultPoint);

			if(mResultLastPoint.x != -1 && mResultLastPoint.y != -1){
				Log.v("jacklam", "point :" + mResultPoint  + " tValue:" + tValue);
				canvas.drawCircle(mResultPoint.x, mResultPoint.y, mRadius, mCutOffPaint);
//				canvas.drawLine(mResultLastPoint.x, mResultLastPoint.y,  mResultPoint.x, mResultPoint.y, mCutOffPaint);
			}

		}
	}
}
