import java.io.Console;
import java.io.FileNotFoundException;
import java.lang.constant.Constable;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        //p1.txt  p2.txt  p3.txt  p3err.txt
        MyScanner scanner = new MyScanner("D:\\Matei\\University\\FLTC\\untitled\\src\\p3err.txt");
        scanner.scan();
    }
}