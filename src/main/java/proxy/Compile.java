package proxy;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Compile {

    /**
     * 编译单个Java文件到target/classes目录
     * @param javaFile Java源文件对象
     */
    public static void compile(File javaFile) {
        // 检查文件是否存在
        if (!javaFile.exists() || !javaFile.isFile()) {
            System.err.println("文件不存在或不是有效文件: " + javaFile.getPath());
            return;
        }

        // 检查文件扩展名
        if (!javaFile.getName().endsWith(".java")) {
            System.err.println("文件不是Java源文件: " + javaFile.getPath());
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("无法获取Java编译器，请确保使用JDK而不是JRE运行");
            return;
        }

        // 准备编译参数
        String[] options = new String[]{"-d", "target/classes"};

        // 确保目标目录存在
        createTargetClassesDir();

        // 执行编译
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjects(javaFile);

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                null,
                Arrays.asList(options),
                null,
                compilationUnits
        );

        boolean success = task.call();
        try {
            fileManager.close();
        } catch (IOException e) {
            System.err.println("关闭文件管理器时出错: " + e.getMessage());
        }

        if (success) {
            System.out.println("编译成功: " + javaFile.getName());
        } else {
            System.err.println("编译失败: " + javaFile.getName());
        }
    }

    private static void createTargetClassesDir() {
        File dir = new File("target/classes");
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("无法创建目标目录: target/classes");
            }
        }
    }


}
