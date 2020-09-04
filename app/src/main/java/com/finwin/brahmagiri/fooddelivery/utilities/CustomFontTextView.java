package com.finwin.brahmagiri.fooddelivery.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class CustomFontTextView extends AppCompatTextView {


    public CustomFontTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/JTMarnie-Medium.otf");
        this.setTypeface(face);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/JTMarnie-Medium.otf");
        this.setTypeface(face);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/JTMarnie-Medium.otf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
