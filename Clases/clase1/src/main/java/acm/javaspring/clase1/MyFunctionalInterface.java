package acm.javaspring.clase1;

@FunctionalInterface
public interface MyFunctionalInterface {
    void execute();
    default String getInfo(){
        return "This is a functional interface";
    }
    static void printMessage(String message){
        System.out.println(message);
    }
}
