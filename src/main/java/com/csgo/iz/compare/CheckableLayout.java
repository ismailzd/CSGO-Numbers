package com.csgo.iz.compare;

import com.csgo.iz.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLayout extends LinearLayout implements Checkable {
	private boolean mChecked;
	private Context context;

	public CheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }	
	
	public void setChecked(boolean checked) {
		mChecked = checked;
		setBackground(checked ? ContextCompat.getDrawable(context,android.R.color.holo_blue_dark) : null);
		
	}

	public boolean isChecked() {
		return mChecked;
	}

	public void toggle() {
		setChecked(!mChecked);
	}
}