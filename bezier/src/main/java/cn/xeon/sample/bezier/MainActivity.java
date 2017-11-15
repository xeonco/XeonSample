package cn.xeon.sample.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import cn.xeon.sample.bezier.lib.BezierView;
import cn.xeon.sample.bezier.lib.CurveView;
import cn.xeon.sample.bezier.lib.NormalView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, BezierView.OnAutoValueCallback {

	private BezierView bv_bezier;
	private NormalView bv_normal;
	private CurveView bv_curve;
	private Spinner spinner;

	private int mState = 0; // 0 正常   1.curve  2 贝塞尔
	private View ll_bezier;
	private SeekBar sb_view;
	private CheckBox cb_view;
	private float t = 0; // bezier t value ,between 0 to 1
	private TextView tv_value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView tv_normal = (TextView)this.findViewById(R.id.tv_normal);
		tv_normal.setOnClickListener(this);

		TextView tv_bezier = (TextView)this.findViewById(R.id.tv_bezier);
		tv_bezier.setOnClickListener(this);

		TextView tv_curve = (TextView)this.findViewById(R.id.tv_curve);
		tv_curve.setOnClickListener(this);

		tv_value = (TextView)this.findViewById(R.id.tv_value);
		ll_bezier = this.findViewById(R.id.ll_bezier);

		sb_view = (SeekBar)this.findViewById(R.id.sb_view);
		cb_view = (CheckBox)this.findViewById(R.id.cb_view);

		spinner = (Spinner)this.findViewById(R.id.spinner);
		bv_bezier = (BezierView)this.findViewById(R.id.bv_bezier);
		bv_normal  = (NormalView)this.findViewById(R.id.bv_normal);
		bv_curve  = (CurveView)this.findViewById(R.id.bv_curve);
		spinner.setOnItemSelectedListener(this);

		sb_view.setOnSeekBarChangeListener(this);
		cb_view.setOnCheckedChangeListener(this);
		bv_bezier.setOnAutoValueCallback(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_normal:
				onNormal();
				break;

			case R.id.tv_bezier:
				onBezier();
				break;

			case R.id.tv_curve:
				onCurve();
				break;
		}
	}

	private void onCurve(){
		mState = 1;
		String[] mItems = getResources().getStringArray(R.array.curve);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.VISIBLE);
		bv_bezier.reset();
		bv_normal.reset();
		bv_curve.reset();
		bv_bezier.setVisibility(View.GONE);
		bv_normal.setVisibility(View.GONE);
		ll_bezier.setVisibility(View.GONE);
		bv_curve.setVisibility(View.VISIBLE);
	}

	private void onNormal() {
		mState = 0;
		bv_bezier.reset();
		bv_normal.reset();
		bv_curve.reset();
		spinner.setVisibility(View.GONE);
		bv_bezier.setVisibility(View.GONE);
		bv_normal.setVisibility(View.VISIBLE);
		bv_curve.setVisibility(View.GONE);
		ll_bezier.setVisibility(View.GONE);
	}

	private void onBezier() {
		mState = 2;
		bv_bezier.reset();
		bv_normal.reset();
		bv_curve.reset();
		String[] mItems = getResources().getStringArray(R.array.bezier);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
		spinner.setAdapter(adapter);
		spinner.setVisibility(View.VISIBLE);
		bv_bezier.setVisibility(View.VISIBLE);
		bv_normal.setVisibility(View.GONE);
		bv_curve.setVisibility(View.GONE);
		ll_bezier.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		if(mState == 0){ // 正常

		}
		else if(mState == 1){ // curve
			if(i == 0){
				bv_curve.setMode(CurveView.ENUM_MODE.MODE_TWO_STEP);
			}
			else if(i == 1){
				bv_curve.setMode(CurveView.ENUM_MODE.MODE_THREE_STEP);
			}
		}
		else if(mState == 2){ // beizer
			if(i == 0){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_ONE_STEP);
			}
			else if(i == 1){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_TWO_STEP);
			}
			else if(i == 2){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_THREE_STEP);
			}
			else if(i == 3){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_FOUR_STEP);
			}
			else if(i == 4){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_FIVE_STEP);
			}
			else if(i == 5){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_SIX_STEP);
			}
			else if(i == 6){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_SEVEN_STEP);
			}
			else if(i == 7){
				bv_bezier.setMode(BezierView.ENUM_MODE.MODE_N_STEP);
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		t = (float)i / (float)100;
		bv_bezier.setT(t);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		if(b){
			bv_bezier.start();
		}
		else{
			bv_bezier.stop();
		}
	}

	@Override
	public void onAutoValueCallback(float value) {
		Log.v("jacklam", "value;" + value);
		tv_value.setText(String.format("%.2f", value));
	}
}
