package com.tecsun.robot.nanning.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.View
import com.tecsun.robot.nanning.builder.BaseBuilder
import com.tecsun.robot.nanning.builder.BuilderLifeCycleObserver
import com.tecsun.robot.nanning.builder.net.monitor.NetChangeObserver
import com.tecsun.robot.nanning.builder.net.monitor.NetUtils
import com.tecsun.robot.nanning.builder.net.monitor.NetworkUtil
import com.tecsun.robot.nanning.lib_base.BaseActivity
import com.tecsun.robot.nanning.pinyin.InitContentProvider
import com.tecsun.robot.nanning.util.PreferenceUtil
import com.tecsun.robot.nanning.util.log.LogUtil
import com.tecsun.robot.nanning.util.snackbar.SnackBarBuilder
import java.util.*

/**
 * 网络监听者
 * @author liudongwen
 * @date 2020/03/09
 */
class NetWorkBuilder(private val baseActivity: BaseActivity?, private var locationView: View?, private var netObserver: NetChangeObserver?) : BaseBuilder() {



    private val  NET_STATE:String = "_NET_STATE"
    private val  NET_STATE_SHOW:String = "NET_STATE_SHOW"
    private val  NET_STATE_HIDE:String = "NET_STATE_HIDE"
    private var snackBarBuilder: SnackBarBuilder? = null

    companion object {
        private val TAG = NetWorkBuilder::class.java.simpleName
    }

    init {
        baseActivity?.lifecycle?.addObserver(BuilderLifeCycleObserver(this))
    }

    override fun onCreate() {

        if (netObserver == null) {
            //默认监听
            netObserver = object : NetChangeObserver {
                override fun onNetConnected(type: NetUtils.NetType?) {
                    LogUtil.i(TAG, ">>>>>>>>>>>>>onNetConnected()")
                    LogUtil.i(TAG, ">>>>>>>>>>>>>type = " + type)
                    baseActivity?.runOnUiThread {
                        hideBiz();
                        PreferenceUtil.set(baseActivity,NET_STATE,NET_STATE_HIDE)
                    }

                }

                override fun onNetDisConnect() {
                    LogUtil.i(TAG, ">>>>>>>>>>>>>onNetDisConnect()")
                    baseActivity?.runOnUiThread {
                        showBiz()
                        PreferenceUtil.set(baseActivity,NET_STATE,NET_STATE_SHOW)
                    }
                }
            }
        }

        //开启网络广播监听
        NetStateReceiver.registerNetworkStateReceiver(baseActivity)

        monitorNetWork()
    }

