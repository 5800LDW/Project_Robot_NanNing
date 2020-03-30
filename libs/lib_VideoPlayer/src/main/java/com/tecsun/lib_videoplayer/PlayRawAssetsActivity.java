package com.tecsun.lib_videoplayer;


import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Environment;
import android.view.View;

import com.dueeeke.videocontroller.StandardVideoController;
import com.tecsun.robot.nanning.util.PermissionsUtils;
import com.tecsun.robot.nanning.util.log.LogUtil;

import java.io.IOException;

/**
 * 播放raw/assets视频
 */

public class PlayRawAssetsActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_play_raw_assets;
    }

    @Override
    protected int getTitleResId() {
        return R.string.str_raw_or_assets;
    }


    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.player);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(getString(R.string.str_raw_or_assets), false);
        mVideoView.setVideoController(controller);

        boolean hadPermission = PermissionsUtils.startRequestPermission(this, permissions,101);
    }

    public void onButtonClick(View view) {
        mVideoView.release();
        Object playerFactory = Utils.getCurrentPlayerFactory();

        if (view.getId() == R.id.btn_raw) {
//            if (playerFactory instanceof ExoMediaPlayerFactory) { //ExoPlayer
//                DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.movie));
//                RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
//                try {
//                    rawResourceDataSource.open(dataSpec);
//                } catch (RawResourceDataSource.RawResourceDataSourceException e) {
//                    e.printStackTrace();
//                }
//                String url = rawResourceDataSource.getUri().toString();
//                mVideoView.setUrl(url);
//            } else { //MediaPlayer,IjkPlayer
//            String url = "android.resource://" + getPackageName() + "/" + R.raw.qxcj;///sdcard/Documents
            String url = "file://"+ Environment.getExternalStorageDirectory().getPath() + "/Documents/shebao.mp4";
//            String url = "file://"+ "/sdcard/Documents"+ "/shebaoCx.mp4";
            LogUtil.e("TAG",url);
            mVideoView.setUrl(url);
//            }
        }
        if (view.getId() == R.id.btn_assets) {

//                if (playerFactory instanceof ExoMediaPlayerFactory) { //ExoPlayer
//                    mVideoView.setUrl("file:///android_asset/" + "test.mp4");
//                } else { //MediaPlayer,IjkPlayer
            AssetManager am = getResources().getAssets();
            AssetFileDescriptor afd = null;
            try {
                afd = am.openFd("test.mp4");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mVideoView.setAssetFileDescriptor(afd);
//                }
        }

        mVideoView.start();
    }
}
