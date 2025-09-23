import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
class InputOverflowException extends Exception{
    public InputOverflowException(String message){
        super(message);
    }
}
class WrongDimensionFormatException extends Exception{
    public WrongDimensionFormatException(String message){
        super(message);
    }
}
class Matrix{
    public static class Position{
        private int row;
        private int column;
        public Position(int initRow, int initColumn){
            row = initRow;
            column = initColumn;
        }
        public int getRow(){
            return row;
        }
        public int getColumn(){
            return column;
        }
    }
    private int[][] matrix;
    private int n,m;
    private void readDimensions(Scanner in) throws FileNotFoundException, InputOverflowException,  InputMismatchException, NoSuchElementException, WrongDimensionFormatException {
        n = in.nextInt();
        m = in.nextInt();
        if (n < 0 || m < 0){
            throw new WrongDimensionFormatException("Negative matrix dimension!");
        }
    }
    private void readMatrix(Scanner in) throws FileNotFoundException, InputOverflowException,  InputMismatchException{
         for (int i =0;i<n;i++){
             for (int j = 0;j<m;j++){
                 matrix[i][j] = in.nextInt();
             }
         }
         if (in.hasNext()) throw new InputOverflowException("Too much characters in input!");
    }
    public boolean isOutOfBound(Position pos){
         return (pos.getRow() < 0 || pos.getColumn() < 0 || pos.getRow() >=n || pos.getColumn()>=m);
    }
    public Matrix(Scanner initScanner) throws FileNotFoundException, InputOverflowException,  InputMismatchException, NoSuchElementException, WrongDimensionFormatException{
        readDimensions(initScanner);
        matrix = new int[n][m];
        readMatrix(initScanner);
    }
    public int at(Position pos) throws ArrayIndexOutOfBoundsException{
        return matrix[pos.row][pos.column];
    }
    public int getNumberOfRows(){
        return n;
    }
    public int getNumberOfColumns(){
        return m;
    }
}
class ExceptionProcessor{ 
    public  ExceptionProcessor(){}
    public static void processInputOverflowException(InputOverflowException e){
        System.out.println(e.getMessage());
    }
    public static void processFileNotFoundException(FileNotFoundException e){
        System.out.println(e.getMessage());
    }
    public static void processInputMismatchException(InputMismatchException e){
        System.out.println("Wrong input format!(probably it's there is not a number)");
    }
    public static void processWrongDimensionFormatException(WrongDimensionFormatException e){
        System.out.println(e.getMessage());
    }
    public static void processNoSuchElementException(NoSuchElementException e){
        System.out.println("Not enough characters!");
    }
    public static void processArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e){
        System.out.println("Array element acces error!");
    }
}
public class Lab{
    private static Matrix matrix;
    private static int[][] moves = {{0,-1},{0,1},{-1,0},{1,0},{1,1}, {-1,1}, {1,-1}, {-1,-1}};
    public static enum ExtremumState{
        MINIMUM,
        MAXIMUM
    }
    public static boolean isLocalExtremum(Matrix.Position pos, ExtremumState state ) {
         for (int[] move : moves){
              int newI = pos.getRow() + move[0];
              int newJ = pos.getColumn() + move[1];
              Matrix.Position newPos = new Matrix.Position(newI,newJ);
              if (matrix.isOutOfBound(newPos)) continue;
              if ((matrix.at(newPos) <= matrix.at(pos) && state == ExtremumState.MINIMUM)
                  || (matrix.at(newPos) >= matrix.at(pos) && state == ExtremumState.MAXIMUM)) return false;
         }
         return true;
    }
    public static void printLocalExtremumList(ExtremumState state){
        if (state == ExtremumState.MAXIMUM) System.out.println("Local maximum's:");
        if (state == ExtremumState.MINIMUM) System.out.println("Local minumum's:");
        for (int i =0;i<matrix.getNumberOfRows();i++){
             for (int j =0;j<matrix.getNumberOfColumns();j++){
                 Matrix.Position pos = new Matrix.Position(i,j);
                 if (isLocalExtremum(pos, state)) System.out.println("row: " + i + "; " + "column: " + j);
             }
         }
    }
    public static void executeLab() throws FileNotFoundException, InputOverflowException,  InputMismatchException, NoSuchElementException, WrongDimensionFormatException{
        File inputFile = new File("input.txt");
        Scanner in = new Scanner(inputFile);
        matrix = new Matrix(in);
        printLocalExtremumList(ExtremumState.MINIMUM);
        printLocalExtremumList(ExtremumState.MAXIMUM);
        in.close();
    }
    public static void main(String args[]){
        ExceptionProcessor exceptionProcessor = new ExceptionProcessor();
        try {
            executeLab();
        } catch (FileNotFoundException e){
            exceptionProcessor.processFileNotFoundException(e);
        } catch (InputOverflowException e){
            exceptionProcessor.processInputOverflowException(e);
        } catch (WrongDimensionFormatException e){
            exceptionProcessor.processWrongDimensionFormatException(e);
        } catch(InputMismatchException e){
            exceptionProcessor.processInputMismatchException(e);
        } catch(NoSuchElementException e){
            exceptionProcessor.processNoSuchElementException(e);
        } catch(ArrayIndexOutOfBoundsException e){
            exceptionProcessor.processArrayIndexOutOfBoundsException(e);
        }
        
    }
}