    private fun hideBiz(){
        if (snackBarBuilder == null) {
            snackBarBuilder = SnackBarBuilder()
        }
        snackBarBuilder?.hide(baseActivity)
    }
    private fun showBiz(){
        if (snackBarBuilder == null) {
            snackBarBuilder = SnackBarBuilder()
        }
        snackBarBuilder?.show(locationView, baseActivity!!)
    }


    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStart() {

//        if(PreferenceUtil.get(InitContentProvider.getStaticContext(),NET_STATE,"").equals(NET_STATE_SHOW)){
//            baseActivity?.runOnUiThread {
//                showBiz()
//            }
//        }
//        else{
//            baseActivity?.runOnUiThread {
//                hideBiz()
//            }
//        }



//        var b = NetworkUtil.isConn(InitContentProvider.getStaticContext());
//        if(!b){
//            baseActivity?.runOnUiThread {
//                showBiz()
//            }
//        }
//        else{
//            baseActivity?.runOnUiThread {
//                hideBiz()
//            }
//        }


        var b = NetworkUtil.isConn(InitContentProvider.getStaticContext());
        if(!b){
            baseActivity?.runOnUiThread {
                netObserver?.onNetDisConnect();
            }
        }
        else{
            baseActivity?.runOnUiThread {
                netObserver?.onNetConnected(NetUtils.NetType.NONE);
            }
        }
        LogUtil.e(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>>> b = " + b)

    }

    override fun onDestroy() {
        super.onDestroy()
        NetStateReceiver.unRegisterNetworkStateReceiver(baseActivity!!)
        NetStateReceiver.removeRegisterObserver(netObserver!!)

    }

    private fun monitorNetWork() {
        NetStateReceiver.registerObserver(netObserver!!)
    }


}

class NetStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        mBroadcastReceiver = this@NetStateReceiver
        if (intent.action!!.equals(
                        ANDROID_NET_CHANGE_ACTION,
                        ignoreCase = true
                ) || intent.action!!.equals(
                        CUSTOM_ANDROID_NET_CHANGE_ACTION,
                        ignoreCase = true
                )
        ) {
            if (!NetUtils.isNetworkAvailable(context)) {
                Log.e(this.javaClass.name, "<--- network disconnected --->")
                isNetworkAvailable = false
            } else {
                Log.e(this.javaClass.name, "<--- network connected --->")
                isNetworkAvailable = true
                apnType = NetUtils.getAPNType(context)
            }
            notifyObserver()
        }
    }

    private fun notifyObserver() {
        if (!mNetChangeObservers!!.isEmpty()) {
            val size = mNetChangeObservers!!.size
            for (i in 0 until size) {
                val observer = mNetChangeObservers!![i]
                if (observer != null) {
                    if (isNetworkAvailable) {
                        observer.onNetConnected(apnType)
                    } else {
                        observer.onNetDisConnect()
                    }
                }
            }
        }
    }


    companion object {

        val CUSTOM_ANDROID_NET_CHANGE_ACTION = "com.zhanyun.api.netstatus.CONNECTIVITY_CHANGE"
        private val ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
        private val TAG = NetStateReceiver::class.java.simpleName

        var isNetworkAvailable = false
            private set
        var apnType: NetUtils.NetType? = null
            private set
        private var mNetChangeObservers: ArrayList<NetChangeObserver>? = ArrayList()
        private var mBroadcastReceiver: BroadcastReceiver? = null

        private val receiver: BroadcastReceiver
            get() {
                if (null == mBroadcastReceiver) {
                    synchronized(NetStateReceiver::class.java) {
                        if (null == mBroadcastReceiver) {
                            mBroadcastReceiver = NetStateReceiver()
                        }
                    }
                }
                return mBroadcastReceiver!!
            }

        /**
         * 注册
         *
         * @param mContext
         */
        fun registerNetworkStateReceiver(mContext: Context?) {
            if (mContext == null) {
                return
            }

//            //动态注册网络变化广播
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                //实例化IntentFilter对象
//                val filter = IntentFilter()
//                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
//                val netBroadcastReceiver = NetStateReceiver()
//                //注册广播接收
//                mContext.registerReceiver(netBroadcastReceiver, filter)
//            }


            val filter = IntentFilter()
            filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION)
            filter.addAction(ANDROID_NET_CHANGE_ACTION)
            mContext.applicationContext.registerReceiver(receiver, filter)
        }

        //    /**
        //     * 清除
        //     *
        //     * @param mContext
        //     */
        //    public static void checkNetworkState(Context mContext) {
        //        Intent intent = new Intent();
        //        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        //        mContext.sendBroadcast(intent);
        //    }

        /**
         * 反注册
         *
         * @param mContext
         */
        fun unRegisterNetworkStateReceiver(mContext: Context) {
            if (mBroadcastReceiver != null) {
                try {
                    mContext.applicationContext.unregisterReceiver(mBroadcastReceiver)
                } catch (e: Exception) {

                }

            }

        }

        /**
         * 添加网络监听
         *
         * @param observer
         */
        fun registerObserver(observer: NetChangeObserver) {
            if (mNetChangeObservers == null) {
                mNetChangeObservers = ArrayList()
            }
            mNetChangeObservers!!.add(observer)
        }

        /**
         * 移除网络监听
         *
         * @param observer
         */
        fun removeRegisterObserver(observer: NetChangeObserver) {
            if (mNetChangeObservers != null) {
                if (mNetChangeObservers!!.contains(observer)) {
                    mNetChangeObservers!!.remove(observer)
                }
            }
        }
    }
}