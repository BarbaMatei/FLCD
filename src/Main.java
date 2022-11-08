import java.io.Console;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.constant.Constable;
import java.util.ArrayList;

public class Main
{
    private static void printToFile(String filePath, Object object) {
        try(PrintStream printStream = new PrintStream(filePath)) {
            printStream.println(object);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //p1.txt  p2.txt  p3.txt  p3err.txt
        MyScanner scanner = new MyScanner("D:\\Matei\\University\\FLCD\\src\\p3.txt");
        scanner.scan();
        printToFile("src\\p3ST.txt", scanner.getSymbolTable());
        printToFile("src\\p3PIF.txt", scanner.getPif());
    }
}