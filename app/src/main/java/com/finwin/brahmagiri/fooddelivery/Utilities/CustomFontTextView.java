package com.finwin.brahmagiri.fooddelivery.Utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.finwin.brahmagiri.fooddelivery.fooddelivery.R;

public class CustomFontTextView extends AppCompatTextView {

    String customFont;

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context, attrs);

    }

    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView);
        int cf = a.getInteger(R.styleable.CustomFontTextView_fontName, 0);
        int fontName = 0;
        switch (cf)
        {
            case 1:
                fontName = R.string.Roboto_Bold;
                break;
            case 2:
                fontName = R.string.Roboto_Italic;
                break;
            case 3:
                fontName = R.string.Roboto_Light;
                break;
            case 4:
                fontName = R.string.Roboto_Medium;
                break;
            case 5:
                fontName = R.string.Roboto_Regular;
                break;
            case 6:
                fontName = R.string.Roboto_Thin;
                break;
            default:
                fontName = R.string.Roboto_Regular;
                break;
        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + customFont + ".ttf");
        setTypeface(tf);
        a.recycle();
    }
}