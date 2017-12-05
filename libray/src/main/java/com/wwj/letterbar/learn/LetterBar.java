package com.wwj.letterbar.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class LetterBar extends View {

    private Paint mPaint;
    private int mTextSize;
    private String[] letters=new String[]{"A","B","C","D","E","F","G","H","I","J"};
    private int mRectHeight,mRectWidth;
    private float mCharSize;
    private TextView tvDialog;
    private int oldIndex=-1;

    public LetterBar(Context context) {
        super(context);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mTextSize =sp2px(context,14);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mCharSize=mPaint.measureText("A");
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public  int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRectWidth=getWidth();
        mRectHeight=getHeight()/letters.length;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if(null!=tvDialog){
                    tvDialog.setVisibility(INVISIBLE);
                }
                oldIndex=-1;
                invalidate();

                break;
                default:
                    if(null!=tvDialog){
                        tvDialog.setVisibility(VISIBLE);
                    }

                    float y=event.getY();
                    int charIndex= (int) (y/mRectHeight);

                    if(charIndex>=0 && charIndex < letters.length){

                        if(charIndex!=oldIndex){
                            if(null!=onLetterChangeListener){
                                onLetterChangeListener.setOnLetterChangeListener(letters[charIndex],charIndex);
                            }
                            invalidate();
                            tvDialog.setText(letters[charIndex]);
                        }

                        oldIndex=charIndex;


                    }

                    break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<letters.length;i++){

            if(i%2==0){
                mPaint.setColor(Color.GREEN);
            }else{
                mPaint.setColor(Color.YELLOW);
            }

//            drawRect(0,i*rectHeight,rectWidth,i*rectHeight+rectHeight,paint)
            canvas.drawRect(0,i*mRectHeight,mRectWidth,i*mRectHeight+mRectHeight,mPaint);

            if(oldIndex==i){
                mPaint.setColor(Color.parseColor("#3A94FF"));
            }else{
                mPaint.setColor(Color.WHITE);
            }

//            drawText(字符串,rectWidth/2-文字大小/2，rectHeight/2+文字大小/2+i*rectHeight,paint）
            canvas.drawText(letters[i],mRectWidth/2-mPaint.measureText(letters[i])/2,mRectHeight/2+mCharSize/2+i*mRectHeight,mPaint);

        }

    }

    public void setTextView(TextView tvDialog) {
        this.tvDialog=tvDialog;
    }

    public interface OnLetterChangeListener{
        void setOnLetterChangeListener(String s, int charIndex);
    }

    private OnLetterChangeListener onLetterChangeListener;

    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener){
        this.onLetterChangeListener=onLetterChangeListener;
    }

}
