/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dev.SerialApi;


//import com.dev.idcard.IDCardByte;

import android.util.Log;

public class SerialApi
{

    public native static int open(String path, int baudRate);

    public native static int op(int sn, int bi);

    public native static int close(int fd);

    public native static void flush(int fd);

    public native static int read(int fd, byte[] buf, int offset, int len);
    public native static int readBlock(int fd, byte[] buf, int offset, int len, int timems);

    public native static void write(int fd, byte[] buf, int offset, int len);

    // @return - the serial length, should always equals to 8
    public native static int readDevSerial(int fd, byte[] buf, int offset, int timems);


    public native static int startFindIDCard(int fd, int timems);
    public native static int selectIDCard(int fd, int timems);
    public native static int readBaseMsg(int fd, byte[] buf, int offset, int timems);
    public native static int readBaseMsgWithFinger(int fd, byte[] buf, int offset, int timems);

    public native static int cp_idcard_start(int fd, int timems);
    public native static int cp_idcard_select(int fd, int timems);
//    public native static IDCardByte cp_idcard_readMsg(int fd, int timems);

    public native static int IccReaderPowerOn(int fd, int slot, byte[] buf, int timems);
    public native static boolean IccReaderPowerOff(int fd, int slot);
    public native static boolean IccCheckSlot(int fd);

    //@param slot- 0:ICC1, 1:SAM1, 2:SAM2
    public native static boolean IccSelectSlot(int fd, int slot);

    public native static int IccReaderApplication(int fd, int slot, byte[] wbuf, int len, byte[] rbuf, int timems);

    public native static void send_cmd_mag(int fd);
    public native static boolean read_mag_data(int fd,byte[] t1, byte[] t2, byte[] t3, int[] len);

    public native static boolean MagGetTrackData(int fd, byte[] t1, byte[] t2, byte[] t3, int[] len, int timems);

    public native static boolean GetMagCardStatus(int fd);

    public native static boolean ReaderGiveUpAction(int fd);

    public native static int DecodeIdBitmap(String p, String w, String b);

    public native static int getSsseCardNum(int fd, byte[] buf);

    public native static int getSsseIdNumAndName(int fd, byte[] bufId, byte[] bufName, int[] bufLen);

    public native static int readUntilNoReply(int fd, byte[] buf, int offset, int maxLength, int timeout, int interval);

    static {
//        System.loadLibrary("tools_v2.1");
//        System.loadLibrary("dex");
        try {
            System.loadLibrary("cp");
            System.loadLibrary("DecodeWlt");
        }catch (UnsatisfiedLinkError e){
            e.getStackTrace();
            Log.e("so包加载错误=====","so包加载错误====="+e.getMessage());
        }

    }

}
