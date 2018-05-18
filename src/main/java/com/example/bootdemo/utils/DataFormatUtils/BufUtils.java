package com.example.bootdemo.utils.DataFormatUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufUtils {
    // 写入字符串
    public static void writeString(ByteBuf bf, int length, String target) {
        // 准备一个临时的ByteBuf，处理字符串型的写入
        ByteBuf temp = Unpooled.buffer(length);
        // 将字符串写入临时ByteBuf中
        temp.writeBytes(target.getBytes());
        // 再写入目标ByteBuf中
        bf.writeBytes(temp, 0, length);
    }

    // 写入无符号int型
    public static void writeUnsignInt(ByteBuf bf, long value) {
        bf.writeInt((int) (value & 0xffffffff));
    }
}
