package com.tecsun.robot.nanning.util.snackbar;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidadvance.topsnackbar.TSnackbar;
import com.tecsun.robot.nanning.lib_base.R;

/**
 * Created by liudongwen on 2019/1/16.
 */
public final class SnackBarBuilder {
    private TSnackbar snackbar;

    public void show(final @Nullable View markView, @NonNull final Activity activity) {

        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(() -> {
                if (snackbar != null) {
                    snackbar.dismiss();
                }

                View contentView = null;
                if (markView != null) {
                    contentView = markView;
                } else if (markView == null) {
                    contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                }
                snackbar = TSnackbar
                        .make(contentView, "", TSnackbar.LENGTH_INDEFINITE);
                snackbar.setMaxWidth(0);
                snackbar.getView().setBackgroundColor(activity.getResources().getColor(R.color.c_fae5e7));
                snackbar.getView().setOnClickListener(v -> {
                    activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                });
                snackbar.show();
            });
        }
    }

    public void hide(final Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(() -> {
                if (snackbar != null) {
                    snackbar.dismiss();
                }
            });
        }
    }

}
