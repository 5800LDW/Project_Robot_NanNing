package com.tecsun.robot.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;


/**
 * 视图工具类
 * Created by _Smile on 2016/5/31.
 */
public class ViewUtils {

    /**
     * 初始化控件
     * @param view 视图
     * @param resourceId 控件ID
     * @return 控件视图
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T xFindViewById(View view, int resourceId) {
        return (T) view.findViewById(resourceId);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T xFindViewById(Activity activity, int resourceId) {
        return (T) activity.findViewById(resourceId);
    }

    /**
     * 传入Object对象，TextView加载内容
     * @param view 控件
     * @param objContent 加载内容
     */
    public static void loadContent(View view, Object objContent) {
        if (view != null && objContent != null) {
            if (objContent instanceof Integer && (Integer) objContent != 0) {
                if (view instanceof Button) {
                    ((Button) view).setText((Integer) objContent);
                } else if (view instanceof TextView) {
                    ((TextView) view).setText((Integer) objContent);
                }
            } else if (objContent instanceof CharSequence) {
                if (view instanceof Button) {
                    ((Button) view).setText((CharSequence) objContent);
                } else if (view instanceof TextView) {
                    ((TextView) view).setText((CharSequence) objContent);
                }
            }
        }
    }

    public static String hideName(String name){

        if (name!=null){
            if (name.length()>0){
                String myname="*"+name.substring(1,name.length());
                return myname;
            }

        }

        return name;
    }
    /**
     * 根据社保卡隐藏显示
     * @param ss
     * @return
     */
    public static String hideSocialSecurity(String ss){

        if (ss != null){
            if (ss.length() == 9) {
                String data = ss.substring(0, 4) + "***" + ss.substring(7);
                return data;
            }

        }
        return ss;
    }
    /**
     * 根据身份证隐藏显示
     * @param idCard
     * @return
     */
    public static String hideIdCard(String idCard){

        if (idCard != null){
            if (idCard.length() == 18){
                String data = idCard.substring(0,6)+"**********"+idCard.substring(16);
                return data;
            }
        }
        return idCard;
    }

    /**
     * 隐藏手机号码
     * @param phone
     * @return
     */
    public static String hidePhone(String phone){

        if (phone != null){
            if (phone.length() == 11){
                String data = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
                return data;
            }
        }
        return phone;
    }

    /**
     * 隐藏银行卡号
     * @param bankCard
     * @return
     */
    public static String hideBankCard(String bankCard){

        if (bankCard != null){
            if (bankCard.length() >= 15){
                String data = bankCard.substring(0,6)+"******"+bankCard.substring(12);
                return data;
            }
        }
        return bankCard;
    }

    /**
     * 将Drawable StringArray转为资源ID数组
     * @param ctx 上下文
     * @param arrIconId 图标资源ArrayID
     * @return 资源ID数组
     */
    public static int[] getArrayDrawable(Context ctx, int arrIconId) {
        TypedArray ar = ctx.getResources().obtainTypedArray(arrIconId);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);
        }
        ar.recycle();

        return resIds;
    }


    /**
     * 初始化倒计时信息
     * @param ac 上下文
     * @param showTvView 显示的控件
     * @param showTipId 倒计时说明资源ID
     * @param countDownTime 倒计时总计时
     * @param pbCountdownBar 倒计时显示进度条
     */
    public static void initCountDownTip(@NonNull Activity ac, TextView showTvView, int showTipId, int countDownTime,
                                        ProgressBar pbCountdownBar) {
        try {
            if (pbCountdownBar != null) {
				pbCountdownBar.setMax(countDownTime);
			}
            if (!ac.isFinishing()) {
				String countdownTip = String.format(ac.getString(showTipId), countDownTime);
				Spanned result;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
					result = Html.fromHtml(countdownTip, Html.FROM_HTML_MODE_LEGACY);
				} else {
					result = Html.fromHtml(countdownTip);
				}
				showTvView.setText(result);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 倒计时显示
     * @param activity 上下文
     * @param showTipId 倒计时说明加载字段stringID
     * @param showView 显示的TextView控件
     * @param pbCountdownBar 进度条显示控件
     * @param countDownTime 倒计时时间
     * @param currentTime 当前时间
     */
    public static void countDownTimeTip(@NonNull Activity activity, int showTipId, TextView showView,
                                        ProgressBar pbCountdownBar, int countDownTime, int currentTime) {
        try {
            if (pbCountdownBar != null) {
				pbCountdownBar.setProgress(currentTime);
			}
            if (!activity.isFinishing()) {
				String countdownTip = String.format(activity.getString(showTipId), countDownTime);
				Spanned result;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
					result = Html.fromHtml(countdownTip, Html.FROM_HTML_MODE_LEGACY);
				} else {
					result = Html.fromHtml(countdownTip);
				}
				showView.setText(result);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态设置RadioButton
     */
    @SuppressWarnings("deprecation")
    public static void setDynamicRadioBtn(Context ctx, String[] arrs,
                                          RadioGroup rgGroup, int checkedBgResId, int checkedTvResId) {
        rgGroup.removeAllViews();
        for (int i = 0; i < arrs.length; i++) {
            RadioButton view = new RadioButton(ctx);
            view.setText(arrs[i]);
            view.setGravity(Gravity.CENTER);

            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            view.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            view.setId(i);
            android.widget.RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1);
             layoutParams.setMargins(5, 0, 5, 0);
            view.setLayoutParams(layoutParams);

            // 设置文字颜色选择器
            Resources resource = (Resources) ctx.getResources();
            ColorStateList csl = (ColorStateList) resource
                    .getColorStateList(checkedTvResId);
            if (csl != null) {
                view.setTextColor(csl);// 设置按钮文字颜色
            }
            view.setBackgroundResource(checkedBgResId);
            view.setPadding(15, 5, 15, 5);

            rgGroup.addView(view);
        }
    }

    /**
     * 判断edittext是否null
     */
    public static String checkEditText(EditText editText) {
        if (editText != null && editText.getText() != null
                && !(editText.getText().toString().trim().equals(""))) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

}
