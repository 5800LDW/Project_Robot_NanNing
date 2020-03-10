package com.tecsun.robot.dance;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.xukefeng.musicplayer.PackageClass.MusicInfo;
import com.sanbot.dance_play.DanceBean;
import com.sanbot.dance_play.DanceManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.MainApp;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

/*
     自定义音乐播放器
     1 从存储卡内部读取所有音乐列表，并显示相关音乐信息
 */
public class DanceMusicListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    //用于将mCursor的数据导入到List对象中，再作为Adapater参数传入
    private List<Map<String , String>> List_map  ;
    //指定布局文件的ListView
    private ListView MusicListView ;
    //申明ContentResolver对象，用于访问系统数据库
    private ContentResolver contentResolver ;
    //用于装载MusicInfo对象
    private List<MusicInfo> musicInfos ;
    //指定SimpleAdapter对象
    private SimpleAdapter simpleAdapter ;
    //权限申请码requestCode
    private final static int STORGE_REQUEST = 1 ;
    //是否处于播放状态
    private boolean isPlyer = false ;
    //用于启动服务的Intent
    private Intent intent ;
    List<String> title = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.xukefeng.musicplayer.R.layout.activity_musiclist_main);
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
            }

            @Override
            public void onSleep() {
                speechManager.doWakeUp();
            }

            @Override
            public void onWakeUpStatus(boolean b) {
            }
        });

        //语音监听结果回调
        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String voiceTXT) {

                //语音监听返回
                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(com.example.xukefeng.musicplayer.R.array.app_arr_back))) {
                    myFinish();
                }else if(PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(com.example.xukefeng.musicplayer.R.array.app_arr_backromain))){
                    myFinish();
                }
                else {
                    int i=0;
                    for(; i<title.size(); i++){
                        if(voiceTXT.contains(title.get(i))){
                            break;
                        }
                    }
                    if(i<title.size()){
                        int finalI = i;
                        speakAndCheckComplete("好的，我要跳舞了，请保证我身边一米范围内没有物体，以免发生碰撞",()->{
                            //将点击位置传递给播放界面，在播放界面获取相应的音乐信息再播放。
                            Bundle bundle = new Bundle() ;
                            bundle.putInt("position" , finalI);
                            Intent intent = new Intent() ;
                            //绑定需要传递的参数
                            intent.putExtras(bundle) ;
                            intent.setClass(DanceMusicListActivity.this ,DanceActivity.class );
                            speechManager.cancelSemanticRequest();
                            speechManager.stopSpeak();
                            startActivityForResult(intent, 0xff);
                        });
                    }
                }

            }
        });

        MusicListView = findViewById(com.example.xukefeng.musicplayer.R.id.MusicListView) ;
        //首先检查自身是否已经拥有相关权限，拥有则不再重复申请
        int check = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ;
            //没有相关权限
            if (check != PackageManager.PERMISSION_GRANTED)
            {
                //申请权限
                ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE} ,STORGE_REQUEST);
            }else {
            //已有权限的情况下可以直接初始化程序
            init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //程序退出时，终止服务
//        stopService(intent) ;
        faceTrackManager.switchFaceTrack(false);
    }

    /*
        界面列表初始化
         */
    private void init()
    {
        DanceManager danceManager =  MainApp.getInstance().getManager();
        List<DanceBean> list = danceManager.getDanceList(this);
        title.clear();
        List_map = new ArrayList<Map<String, String>>() ;
        if(List_map.size()==0){
            return;
        }
        for (int i = 0 ; i < list.size() ; i++)
        {
            title.add((i+1)+"");
            Map<String , String> map = new HashMap<>() ;
            map.put("index",(i+1)+" "+ list.get(i).getName());
            List_map.add(map);
        }

        //SimpleAdapter实例化
        simpleAdapter = new SimpleAdapter(this , List_map , com.example.xukefeng.musicplayer.R.layout.dance_adapter_view ,
                new String[] {"index"} ,new int[]{com.example.xukefeng.musicplayer.R.id.tv_index}) ;
        //为ListView对象指定adapter
        MusicListView.setAdapter(simpleAdapter);
        //绑定item点击事件
        MusicListView.setOnItemClickListener(this);
    }

    @Override
    protected void onMainServiceConnected() {
        super.onMainServiceConnected();
        if(List_map.size()==0){
            speak("没有搜索到舞蹈，请先通过舞蹈编辑程序添加舞蹈");
        }
    }

    /*
    申请权限处理结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         switch (requestCode)
         {
             case STORGE_REQUEST :
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                 {
                     //完成程序的初始化
                     init();
                     System.out.println("程序申请权限成功，完成初始化") ;
                 }
                 else {
                     System.out.println("程序没有获得相关权限，请处理");
                 }
                 break ;
         }

    }

    /*
       item点击实现
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        String MusicData = musicInfos.get(position).getData();
//        System.out.println("THE MUSIC DATA IS " + MusicData);

                //将点击位置传递给播放界面，在播放界面获取相应的音乐信息再播放。
        Bundle bundle = new Bundle() ;
        bundle.putInt("position" , position);
//        bundle.putSerializable("musicinfo" , (Serializable) getMusicInfos());
        Intent intent = new Intent() ;
        //绑定需要传递的参数
        intent.putExtras(bundle) ;
        intent.setClass(this ,DanceActivity.class );
        speechManager.cancelSemanticRequest();
        speechManager.stopSpeak();
        startActivityForResult(intent, 0xff);
    }
    //播放Activity调用方法来获取MusicMediainfo数据
    public List<MusicInfo> getMusicInfos()
    {
        return musicInfos ;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0xff){
            if(resultCode == 0x00){       //语音返回主界面
                myFinish();
            }else {     //按钮返回音乐列表

            }
        }
    }
}
