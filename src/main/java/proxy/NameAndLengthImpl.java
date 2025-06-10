package proxy;

public class NameAndLengthImpl implements MyInterface{
    @Override
    public void func1() {
        String methodName = "fun1";
        System.out.println(methodName);
        System.out.println(methodName.length());
    }

    @Override
    public void func2() {
        String methodName = "fun1";
        System.out.println(methodName);
        System.out.println(methodName.length());
    }

    @Override
    public void func3() {
        String methodName = "fun1";
        System.out.println(methodName);
        System.out.println(methodName.length());
    }
}
