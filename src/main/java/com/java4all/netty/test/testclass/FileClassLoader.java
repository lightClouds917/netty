package com.java4all.netty.test.testclass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wangzhongxiang
 * @date 2019年10月24日 09:29:13
 */
public class FileClassLoader extends ClassLoader{
    private String rootDir;

    public FileClassLoader(String dir) {
        this.rootDir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = new byte[0];
        try {
            classData = this.getClassData(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null == classData){
            throw new ClassNotFoundException();
        }else {
            return defineClass(name,classData,0,classData.length);
        }
    }

    private byte[] getClassData(String className) throws IOException {
        String path = this.className2Path(className);
        FileInputStream ins = new FileInputStream(path);
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int byteNumRead = 0;
        while ((byteNumRead = ins.read(buffer)) != -1){
            ous.write(buffer,0,byteNumRead);
        }
        return ous.toByteArray();

    }

    private String className2Path(String className){
        return rootDir + "/"
                + className.replace('.','/') + ".class";
//        return new StringBuffer()
//                .append(rootDir)
//                .append("/")
//                .append(className.replace(".","/"))
//                .append(".java")
//                .toString();
    }
}
