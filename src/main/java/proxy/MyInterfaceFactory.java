package proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

public class MyInterfaceFactory {
    public static final AtomicInteger cont = new AtomicInteger();
    private static File createJavaFile(String className,MyHandler myHandler) throws IOException {


        String func1Body = myHandler.functionBody("func1");
        String func2Body = myHandler.functionBody("func2");
        String func3Body = myHandler.functionBody("func3");



        String context ="package proxy;\n"+
                "\n"+
                "public class "+ className +" implements MyInterface{\n" +
                "    MyInterface myInterface;\n"+
                "    @Override\n" +
                "    public void func1() {\n" +
                "        "+func1Body+"\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void func2() {\n" +
                "        "+func2Body+"\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void func3() {\n" +
                "        "+func3Body+"\n" +
                "    }\n" +
                "}\n";

        File javaFile = new File(className + ".java");
        Files.writeString(javaFile.toPath(),context);
        return javaFile;
    }


    private static String getClassName(){
        return "MyInterface$proxy" + cont.incrementAndGet();
    }


    private static MyInterface newInstance(String className,MyHandler myHandler ) throws Exception {
        Class<?> aClass = MyInterfaceFactory.class.getClassLoader().loadClass(className);
        Constructor<?> constructor = aClass.getConstructor();
        MyInterface proxy = (MyInterface)constructor.newInstance();
        myHandler.setProxy(proxy);
        return proxy;
    }

    public static MyInterface createProxyObject(MyHandler myHandler) throws Exception{
        String className = getClassName();
        File javaFile = createJavaFile(className,myHandler);
        Compile.compile(javaFile);
        return newInstance("proxy."+className,myHandler);


    }



}
