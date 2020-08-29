package com.harrison;


import com.harrison.classLoader.MyClassLoader;
import com.harrison.fileUtil.FileListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class Application extends FileAlterationListenerAdaptor {

    public static Application application;

    // 需要热加载类的根目录
    public static String rootPath;

    public Application(){
        Application.application = this;
    }

    public void start() throws Exception{
        //类似于SpringBoot启动流程。。。
        init();
        // logic code
        new User().sayHello();
    }

    public void init(){
        System.out.println("初始化项目。");
    }

    public static void run(Class<?> clazz) throws Exception{
        String rootPath = clazz.getResource("/").getPath().replaceAll("%20", " ");
        rootPath = new File(rootPath).getPath();
        Application.rootPath = rootPath;
        startFileMoni(rootPath);
        MyClassLoader myClassLoader = new MyClassLoader(rootPath, rootPath + "/com/harrison");
        start0(myClassLoader);
    }

    /**
     * Application 由自定义的类加载加载，因此在Application中实例化的对象都会优先选择自定义的类加载器
     * @param myClassLoader 自定义类加载器
     * @throws Exception
     */
    public static void start0(MyClassLoader myClassLoader) throws Exception{
        // loadClass(String name) 参数name是class的全限定名
        Class<?> aClass = myClassLoader.loadClass("com.harrison.Application");
        Object o = aClass.newInstance();
        aClass.getMethod("start").invoke(o);
    }

    public static void close(){
        System.out.println("关闭项目");
        //通知JVM销毁已失去引用的对象(执行finalize()方法)
        System.runFinalization();
        //通知JVM GC
        System.gc();
    }

    /**
     *  启动文件监听器
     * @param rootPath
     * @throws Exception
     */
    public static void startFileMoni(String rootPath) throws Exception{
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(rootPath);
        fileAlterationObserver.addListener(new FileListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }
}
