package com.didikee.tooltips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.didikee.tooltip.ToolTip;

public class MainActivity extends AppCompatActivity implements View
        .OnLongClickListener {

    private View bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.button);
        final View bt2 = findViewById(R.id.button2);
        final View bt3 = findViewById(R.id.button3);
        final View bt_1 = findViewById(R.id.bt_1);
        final View bt_2 = findViewById(R.id.bt_2);
        final View bt_3 = findViewById(R.id.bt_3);
        final View bt_4 = findViewById(R.id.bt_4);

        bt.setOnLongClickListener(this);
        bt2.setOnLongClickListener(this);
        bt3.setOnLongClickListener(this);
        bt_1.setOnLongClickListener(this);
        bt_2.setOnLongClickListener(this);
        bt_3.setOnLongClickListener(this);
        bt_4.setOnLongClickListener(this);

    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId()==R.id.button3){
//            ToolTips.destroy(view);
        }else {
//            ToolTips.showTip(MainActivity.this,"This is ToolTip Show !",view);
            ToolTip.showTip(view,"This is ToolTip Show !");
        }
        return true;
    }
}
