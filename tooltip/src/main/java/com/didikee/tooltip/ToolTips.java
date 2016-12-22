package com.didikee.tooltip;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by didik 
 * Created time 2016/12/20
 * Description: 
 */

public class ToolTips {

    private static WindowManager wm;
    private static WindowManager.LayoutParams wmParams;
    private ToolTips() {
    }

    public static void showTip(final Context context, String tipText, View anchor) {
        if (context==null || anchor==null)return ;
        ToolTips toolTips = new ToolTips();
        if (wm==null){
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (wmParams==null){
            wmParams = new WindowManager.LayoutParams();
            wmParams.format = PixelFormat.RGBA_8888;
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            wmParams.gravity = Gravity.LEFT | Gravity.TOP;
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;//do Not need any permissions
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        int[] screen = new int[2];
        anchor.getLocationOnScreen(screen);

        final View tipContentView = LayoutInflater.from(context).inflate(R.layout
                .layout_tooltips, null, false);
        tipText = TextUtils.isEmpty(tipText) ? "" : tipText;
        ((TextView) tipContentView.findViewById(R.id.tip)).setText(tipText);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        tipContentView.measure(w, h);
        int tipHeight = tipContentView.getMeasuredHeight();
        int tipWidth = tipContentView.getMeasuredWidth();

        int width = anchor.getWidth();
        int height = anchor.getHeight();

        int v = dp2px(context, 24);
        int anchorCenterX = screen[0] + width / 2;
        int x = anchorCenterX - tipWidth / 2;
        int y = screen[1] + height + v;

        if (x < v / 3) {
            x = v / 3;
        } else if (widthPixels - (x + tipWidth) < v / 3) {
            x = widthPixels - (v / 3 + tipWidth);
        }

        if (heightPixels - (screen[1] + height + v + tipHeight) < v / 3) {
            y = screen[1] - v - tipHeight;
        }
        wmParams.x = x;
        wmParams.y = y;

        wmParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        wmParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //add animations
        wmParams.windowAnimations = R.style.ToolTipAnimation;

        wm.addView(tipContentView, wmParams);

        anchor.postDelayed(new Runnable() {
            @Override
            public void run() {
                wm.removeView(tipContentView);
            }
        }, 1500);
    }

    public static void destroy(View view) {
        if (view==null){
            System.gc();
            return;
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                wmParams=null;
                wm=null;
                System.gc();
            }
        },2000);

    }

    private static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
