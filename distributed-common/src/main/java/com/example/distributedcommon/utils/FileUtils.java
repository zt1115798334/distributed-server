package com.example.distributedcommon.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang
 * date: 2021/9/10 16:07
 * description:
 */
public class FileUtils {

    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        String contentType = new MimetypesFileTypeMap().getContentType(file);
        FileItem fileItem = diskFileItemFactory.createItem("file", contentType, false, file.getName());
        InputStream inputStream = new ByteArrayInputStream(FileCopyUtils.copyToByteArray(file));
        OutputStream outputStream = fileItem.getOutputStream();
        FileCopyUtils.copy(inputStream, outputStream);
        return new CommonsMultipartFile(fileItem);
    }

    /**
     * MultipartFile 转 File
     *
     * @param file file
     * @throws Exception 异常
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (file != null && file.getSize() > 0) {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
