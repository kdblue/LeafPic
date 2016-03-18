package com.leafpic.app.Views;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;

import com.leafpic.app.R;
import com.leafpic.app.utils.ColorPalette;

/**
 * Created by dnld on 23/02/16.
 */
public class ThemedActivity extends AppCompatActivity {

    SharedPreferences SP;

    private int primaryColor;
    private int accentColor;
    private boolean darkTheme;
    private boolean coloredNavBar;
    private boolean openCollapsing;
    private boolean oscuredStatusBar;
    private boolean applyThemeImgAct; //TASPARENCY


    public boolean isNavigationBarColored() {
        return coloredNavBar;
    }

    public boolean isTraslucentStatusBar() {
        return oscuredStatusBar;
    }

    public boolean isApplyThemeOnImgAct() {
        return applyThemeImgAct;
    }

    public boolean isDarkTheme() {
        return darkTheme;
    }

    public boolean isCollapsingToolbar() {
        return openCollapsing;
    }

    public boolean isTransparencyZero() {
        return 255 - SP.getInt("set_alpha", 0)==255 ? true : false;
    }

    public int getTransparency() {
        return 255 - SP.getInt("set_alpha", 0);
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getAccentColor() {
        return accentColor;
    }


    //METHOD
    public int getBackgroundColor(){
        if(darkTheme) return ColorPalette.getDarkBackgroundColor(getApplicationContext());
        else return ColorPalette.getLightBackgroundColor(getApplicationContext());
    }

    public int getTextColor(){
        if(darkTheme) return ColorPalette.getDarkTextColor(getApplicationContext());
        else return ColorPalette.getLightTextColor(getApplicationContext());
    }

    public void setNavBarColor() {
        if (isNavigationBarColored())
            getWindow().setNavigationBarColor(getPrimaryColor());
        else getWindow().setNavigationBarColor(ContextCompat.getColor(getApplicationContext(), R.color.md_black_1000));
    }

    @Override
    public void onResume(){
        super.onResume();
        updateTheme();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        updateTheme();
    }

    protected void setStatusBarColor() {
        /* if (makeTranslucent) {
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().setStatusBarColor(getPrimaryColor());
        }
        else getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        // TODO : IT WORKS BUT, MUST BE OPTIMIZED
        if (isTraslucentStatusBar()) {
            int c = getOscuredColor(getPrimaryColor());
            getWindow().setStatusBarColor(c);
        } else
        getWindow().setStatusBarColor(getPrimaryColor());
    }

    public int getOscuredColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.85f; // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public int getTransparentColor(int color, int alpha){
        int res = ColorUtils.setAlphaComponent(color, alpha);
        return  res;
    }

    public void updateTheme(){
        this.primaryColor = SP.getInt("primary_color", ContextCompat.getColor(getApplicationContext(),R.color.md_teal_500));//TEAL CARD BG DEFAULT;
        this.accentColor = SP.getInt("accent_color", ContextCompat.getColor(getApplicationContext(), R.color.md_orange_500));//TEAL COLOR DEFAULT
        darkTheme = SP.getBoolean("set_dark_theme", true);//DARK THEME DEFAULT
        coloredNavBar = SP. getBoolean("nav_bar", false);
        openCollapsing = SP.getBoolean("set_collaps_toolbar", true);
        oscuredStatusBar = SP.getBoolean("set_traslucent_statusbar",false);
        applyThemeImgAct = SP.getBoolean("apply_theme_img_act", false);
    }

    public void setRecentApp(String text){
        BitmapDrawable drawable = ((BitmapDrawable) getDrawable(R.mipmap.ic_launcher));
        setTaskDescription(new ActivityManager.TaskDescription(text, drawable.getBitmap(), getPrimaryColor()));
    }
}
