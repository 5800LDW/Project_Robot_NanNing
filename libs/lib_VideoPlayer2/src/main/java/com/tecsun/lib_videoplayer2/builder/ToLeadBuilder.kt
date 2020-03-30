package com.tecsun.lib_videoplayer2.builder

import com.tecsun.lib_videoplayer2.util.FileUtil
import com.tecsun.lib_videoplayer2.util.FileUtil.FOLDER_PATH
import com.tecsun.robot.nanning.util.log.LogUtil
import java.io.File

object ToLeadBuilder {

    private val TAG = ToLeadBuilder::class.java.simpleName

    fun toLead() : ArrayList<File>{

        FileUtil.createFolder()

        var files = ArrayList<File>()
        try {
            FileUtil.getSuffixFile(files, FOLDER_PATH, ".mp4")
        } catch (e: Exception) {
            LogUtil.e(TAG, e)
        }

        val items = arrayOfNulls<String>(files.size)
        for (i in files.indices) {
            items[i] = files[i].name
//            LogUtil.e(TAG, "${files[i].name} =  ${files[i].absoluteFile}")
            LogUtil.e(TAG, "${files[i].name} =  ${files[i].absolutePath}")
        }

        return files

    }
}