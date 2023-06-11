package Burst.Engine.Source.Core.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.IOException;
import java.lang.reflect.Modifier;
/**
 * @author Oliver Schuetz
 */
public class ClassDerivativeSearch {
    private Class<?> baseClass;
    private List<String> packageNames;
    private List<Class<?>> classes;

    public ClassDerivativeSearch(Class<?> baseClass) {
        this.baseClass = baseClass;
        this.packageNames = new ArrayList<>();
    }

    public void addPackage(String packageName) {
        this.packageNames.add(packageName);
    }


public  List<Class<?>> search() {
    this.classes = new ArrayList<>();
    for (String packageName : packageNames) {
        // Search for all classes in this package and add them to the list if they are a instance of the base class
        try {
            List<Class<?>> classes = ClassFinder.getClassesForPackage(packageName);
            for (Class<?> clazz : classes) {
                if (baseClass.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                    this.classes.add(clazz);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    return classes;
}

}