package com.example.licagent;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;



public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 150; // in ms

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0,mIndex++));
            if(mIndex<mText.length()+1){
                mHandler.postDelayed(runnable,mDelay);
            }
        }
    };
    public void animateText(CharSequence text){
        mText = text;
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable, mDelay);
    }
    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}
