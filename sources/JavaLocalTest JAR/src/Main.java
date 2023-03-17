import main.DBManipulation;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        DBManipulation dbManipulation = new DBManipulation("localhost:5432/project2","postgres","111111");
    }
}