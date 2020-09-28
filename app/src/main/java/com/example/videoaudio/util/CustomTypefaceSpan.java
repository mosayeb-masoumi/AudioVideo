package com.example.videoaudio.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface newType;

    public CustomTypefaceSpan(String family, Typeface type) {
        super(family);
        newType = type;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(ds, newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }

    public static Spannable changeTypefaceShamsi(Context context, String first, String second) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "font1.TTF");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "font1.TTF");
        // Create a new spannable with the two strings
        Spannable spannable = new SpannableString(first + second);

        // Set the custom typeface to span over a section of the spannable object
        spannable.setSpan(new CustomTypefaceSpan("sans-serif", font), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new CustomTypefaceSpan("sans-serif", font2), first.length(), first.length() + second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }
    public static Spannable changeTypeface(Context context,String first, String second,String third) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "font1.TTF");
        Typeface font2 = Typeface.createFromAsset(context.getAssets(), "font1.TTF");
        // Create a new spannable with the two strings
        Spannable spannable = new SpannableString(first + second+third);

        // Set the custom typeface to span over a section of the spannable object
        spannable.setSpan(new CustomTypefaceSpan("sans-serif", font2), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new CustomTypefaceSpan("sans-serif", font), first.length(), first.length() + second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new CustomTypefaceSpan("sans-serif", font2), first.length() + second.length(), first.length() + second.length()+third.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }
}
