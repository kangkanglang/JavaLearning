package com.harrison.fileUtil;

import com.harrison.Application;
import com.harrison.classLoader.MyClassLoader;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        if( file.getName().indexOf(".class") != -1){
            try {
                // 热部署
                Application.close();
                MyClassLoader myClassLoader = new MyClassLoader(Application.rootPath, Application.rootPath + "/com/harrison");
                Application.start0(myClassLoader);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

