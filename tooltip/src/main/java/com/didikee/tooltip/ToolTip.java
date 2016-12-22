package com.didikee.tooltip;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by didik 
 * Created time 2016/12/22
 * Description: 
 */

public class ToolTip {

    private static View tipView;
    private ToolTip() {
    }

    public static void showTip(View anchor,String text){
        Context context = anchor.getContext();
        if (tipView == null){
            tipView=LayoutInflater.from(context).inflate(R.layout.layout_tooltips_toast,null,false);
        }
        final Toast toast=new Toast(anchor.getContext());
        //location
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        int systemBarHeight = getSystemStatusBarHeight(context);
        int[] screen = new int[2];
        anchor.getLocationOnScreen(screen);


        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        tipView.measure(w, h);
        int tipHeight = tipView.getMeasuredHeight();
        int tipWidth = tipView.getMeasuredWidth();

        int width = anchor.getWidth();
        int height = anchor.getHeight();

        int v = dp2px(context, 24);
        int anchorCenterX = screen[0] + width / 2;
        int x = anchorCenterX - tipWidth / 2;
        int y = screen[1] + height + v;

        //Note: 默认是离屏幕 24/3=8dp 的距离
        if (x < v / 3) {
            x = v / 3;
        } else if (widthPixels - (x + tipWidth) < v / 3) {
            x = widthPixels - (v / 3 + tipWidth);
        }

        if (heightPixels - (screen[1] + height + v + tipHeight) < v / 3) {
            y = screen[1] - v - tipHeight -systemBarHeight;
        }else {
            y-=systemBarHeight;
        }
        toast.setGravity(Gravity.START|Gravity.TOP,x,y);
        toast.setView(tipView);
        toast.setText(text);

        toast.show();
        anchor.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },1250);
    }

    private static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private static int getSystemStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
