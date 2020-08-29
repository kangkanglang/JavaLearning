package com.harrison;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastDFSTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSTest.class);

    File file = null;
    Set<MateData> metaDataSet = null;

    @Before
    public void newFile() {
        metaDataSet = createMetaData();
        file = new File(Variables.pathname);
    }

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * test 1 -- 图片上传
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        //上传图片
        StorePath storePath = this.storageClient.uploadFile(new FileInputStream(file), file.length(), Variables.fileExtName, metaDataSet);
        printlnPath(storePath);
    }

    /**
     * test 2 -- 图片上传缩略图
     */
    @Test
    public void testCrtThumbImage() throws FileNotFoundException {
        //上传图片的缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(new FileInputStream(file), file.length(), Variables.fileExtName, metaDataSet);
        String fullPath = thumbImageConfig.getThumbImagePath(storePath.getFullPath());
        System.out.println("【图片缩略图带有组名的路径】:" + fullPath);
        printlnPath(storePath);
    }

    /**
     * 查询
     */
    @Test
    public void testQuery() {
        FileInfo fileInfo = this.storageClient.queryFileInfo(Variables.groupName, Variables.path);
        System.out.println("图片信息如下：\n" + fileInfo.getCrc32() + "\n" + new Date(fileInfo.getCreateTime()) + "\n" + fileInfo.getFileSize() + "\n" + fileInfo.getSourceIpAddr());
    }

    /**
     * 删除
     */
    @Test
    public void testDel() {
        this.storageClient.deleteFile(Variables.filePath);
    }


    /**
     * 删除(效果同上删除)
     */
    @Test
    public void testDel2() {
        this.storageClient.deleteFile(Variables.groupName, Variables.path);
    }


    /**
     * 下载文件
     */
    @Test
    public void downLoadFile() {
        DownloadFileWriter callback = new DownloadFileWriter(Variables.filename);
        this.storageClient.downloadFile(Variables.groupName, Variables.path, callback);
        // StorageDownloadCommand<String> stringStorageDownloadCommand = new StorageDownloadCommand<>(variables.groupName, variables.path, callback);
        // String fileName = commandTestBase.executeStoreCmd(stringStorageDownloadCommand);
    }

    /**
     * 创建元信息
     *
     * @return
     */
    private Set<MateData> createMetaData() {
        Set<MateData> metaDataSet = new HashSet<MateData>();
        metaDataSet.add(new MateData("Author", "harrison"));
        metaDataSet.add(new MateData("CreateDate", "2020.08.15"));
        return metaDataSet;
    }


    private void printlnPath(StorePath storePath) {
        //组名
        System.out.println("【组名】:" + storePath.getGroup());
        //带组名的文件地址
        System.out.println("【带组名的文件地址】:" + storePath.getFullPath());
        //不带组名的文件地址
        System.out.println("【不带组名的文件地址】:" + storePath.getPath());
    }

}
