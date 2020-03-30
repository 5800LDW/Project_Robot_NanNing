//package com.tecsun.lib_videoplayer2.base
//
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
//import com.tecsun.robot.nanning.util.log.LogUtil
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
//
//open class VideoBaseActivity : BaseActivity() {
//    private val TAG = VideoBaseActivity::class.java.simpleName
//    override fun finish() {
//        super.finish()
//        LogUtil.e(TAG,">>>>>>>>>>>>>>>>>> finish()")
//        myStopSpeak()
//        if (speechManager != null) {
//            speechManager.cancelSemanticRequest()
//        }
//    }
//
//    override fun onRobotServiceConnected() {
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onRobotServiceConnected()")
//        setSpeakManager()
//        speechManagerSleep()
//    }
//
//    private fun setSpeakManager() { //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(object : WakenListener {
//            override fun onWakeUp() {
//                LogUtil.i(TAG, "onWakeUp()")
//                speechManagerSleep()
//            }
//
//            override fun onSleep() {
//                LogUtil.i(TAG, "onSleep()")
//            }
//
//            override fun onWakeUpStatus(b: Boolean) {
//                LogUtil.i(TAG, "onWakeUpStatus = $b")
//            }
//        })
//
//        //监听到"返回"就自动结束当前Activity
//        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
//            override fun voiceRecognizeText(voiceTXT: String) {
//                if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_back))) {
//                    myFinish()
//                } else {
//                    speechManagerSleep()
//                    myStopSpeak()
//                    LogUtil.i(TAG, "voiceRecognizeText = $voiceTXT")
//                }
//            }
//        })
//    }
//}