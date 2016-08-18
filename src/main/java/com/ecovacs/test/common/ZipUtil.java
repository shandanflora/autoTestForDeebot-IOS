package com.ecovacs.test.common;

import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by ecosqa on 16/8/18.
 * utility of zip
 */
public class ZipUtil {

    /**
     * recursion compress folder
     * @param srcRootDir path of source file
     * @param file destination file or folder to compress
     * @param zos ZipOutputStream
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception
    {
        if (file == null)
        {
            return;
        }

        //if is file，compress file directly
        if (file.isFile())
        {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            //get absolute path to destination folder
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1)
            {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1)
            {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        }
        //if is folder，compress all folder
        else
        {
            //compress file or subfolder in destination folder
                File[] childFileList = file.listFiles();
                if(childFileList != null){
                    for(File childFile:childFileList)
                    {
                        //childFile.getAbsolutePath().indexOf(file.getAbsolutePath());
                        zip(srcRootDir, childFile, zos);
                    }
                }
        }
    }

    /**
     * compress for file or folder
     * @param srcPath source path. if compress file, is absolute path; if compress folder, is top folder path
     * @param zipPath destination path. note: srcPath is not contain zipPath
     * @param zipFileName destination file name
     */
    public static boolean zip(String srcPath, String zipPath, String zipFileName) throws Exception
    {
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName))
        {
            throw new ParameterException("Input parameter is null!!!");
        }
        CheckedOutputStream cos;
        ZipOutputStream zos = null;
        try
        {
            File srcFile = new File(srcPath);

            //if sub folder of source folder, throw exception(prevent endless loop)
            if (srcFile.isDirectory() && zipPath.contains(srcPath))
            {
                throw new ParameterException("zipPath must not be the child directory of srcPath.");
            }

            //if not exist folder, make directory
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory())
            {
                if(!zipDir.mkdirs()){
                    return false;
                }
            }

            //create object of destination file
            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists())
            {
                //if not permit delete, throe SecurityException
                //SecurityManager securityManager = new SecurityManager();
                //securityManager.checkDelete(zipFilePath);
                //if exist repeat file, delete
                if(!zipFile.delete()){
                    return false;
                }
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);

            //if only compress one file, cut parent folder of the file
            String srcRootDir = srcPath;
            if (srcFile.isFile())
            {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1)
                {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            //compress file or folder with recursion
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (zos != null)
            {
                zos.close();
            }
        }
        return true;
    }

}
