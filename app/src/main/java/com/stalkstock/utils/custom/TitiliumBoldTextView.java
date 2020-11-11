package com.stalkstock.utils.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TitiliumBoldTextView extends androidx.appcompat.widget.AppCompatTextView {

    public TitiliumBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitiliumBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitiliumBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/TitilliumWeb_Bold.ttf");
            setTypeface(tf);
        }
    }
}