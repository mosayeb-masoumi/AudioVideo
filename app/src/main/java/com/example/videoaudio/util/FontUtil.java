package com.example.videoaudio.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class FontUtil {

    private Typeface font_regular;
    private static FontUtil ourInstance = null;

    public static FontUtil getInstance(Context context) {
        if (ourInstance == null) ourInstance = new FontUtil(context);
        return ourInstance;
    }

    private FontUtil(Context context) {
        font_regular = Typeface.createFromAsset(context.getAssets(), getPathRegularFont());
    }

    public String getPathRegularFont() {
        return "font1.TTF";
    }

    public Typeface getRegularFont() {
        return font_regular;
    }

    public static void overrideFont(Context context, String defaultFontNameToOverride) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), "font1.TTF");

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {

        }
    }
}
