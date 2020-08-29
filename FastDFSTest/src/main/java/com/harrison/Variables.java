package com.harrison;

import java.net.InetSocketAddress;

/**
 * @author: harrison
 * @description: 定义常用的变量
 **/
public class Variables {

    //安装fastdfs的虚拟机的ip
    public static final String ip_home = "192.168.125.132";

    //默认文件本地文件路径
    public static final String pathname = "D:\\FastDFS.jpg";

    //组名，跟你在fastdfs配置文件中的一致
    public static final String groupName = "group1";

    //fastDFS存储文件的path，这个path路径需要你执行测试方法中上传后，获取的path粘贴过来，用于查询、删除的
    public static final String path = "M00/00/00/wKh9hV85KbCANh0VAADPWXht_to590.jpg";

    //文件下载地址 文件名可以重命名
    public static final String filename = "D:\\FastDFS_Download.jpg";

    //默认文件格式，后缀名,设置上传后在fastdfs存储的格式，你可以改成其它格式图片，fastdfs只支持几种常用格式的，自己百度可以查查，jpg和png都是可以的
    public static final String fileExtName = "jpg";

    //带组名的path
    public static final String filePath = groupName + path;

    //public final static int port = 22122;

    //public final static int store_port = 23000;

    //public static InetSocketAddress address = new InetSocketAddress(ip_home, port);

    //public static InetSocketAddress store_address = new InetSocketAddress(ip_home, store_port);

    //超时时间
    //public static final int soTimeout = 550;

    //连接时间
    //public static final int connectTimeout = 500;


}
