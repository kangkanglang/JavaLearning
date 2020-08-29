package com.harrison.classLoader;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author harrison
 * 自定义ClassLoader
 * 作用：将自己指定目录下  更新的class文件  动态加载到JVM中
 */
public class MyClassLoader extends ClassLoader {

    // 项目根目录
    public String rootPath;

    // 需要热加载的 class 记录， 因为有些类是不需要我们加载的  比如 String
    public List<String> clazzs;

    /**
     * 传入指定目录  热加载class文件
     * @param rootPath 项目的根目录，需要根据此目录来截取class文件路径  因为加载的是class问价， 其目录为： D://projects//com/xx/X.class
     *                 但是classLoader需要根据ClassName加载，而className的格式为 com.xx.X,所以需要根据路径截取到com这一截
     * @param clazzPaths 需要热加载的项目
     * @throws Exception
     */
    public MyClassLoader(String rootPath, String... clazzPaths) throws Exception{
        this.rootPath = rootPath;
        this.clazzs = new ArrayList<>();
        for(String clazzPath : clazzPaths){
            LoadClassPath(new File(clazzPath));
        }
    }

    /**
     * 根据目录扫描项目里的class文件 并把文件加载进JVM
     * defineClass() 此方法为ClassLoader的方法
     * 此方法传入一个className 与 byte数组(byte数组是对应Class文件的二进制数据数组) 来将对应的Class文件加载进JVM， 并生成Class对象
     * @param file 出入扫描的目录
     * @throws Exception
     */
    private void LoadClassPath(File file) throws Exception{
        if(file.isDirectory()){
            for(File f : file.listFiles()){
                LoadClassPath(f);
            }
        }else {
            String fileName = file.getName();
            String filePath = file.getPath();
            String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if("class".equals(endName)){
                InputStream in = new FileInputStream(file);
                byte[] bytes =new byte[(int)file.length()];
                in.read(bytes);
                String className = filePathTOClassName(filePath);
                clazzs.add(className);
                // 将class文件生成class对象
                defineClass(className, bytes, 0, bytes.length);
            }
        }
    }

    /**
     * 将文件路径替换为ClassName
     * @param filePath 文件路径
     * @return
     */
    private String filePathTOClassName(String filePath) {
        String className = filePath.replace(rootPath, "").replaceAll("\\\\", ".");
        className = className.substring(0, className.lastIndexOf("."));
        className = className.substring(1);
        return className;
    }

}
