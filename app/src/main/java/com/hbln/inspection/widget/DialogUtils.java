package com.hbln.inspection.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;

import com.hbln.inspection.R;
import com.hbln.lib_views.BottomPopupDialog;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 23:27
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class DialogUtils {
    private static final DialogUtils ourInstance = new DialogUtils();

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        return ourInstance;
    }

    public void showFont(Context context, final WebView webView) {
        SeekBar seekBar;
        final BottomPopupDialog fontDialog;
        fontDialog = new BottomPopupDialog(context, R.layout.dialog_font, true);
        fontDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontDialog.cancel();
            }
        });
        seekBar = (SeekBar) fontDialog.findViewById(R.id.seek_dialog_font);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                WebSettings settings = webView.getSettings();
                WebSettings.TextSize textSize;
                //50-150
                if (progress < 20) {
                    textSize = WebSettings.TextSize.SMALLEST;
                } else if (progress < 40) {
                    textSize = WebSettings.TextSize.SMALLER;
                } else if (progress < 60) {
                    textSize = WebSettings.TextSize.NORMAL;
                } else if (progress < 80) {
                    textSize = WebSettings.TextSize.LARGER;
                } else {
                    textSize = WebSettings.TextSize.LARGEST;
                }
                if (textSize != settings.getTextSize()) {
                    settings.setTextSize(textSize);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(getTextProgress(webView));
        fontDialog.show();
    }

    private int getTextProgress(WebView webView) {
        WebSettings settings = webView.getSettings();
        WebSettings.TextSize textSize = settings.getTextSize();
        //50-150
        if (textSize == WebSettings.TextSize.SMALLEST) {
            return 10;
        } else if (textSize == WebSettings.TextSize.SMALLER) {
            return 30;
        } else if (textSize == WebSettings.TextSize.NORMAL) {
            return 50;
        } else if (textSize == WebSettings.TextSize.LARGER) {
            return 70;
        } else {
            return 90;
        }
    }

    public Dialog showDefTwoBtn(Activity activity, String title, String message, String left, String right, final onDialogClickListener onLeftClick, final onDialogClickListener onRightClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(left)) {
            builder.setNegativeButton(left, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onLeftClick.dialogClickListener(dialog);
                }
            });
        }
        if (!TextUtils.isEmpty(right)) {
            builder.setPositiveButton(right, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onRightClick.dialogClickListener(dialog);
                }
            });
        }
        return builder.show();
    }

    public Dialog showOneBtn(Activity activity, String title, String message, String btn, final onDialogClickListener onClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (!TextUtils.isEmpty(btn)) {
            builder.setPositiveButton(btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onClick.dialogClickListener(dialog);
                }
            });
        }
        return builder.show();
    }

    public Dialog showProgress(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(R.layout.dialog_progress);
        builder.setCancelable(false);
        return builder.create();
    }

    /**
     * 自定义点击事件
     */
    public interface onDialogClickListener {
        void dialogClickListener(DialogInterface dialog);
    }
}
