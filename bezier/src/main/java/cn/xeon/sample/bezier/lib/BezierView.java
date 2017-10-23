package cn.xeon.sample.bezier.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * on 2017/10/23.
 *
 * @author LinZaixiong
 */

public class BezierView extends BaseView implements Runnable {

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

	/** t value, 0...1 */
	private float t = 0.0f;
	/**  模式 */
	private ENUM_MODE mMode = ENUM_MODE.MODE_ONE_STEP;
	/** 用于t的刷新 */
	private Handler handler = new Handler();
	/** 是否开始自动 */
	private boolean isStart = false;


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
	 *  设置t value
	 * @param t
	 */
	public void setT(float t){
		this.t = t;
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

			t += STEP_INC;
			if(t >= 1.0f){
				t = 0.0f;
			}

			if(t > 0.0f){
				setT(t);
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

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
