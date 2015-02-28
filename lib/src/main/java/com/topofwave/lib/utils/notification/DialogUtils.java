package com.topofwave.lib.utils.notification;


import com.topofwave.lib.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;

public class DialogUtils {

    /**
     * 创建一个ProgressDialog
     * @param activity
     * @param progressDialogTitleId 标题资源id .如果小于或者等于0，使用默认id
     * @param progressDialogMsgId 信息id
     * @return The new dialog
     */
    public static ProgressDialog createProgressDialog(final Activity activity, int progressDialogTitleId, int progressDialogMsgId) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        if(progressDialogTitleId <= 0) {
            progressDialogTitleId = R.string.app_name;
        }
        progressDialog.setTitle(progressDialogTitleId);
        if(progressDialogMsgId <= 0) {
            progressDialogMsgId = R.string.process_tip;
        }
        progressDialog.setMessage(activity.getString(progressDialogMsgId));
        progressDialog.setIndeterminate(true);
        progressDialog.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                activity.onKeyDown(keyCode, event);
                return false;
            }
        });
        // progressDialog.setInverseBackgroundForced(true);
        return progressDialog;
    }

    /**
     * 创建一个确定取消dialog
     * @param context
     * @param dialogTitle
     * @param screenMessage
     * @param iconResourceId
     * @param listener
     * @return
     */
    public static AlertDialog newYesNoDialog(final Context context, String dialogTitle, String screenMessage, int iconResourceId,
        String positiveMessage, String negativeMessage, OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        if(positiveMessage == null)
            builder.setPositiveButton(android.R.string.yes, listener);
        else
            builder.setPositiveButton(positiveMessage, listener);
        if(negativeMessage == null)
            builder.setNegativeButton(android.R.string.no, listener);
        else
            builder.setNegativeButton(negativeMessage, listener);
        builder.setTitle(dialogTitle);
        builder.setMessage(screenMessage);
        builder.setIcon(iconResourceId);
        return builder.create();
    }

    /**
     * 创建一个新AlertDialog，展示一些简单信息
     * @param context
     * @param dialogTitle
     * @param screenMessage
     * @param iconResourceId
     * @return
     */
    public static AlertDialog newMessageDialog(final Context context, String dialogTitle, String screenMessage, int iconResourceId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle(dialogTitle);
        builder.setMessage(screenMessage);
        builder.setIcon(iconResourceId);

        return builder.create();
    }

    /**
     * 创建一个自定义dialog
     * @param context
     * @param theme
     * @param view
     * @return
     */
    public static Dialog newCustomDialog(final Context context, int theme, View view, boolean cancelable) {
        Dialog customDialog = new Dialog(context, theme);
        customDialog.setCancelable(cancelable);
        customDialog.setContentView(view);
        return customDialog;
    }

}
