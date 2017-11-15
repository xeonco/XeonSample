package cn.xeon.sample.bezier.lib;

import android.graphics.PointF;

/**
 * on 2017/10/24.
 *
 * @author LinZaixiong
 */

public class Bezier {

	public static void getCutOffPoint(float t, PointF startPoint, PointF endPoint,  PointF resultPoint){

		resultPoint.x = (1-t) * startPoint.x + t * endPoint.x;
		resultPoint.y = (1-t) * startPoint.y + t * endPoint.y;
	}

	public static void getTwoStepCutOffPoint(float t, PointF startPoint, PointF endPoint, PointF controlPoint, PointF resultPoint, PointF pointStart, PointF pointEnd){

		PointF p0 = startPoint;
		PointF p1 = controlPoint;
		PointF p2 = endPoint;

		PointF p01 = new PointF();
		PointF p11 = new PointF();
		PointF p02 = new PointF();

		float xP01Length = controlPoint.x - startPoint.x;
		float yP01Length = controlPoint.y - startPoint.y;

		p01.x = startPoint.x +  t * xP01Length;
		p01.y = startPoint.y + t * yP01Length;


		float xP12Length = endPoint.x - controlPoint.x;
		float yP12Length = endPoint.y - controlPoint.y;

		p11.x = controlPoint.x + t * xP12Length;
		p11.y = controlPoint.y + t * yP12Length;

		float xP012Length = p11.x - p01.x;
		float yP012Length = p11.y - p01.y;
		p02.x = p01.x + t * xP012Length;
		p02.y = p01.y + t * yP012Length;

		resultPoint.x = p02.x;
		resultPoint.y = p02.y;
//		resultPoint.x = (1-t) * startPoint.x + 2*t*(1-t)*controlPoint.x + t*t*endPoint.x;
//		resultPoint.y = (1-t) * startPoint.y + 2*t*(1-t)*controlPoint.y + t*t*endPoint.y;
	}


	public static void getTwoStepCutOffPoint2(float t, PointF startPoint, PointF endPoint, PointF controlPoint, PointF resultPoint){

		resultPoint.x = (1-t) *(1-t) *startPoint.x + 2*t*(1-t)*controlPoint.x + t*t*endPoint.x;
		resultPoint.y = (1-t) *(1-t) * startPoint.y + 2*t*(1-t)*controlPoint.y + t*t*endPoint.y;
	}



}
