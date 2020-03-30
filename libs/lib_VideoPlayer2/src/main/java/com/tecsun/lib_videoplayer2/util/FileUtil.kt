package com.tecsun.lib_videoplayer2.util

import android.os.Environment
import com.tecsun.robot.nanning.util.log.LogUtil
import java.io.File
import java.io.IOException


object FileUtil {
    private val TAG = FileUtil::class.java.simpleName

    var FOLDER_NAME = "Documents"

    var FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + FOLDER_NAME

    fun createFile(): Boolean {
        val file = File(FOLDER_PATH)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                LogUtil.e(TAG, e)
            }
        }
        return file.exists()
    }

    fun createFolder(): Boolean {
        val dirFolder = File(FOLDER_PATH)
        if (!dirFolder.exists()) {
            try {
                dirFolder.mkdirs()
            } catch (e: Exception) {
                LogUtil.e(TAG, e)
            }
        }
        return dirFolder.exists()
    }

    /**
     * 读取sd卡上指定后缀的所有文件,包括此路径下的其他文件夹里面的指定后缀文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffix   后缀名称 比如 .gif
     * @return
     */
    fun getSuffixFile(files: ArrayList<File>, filePath: String?, suffix: String?): List<File?>? {
        val f = File(filePath)
        if (!f.exists()) {
            return null
        }
        //        f.listFiles()
        val subFiles = f.listFiles()
        if (subFiles != null) {
            for (subFile in subFiles) {
                if (subFile.isFile && subFile.name.endsWith(suffix!!)) { // ToastUtil.showToast(mContext,subFile.getName());
                    files.add(subFile)
                } else if (subFile.isDirectory) {
                    getSuffixFile(files, subFile.absolutePath, suffix)
                } else { //非指定目录文件 不做处理
                }
            }
        }
        return files
    }

    fun getName(file: File):String{
        return file.name.substring(0, file.name.lastIndexOf("."))
    }


}