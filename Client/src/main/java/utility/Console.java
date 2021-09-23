package utility;

import Interfaces.ConsoleInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class to works with user's input and console
 */
public class Console implements ConsoleInterface {

    private static Console instance;
    private Scanner scanner;
    private BufferedReader bufferedReader;
    private boolean exeStatus;

    public static Console getInstance() {
        if (instance == null) instance = new Console();
        return instance;
    }

    @Override
    public void setScanner(Scanner aScanner) {
        exeStatus = false;
        scanner = aScanner;
    }

    @Override
    public void print(Object anObj) {
        System.out.print(anObj);
    }

    @Override
    public void print(Object anObj, boolean fieldsReading) {
        if (!exeStatus || !fieldsReading) print(anObj);
    }

    @Override
    public String read() throws IOException {

        try {
            return (!exeStatus) ? scanner.nextLine().trim() : bufferedReader.readLine();
        } catch (NoSuchElementException exception) {
            System.exit(0);
            return null;
        }
    }

    @Override
    public boolean getExeStatus() {
        return exeStatus;
    }

    @Override
    public void setBufferedReader(BufferedReader aBufferedReader) {
        bufferedReader = aBufferedReader;
    }

    @Override
    public void setExeStatus(boolean anExeStatus) {
        exeStatus = anExeStatus;
    }
}