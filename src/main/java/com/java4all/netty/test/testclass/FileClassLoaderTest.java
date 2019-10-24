package com.java4all.netty.test.testclass;

/**
 * @author wangzhongxiang
 * @date 2019年10月24日 09:31:31
 */
public class FileClassLoaderTest {

    private static String rootDir;

    public static void main(String[]args){
//        FileClassLoader fileClassLoader = new FileClassLoader("");
//        System.out.println("自定义类加载器的父类加载器："+fileClassLoader.getParent());
//        System.out.println("系统默认的AppClassLoader:"+ClassLoader.getSystemClassLoader());
//        System.out.println("AppClassLoader父类加载器："+ClassLoader.getSystemClassLoader().getParent());
//        System.out.println("ExtClassLoader父父类加载器："+ClassLoader.getSystemClassLoader().getParent().getParent());

        rootDir = "G:/mylearn/netty/src/main/java";
        FileClassLoader loader = new FileClassLoader(rootDir);
        try {
            Class<?> aClass = loader.findClass("com.java4all.netty.test.testclass.AAA");
            aClass.newInstance().toString();
            System.out.println("aaaaaaaaaaaaaaaaaa");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}

//自定义类加载器的父类加载器：sun.misc.Launcher$AppClassLoader@14dad5dc
//系统默认的AppClassLoader:sun.misc.Launcher$AppClassLoader@14dad5dc
//AppClassLoader父类加载器：sun.misc.Launcher$ExtClassLoader@5d22bbb7
//ExtClassLoader父父类加载器：null
