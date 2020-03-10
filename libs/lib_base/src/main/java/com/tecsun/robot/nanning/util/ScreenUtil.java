package com.tecsun.robot.nanning.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class ScreenUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * @param activity
	 * @return screenHeight
	 */
	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	/**
	 * 
	 * @param activity
	 * @return screenWidth
	 */
	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	/**
	 * 第二种方法获取屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth2(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE );
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics( outMetrics);
		return outMetrics .widthPixels ;

	}


	// public static int getAppField(Activity activity){
	// Rect outRect = new Rect();
	// back
	// activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
	// }

	/**
	 * 状态栏的高度
	 * @param activity
	 * @return StatusHeight
	 */
	public int getStatusHeight(Activity activity) {
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 状态栏的高度
	 * @param context
	 * @return StatusHeight
	 */
	public static int getStatusHeight02(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 应用区域(包括标题栏)
	 * @param activity
	 * @return ApplicationFieldHeight
	 */
	public static int getApplicationFieldHeight(Activity activity) {
		Rect outRect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
		int ApplicationFieldHeight = outRect.height();
		return ApplicationFieldHeight;
	}

	/**
	 * View绘制区域的高度(不包括标题栏)
	 * @param activity
	 * @return UserDawAreaHeight
	 */
	public static int getViewDawAreaHeight(Activity activity) {
		Rect outRect = new Rect();
		activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
		int ViewDawAreaHeight = outRect.height();
		return ViewDawAreaHeight;
	}

}
