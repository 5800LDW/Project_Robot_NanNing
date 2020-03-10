//package com.tecsun.robot.nanninig
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.KeyEvent
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import com.google.gson.Gson
//import com.sanbot.opensdk.beans.FuncConstant.HARDWARE_MANAGER
//import com.sanbot.opensdk.beans.Order
//import com.sanbot.opensdk.function.beans.FaceRecognizeBean
//import com.sanbot.opensdk.function.unit.HardWareManager
//import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener
//import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener
//import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener
//import com.tecsun.jc.base.common.BaseConstant
//import com.tecsun.robot.builder.ListeningAnimationBuilder
//import com.tecsun.robot.builder.WelcomeSpeakBuilder
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
//import com.tecsun.robot.nanning.util.ExitUtils.exit
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
//
///**
// * 主页
// *
// * @author liudongwen
// * @date 2020/02/27
// */
//class HomePageActivityTest : BaseActivity() {
//    private var ivListenButton: ImageView? = null
//    private var ivRobot: ImageView? = null
//    private var llListen: LinearLayout? = null
//    private var ll01: View? = null
//    private var ll02: View? = null
//    private var ll03: View? = null
//    private var ll04: View? = null
//    private val wsb = WelcomeSpeakBuilder(this)
//    private val lab = ListeningAnimationBuilder(this)
//    private var hardWareManager: HardWareManager? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.app_activity_home_page)
//        ivListenButton = findViewById(R.id.ivListenButton)
//        llListen = findViewById(R.id.llListen)
//        ivRobot = findViewById(R.id.ivRobot)
//        ll01 = findViewById(R.id.ll01)
//        ll02 = findViewById(R.id.ll02)
//        ll03 = findViewById(R.id.ll03)
//        ll04 = findViewById(R.id.ll04)
//        ll01!!.setOnClickListener(View.OnClickListener { v: View? -> myStartActivity(ConsultationActivity::class.java) })
//        ll02!!.setOnClickListener(View.OnClickListener { v: View? -> myStartActivity(Mainactivity::class.java) })
//        ll03!!.setOnClickListener(View.OnClickListener { v: View? ->
//            //            myStartActivity(PoliciesAndRegulationsActivity.class);
//            speechManager.cancelSemanticRequest()
//            val intent9 = Intent()
//            intent9.setClass(this, HtmlActivity::class.java)
//            intent9.putExtra("title", "政策法规")
//            intent9.putExtra("url", BaseConstant.URL_POLICIES_AND_REGULATIONS)
//            startActivity(intent9)
//        })
//        ll04!!.setOnClickListener(View.OnClickListener { v: View? -> myStartActivity(EntertainmentActivity::class.java) })
//        findViewById<View>(R.id.flBG).setOnClickListener { v: View? -> speechManagerWakeUp() }
//        hardWareManager = getUnitManager(HARDWARE_MANAGER) as HardWareManager?
//        setAll()
//        //        Glide.with(this).load(R.drawable.app_gif_listening).into(new GlideDrawableImageViewTarget(ivListenButton,10));
////        Glide.with(this).load(R.drawable.app_gif_listening).asGif().into(ivListenButton);
////
////        Glide.with(this)
////                .load(R.drawable.app_gif_listening)
////                .asGif()
////                .fitCenter()
////                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
////                .into(ivListenButton);
////        GifView gifView1 = (GifView)findViewById(R.id.gif1);
////        gifView1.setVisibility(View.VISIBLE);
////        gifView1.play();
////        gifView1.setGifResource(R.mipmap.app_gif_listening);
////        GifView gifView1 = (GifView)findViewById(R.id.gif1);
////        gifView1.setVisibility(View.VISIBLE);
////        gifView1.play();
////        gifView1.setGifResource(R.mipmap.app_gif_listening);
//
//
//
//
//
//    }
//
//    override fun onRobotServiceConnected() {
//        super.onRobotServiceConnected()
//        Log.e(TAG, "onRobotServiceConnected()")
//        setAll()
//        sendCommandToMainService(Order(4865, 1480, "你叫什么名字啊"), null)
//    }
//
//    private fun setAll() {
//        setSpeakManager()
//        setHardWareManager()
//    }
//
//    private fun setSpeakManager() { //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(object : WakenListener {
//            override fun onWakeUp() {
//                Log.e(TAG, "onWakeUp >>>>>>>>>>>>>>>>")
//                lab.onWakeup(ivListenButton, llListen, ivRobot)
//            }
//
//            override fun onSleep() {
//                lab.onSleep(ivListenButton, llListen, ivRobot)
//                Log.e(TAG, "onSleep >>>>>>>>>>>>>>>> ")
//            }
//
//            override fun onWakeUpStatus(b: Boolean) {
//                Log.e(TAG, "onWakeUpStatus >>>>>>>>>>>>>>>> = $b")
//            }
//        })
//        //下面是精简写法;
//        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
//            override fun voiceRecognizeText(text: String) {
//                skipNext(text)
//            }
//        })
//        //        //下面是完整写法和他们的推荐写法参考
////        //监听语音
////        speechManager.setOnSpeechListener(new RecognizeListener() {
////            //后台返回数据
////            @Override
////            public boolean onRecognizeResult(@NonNull Grammar grammar) {
//////                skipNext(grammar.getText());
////                //只有在配置了RECOGNIZE_MODE为1，且返回为true的情况下，才会拦截
////                Log.i(TAG, "onRecognizeResult: " + grammar.getText());
////
////                Log.i(TAG,
////                        "EGN_DUMI = " + grammar.EGN_DUMI + "\n" +
////                                "EGN_IFLYTEK = " + grammar.EGN_IFLYTEK + "\n" +
////                                "EGN_QAA = " + grammar.EGN_QAA + "\n" +
////                                "EGN_ZHIYIN = " + grammar.EGN_ZHIYIN + "\n" +
////
////                                "action = " + grammar.getAction() + "\n" +
////                                "engine = " + grammar.getEngine() + "\n" +
////                                "initialData = " + grammar.getInitialData() + "\n" +
////                                "isLast = " + grammar.isLast() + "\n" +
////                                "params = " + grammar.getParams() + "\n" +
////                                "route = " + grammar.getRoute() + "\n" +
////                                "text = " + grammar.getText() + "\n" +
////                                "topic = " + grammar.getTopic() + "\n");
////
////                if (grammar.getTopic().equals("test_topic")) {
//////                    speechManager.startSpeak("接收到了自定义语义");
////                    return true;
////                }
////                return true;
////            }
////
////            //识别用户语音
////            @Override
////            public void onRecognizeText(@NonNull RecognizeTextBean recognizeTextBean) {
////                if (recognizeTextBean != null && !TextUtils.isEmpty(recognizeTextBean.getText())) {
//////                    wsb.setStateWorking();
////                    skipNext(recognizeTextBean.getText());
////                }
////                Log.i(TAG, "tvSpeechRecognizeResult: " + recognizeTextBean.getText());
////            }
////
////            @Override
////            public void onRecognizeVolume(int i) {
////                Log.i(TAG, "onRecognizeVolume: " + String.valueOf(i));
////            }
////
////            @Override
////            public void onStartRecognize() {
////                Log.i(TAG, "onStartRecognize: ");
////            }
////
////            @Override
////            public void onStopRecognize() {
////                //TODO 停止识别语音的时候的逻辑处理;
////                Log.i(TAG, "onStopRecognize: ");
////            }
////
////            @Override
////            public void onError(int i, int i1) {
////                Log.i(TAG, "onError: i=" + i + " i1=" + i1);
////            }
////        });
//    }
//
//    private fun setHardWareManager() {
//        if (hardWareManager != null) { //人脸识别回调
//            hdCameraManager.setMediaListener(object : FaceRecognizeListener {
//                override fun recognizeResult(list: List<FaceRecognizeBean>) {
//                    val sb = StringBuilder()
//                    for (bean in list) {
//                        sb.append(Gson().toJson(bean))
//                        sb.append("\n")
//                        if (wsb.currentState == WelcomeSpeakBuilder.STATE_FREE) {
//                            wsb.delaySpeak(bean) { speechManagerWakeUp() }
//                        }
//                    }
//                    Log.e(TAG, "人脸识别回调 >>>>>>>>>>>>>>>>$sb")
//                }
//            })
//            //红外
//            hardWareManager!!.setOnHareWareListener(object : PIRListener {
//                override fun onPIRCheckResult(isCheck: Boolean, part: Int) { //                Log.e("TAGTAG", (part == 1 ? "正面" : "背后") + "PIR被触发");
//                }
//            })
//            //红外距离
//            hardWareManager!!.setOnHareWareListener(object : InfrareListener {
//                override fun infrareDistance(part: Int, distance: Int) {
//                    if (distance != 0) { //                    System.out.print("部位" + part + "的距离为" + distance);
////                        Log.e("TAGTAG", "部位" + part + "的距离为" + distance);
//                    }
//                }
//            })
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.e(TAG, "onResume()")
//    }
//
//    private var exitTime: Long = 0
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - exitTime > 2000) {
//                showToast(getString(R.string.app_drop_out))
//                exitTime = System.currentTimeMillis()
//            } else {
//                finish()
//            }
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        exit()
//    }
//
//    private fun skipNext(voiceTXT: String?) {
//        wsb.setStateWorking()
//        if (voiceTXT != null) {
//            if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_function1_keyword))) {
//                ll01!!.performClick()
//            } else if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_function2_keyword))) {
//                ll02!!.performClick()
//            } else if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_function3_keyword))) {
//                ll03!!.performClick()
//            } else if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_function4_keyword))) {
//                ll04!!.performClick()
//            }
//        }
//    }
//
//    companion object {
//        private val TAG = HomePageActivityTest::class.java.simpleName
//    }
//
//    open fun sendQuestion(type: Int, msg: String) {
//        //给主控发消息
//        LogUtils.Logd(Constants.SANBAO, "sendQuestion:$type,msg=$msg")
//        if (TextUtils.isEmpty(msg)) {
//            return
//        }
//        //1480发文字，1481发舞蹈动作
//        if (BuildConfig.FLAVOR.contains(Constants.ZHIYIN_DESK)) {//桌面机器人
//            sendCommandToMainService(Order(4865, 1480, msg), null)
//            LogUtils.Logd(Constants.SANBAO, "desk sendQuestion:$type,msg=$msg")
//        } else if (!MyApplication.sIsJinGangRobot) {//B款
//            sendCommandToMainService(Order(type, 0, msg), null)
//            LogUtils.Logd(Constants.SANBAO, "AB sendQuestion:$type,msg=$msg")
//        } else {//D款
//            val order = Order(type, type, msg)
//            if (mHardWareManager == null) {
//                mHardWareManager = getUnitManager(FuncConstant.HARDWARE_MANAGER) as HardWareManager?
//            }
//            mHardWareManager!!.sendRawData(order)
//            LogUtils.Logd(Constants.SANBAO, "D sendQuestion:$type,msg=$msg")
//        }
//    }
//}