package Burst.Engine.Source.Core.Util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassFinder {
    public static List<Class<?>> getClassesForPackage(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                String directoryPath = resource.getPath();
                addClassesFromDirectory(packageName, directoryPath, classes);
            }
        }
        return classes;
    }

    private static void addClassesFromDirectory(String packageName, String directoryPath, List<Class<?>> classes) throws ClassNotFoundException {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isDirectory()) {
                        String subPackage = packageName + "." + fileName;
                        String subDirectoryPath = directoryPath + "/" + fileName;
                        addClassesFromDirectory(subPackage, subDirectoryPath, classes);
                    } else if (fileName.endsWith(".class")) {
                        String className = packageName + "." + fileName.substring(0, fileName.length() - 6);
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    }
                }
            }
        }
    }
}
