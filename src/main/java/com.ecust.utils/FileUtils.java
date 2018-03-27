package com.ecust.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件工具类，用来处理一些文件相关的操作
 *
 * @author caizheng
 *
 */
public class FileUtils {
    /**
     * 工具类，从txt文本中选取部分的数据集，并且保存
     *
     * @param starLine
     *            ： 开始的行数，下标的开始值为 1
     * @param endLine
     *            ： 结束的行数
     * @param inputUrl
     *            ： 输入的文件地址
     * @param outputUrl：
     *            输出的文件地址
     */
    public static void getPartDataByNum(int starLine, int endLine, String inputUrl, String outputUrl) {
        File file = new File(inputUrl);
        if (file.exists() && file != null) {
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 1;
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl, false), "UTF-8")));
                while ((lineTxt = bufferedReader.readLine()) != null && i <= endLine) {
                    if (i >= starLine) {
                        saveLineFast(lineTxt, out);
                    }
                    i++;
                }
                out.close();
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println("文件不存在");
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归得到一个文件夹里面所有的文件
     * @param path
     * @return
     */
    public static List<String> traverseFolder(String path) {
        List<String> fileUrls = new ArrayList<>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    fileUrls.add(file2.getAbsolutePath());
                    folderNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        fileNum++;
                    } else {
                        fileUrls.add(file2.getAbsolutePath());
                        System.out.println("文件:" + file2.getAbsolutePath());
                        folderNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
        return fileUrls;

    }

    /**
     * 在文件中保存该字符串
     *
     * @param lineString
     *            ： 传入需要保存的字符串
     * @param out
     *            ： 传入输出流
     */
    public static void saveLineFast(String lineString, PrintWriter out) {
        try {
            out.println(lineString);
        } catch (Exception e) {
            System.out.println("文件不存在");
            e.printStackTrace();
        }
    }

    /**
     * 在文件中保存该字符串
     *
     * @param outputUrl
     *            ： 输出文件的绝对地址
     * @param lineString
     *            ： 传入需要保存的字符串
     */
    public static void saveLine(String outputUrl, String lineString) {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl, false), "UTF-8")));
            out.print(lineString + "\n");
            out.close();
        } catch (Exception e) {
            System.out.println("文件不存在");
            e.printStackTrace();
        }
    }

    /**
     * 在文件中保存该字符串
     *
     * @param outputUrl
     *            ： 输出文件的绝对地址
     * @param lineString
     *            ： 传入需要保存的字符串
     */
    public static void saveLineAppend(String outputUrl, String lineString) {
        try {
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputUrl, true), "UTF-8")));
            out.print(lineString + "\n");
            out.close();
        } catch (Exception e) {
            System.out.println("文件不存在");
            e.printStackTrace();
        }
    }

    /**
     * 根据地址返回一个List容器，里面存放每行的字符串
     *
     * @param url
     *            ： 文本文件地址
     * @return
     */
    public static List<String> getAllData(String url) {
        File file = new File(url);
        List<String> list = new ArrayList<String>();
        if (file.exists() && file != null) {
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println("文件不存在");
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 根据文件返回一个List容器，里面存放每行的字符串
     *
     * @param file
     *            ： 文本文件地址
     * @return
     */
    public static List<String> getAllData(File file) {
        List<String> list = new ArrayList<>();
        if (file.exists() && file != null) {
            try {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println("文件不存在");
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param targetFile
     *            源文件
     * @param targetFile
     *            复制到的新文件
     */
    public static void fileChannelCopy(File sourceFile, File targetFile) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(sourceFile);
            fo = new FileOutputStream(targetFile);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param sourceUrl
     *            源文件地址
     * @param targetUrl
     *            复制到的新文件地址
     */
    public static void fileChannelCopy(String sourceUrl, String targetUrl) {
        File sourceFile = new File(sourceUrl);
        File targetFile = new File(targetUrl);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(sourceFile);
            fo = new FileOutputStream(targetFile);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}