package com.example.money.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.example.money.LoginActivity;
import com.example.money.R;
import com.example.money.AoShan;
import com.example.money.entity.User;
import com.example.money.widget.SimpleBlockedDialogFragment;

import java.io.IOException;
import java.net.URL;

/**
 * Created by su on 15-11-18.
 */
public class UiHelper {

    public static void showLogoutDialog(final Context context) {
        new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                .setMessage(R.string.logout_dialog_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.clearUser(context);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra("back_to_main", true);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    public static AlertDialog showTip(Context context, String tip) {
        if (TextUtils.isEmpty(tip)) {
            return null;
        }
        return new AlertDialog.Builder(context)
                .setMessage(tip)
                .setPositiveButton(R.string.known, null)
                .show();
    }

    public static AlertDialog showConfirm(Context context, String tip) {
        return new AlertDialog.Builder(context)
                .setMessage(tip)
                .setPositiveButton(R.string.confirm, null)
                .show();
    }

    public static ShapeDrawable getShapeDrawable(int color, int radius) {
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

    public static LayerDrawable getBorderLayerDrawable(int color, int backgroundColor, int padding, int radius) {
        Drawable[] layers = new Drawable[2];
        layers[0] = getShapeDrawable(color, radius);
        layers[0].setState(new int[]{android.R.attr.state_enabled});
        layers[1] = getShapeDrawable(backgroundColor, radius);
        layers[1].setState(new int[]{android.R.attr.state_enabled});
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        layerDrawable.setLayerInset(1, padding, padding, padding, padding);
        return layerDrawable;
    }

    public static void showLoadErrorLayout(Context context, FrameLayout fl, boolean show, View.OnClickListener onClickListener) {
        View v = fl.findViewById(R.id.load_error_layout);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.load_error, null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = AoShan.sActionBarHeight;
            fl.addView(v, lp);
        }
        if (show) {
            v.setVisibility(View.VISIBLE);
            v.setOnClickListener(onClickListener);
            Toast.makeText(context, R.string.loading_error, Toast.LENGTH_SHORT).show();
        } else {
            v.setVisibility(View.GONE);
        }
    }

    public static void showLoadErrorLayout(Activity activity, boolean show, View.OnClickListener onClickListener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        View v = activity.findViewById(R.id.load_error_layout);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.load_error, null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = AoShan.sActionBarHeight;
            activity.addContentView(v, lp);
        }
        if (show) {
            v.setVisibility(View.VISIBLE);
            v.setOnClickListener(onClickListener);
            Toast.makeText(activity, R.string.loading_error, Toast.LENGTH_SHORT).show();
        } else {
            v.setVisibility(View.GONE);
        }
    }

    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                actionBarHeight += dp2px(8, context.getResources().getDisplayMetrics());
            }
        }
        return actionBarHeight;
    }

    public static void showLoadingLayout(Context context, FrameLayout fl, boolean show) {
        View v = fl.findViewById(R.id.progress_bar_layout);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loading, null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = AoShan.sActionBarHeight;
            fl.addView(v, lp);
        }
        if (show) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.GONE);
        }
    }

    public static void showLoadingLayout(Activity activity, boolean show) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        View bgView = activity.findViewById(R.id.main_layout);
        View v = activity.findViewById(R.id.progress_bar_layout);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loading, null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = AoShan.sActionBarHeight;
            activity.addContentView(v, lp);
        }

        if (show) {
            v.setVisibility(View.VISIBLE);
            if (bgView != null) {
                bgView.setVisibility(View.GONE);
            }
        } else {
            if (bgView != null) {
                bgView.setVisibility(View.VISIBLE);
            }
            v.setVisibility(View.GONE);
        }
    }

    public static int dp2px(int dpValue, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, displayMetrics);
    }

    public static int sp2px(int spValue, DisplayMetrics displayMetrics) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, displayMetrics);
    }
}
