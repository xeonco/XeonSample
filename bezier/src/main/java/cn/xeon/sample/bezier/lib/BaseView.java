package cn.xeon.sample.bezier.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * on 2017/10/23.
 *
 * @author LinZaixiong
 */

public class BaseView extends View {
	/**  白色 */
	protected static final int COLOR_WHITE = 0xffffffff;
	/** 红色 */
	protected static final int COLOR_RED = 0xffff0000;

	protected static final int DEFAULT_RADIUS = 5;

	/**  画的点  */
	protected List<PointF> mPoints = new ArrayList<PointF>();
	/** 线画笔 */
	protected Paint mLinePaint = new Paint();
	/**  圆画笔 */
	protected Paint mCirclePaint = new Paint();
	/** 半径 */
	protected float mRadius = DEFAULT_RADIUS;

	public BaseView(Context context) {
		super(context);
		init(context);
	}

	public BaseView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	/**
	 *  重置view
	 */
	public void reset(){
		mPoints.clear();
		invalidate();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_DOWN){
			PointF point = new PointF(event.getX(), event.getY());
			mPoints.add(point);
			invalidate();
		}
		return super.onTouchEvent(event);
	}

	/**
	 *  初始化参数
	 * @param context 上下文
	 */
	protected void init(Context context){

		mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, context.getResources().getDisplayMetrics());

		mLinePaint.setColor(COLOR_WHITE);
		mLinePaint.setAntiAlias(true);

		mCirclePaint.setColor(COLOR_RED);
		mCirclePaint.setAntiAlias(true);
	}


	/**
	 *  画线
	 * @param canvas
	 * @param startX
	 * @param startY
	 * @param stopX
	 * @param stopY
	 */
	protected void drawLine(Canvas canvas, float startX, float startY, float stopX, float stopY){
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
	}

	/**
	 *  画圆
	 * @param canvas
	 */
	protected void drawCircle(Canvas canvas, float x, float y){
		canvas.drawCircle(x, y, mRadius, mCirclePaint);
	}
}
