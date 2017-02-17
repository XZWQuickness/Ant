package com.exz.antcargo.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class DrawableCenterButton extends RadioButton {

    public DrawableCenterButton(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public DrawableCenterButton(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[2];
            if (drawableLeft != null) {

                float textWidth = getPaint().measureText(getText().toString());//文字的宽度
                int drawablePadding = getCompoundDrawablePadding();//文字与drawable之间的宽度
                int drawableWidth = 0;
                drawableWidth = drawableLeft.getIntrinsicWidth();//drawable的宽度
                float bodyWidth = textWidth + drawableWidth + drawablePadding;//文字加图片加间距的宽度
                setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);//距离右边 控件总宽度，减去内容宽度，，，，将内容放到最左边
                canvas.translate((getWidth() - bodyWidth) / 2, 0);//把当前画布的原点移到 总宽度 减去内容/2 的位置开始画， 就是居中的
            }
        }
        super.onDraw(canvas);
    }
}