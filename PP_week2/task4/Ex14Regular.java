import java.util.Scanner;
import java.io.IOException;
public class Ex14Regular{
    public static int[][] readArray() throws ArrayIndexOutOfBoundsException, IOException, NumberFormatException{
        Scanner in = new Scanner(System.in);
        int sz = 0;
        boolean isArrayInitialized = false;
        int[][] arr = null;
        int linesCount = 0;
        while (in.hasNextLine()){
            linesCount++;
            String line = in.nextLine();
            String[] temp = line.split(" ");
            sz = temp.length; // здесь мы задаем сторону квадрата как количество чисел в первой строке, ведь если вводится квадрат то это верно
            if (linesCount > sz){
                if (!line.isEmpty()){
                    ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Wrong array size, probably it's not a square!");
                    throw e;
                }
                break;
            }
            if (!isArrayInitialized){
                arr = new int[sz][];
                isArrayInitialized = true;
            }
            if (temp.length != sz){
                throw new ArrayIndexOutOfBoundsException("Wrong input: line " + linesCount + " length is " + temp.length + ", expected: " + sz);
            }
            int[] row = new int[sz];
            for (int i = 0;i<temp.length;i++){
                try{
                    row[i] = Integer.parseInt(temp[i]);
                } catch(NumberFormatException e){
                    throw e;
                }
            }
            arr[linesCount - 1] = row;
      
            
        }
        if (!isArrayInitialized){
            IOException e = new IOException("Wrong input");
            throw e;
        }
        in.close();
        return arr;
    }
    public static boolean isMagicSquare(int arr[][]){
        int rowSum = 0;
        for (int i = 0;i<arr.length;i++){
            int temp = 0;
            for (int j = 0;j<arr[i].length;j++){
                temp += arr[i][j];
            }
            if (i == 0){
                rowSum = temp;
            } else{
                if (rowSum != temp) return false;
            }
        }
        int colSum = 0;
        for (int i = 0;i<arr.length;i++){
            int temp = 0;
            for (int j = 0;j<arr[i].length;j++){
                temp += arr[j][i];
            }
            if (i == 0){
                colSum = temp;
            } else{
                if (colSum != temp) return false;
            }
        }
        if (colSum != rowSum)     return false;
        int firstDiagSum = 0;
        int secDiagSum = 0;
        for (int i =0;i<arr.length;i++){
            firstDiagSum += arr[i][i];
            secDiagSum += arr[arr.length -1 - i][i];
        }
        if (firstDiagSum != secDiagSum || firstDiagSum!=rowSum) return false;
        return true;
    }
    public static void main(String[] args){
        int arr[][] = null;
        try{
            arr = readArray();
        } catch(IOException e){
            System.out.println("IOException in method readArray(): " + e.getMessage());
            return;
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException in method readArray(): " + e.getMessage());
            return;
        } catch(NumberFormatException e){
            System.out.println("NumberFormatException in method readArray(): " + e.getMessage());
            return ;
        }
        for (int[] row : arr){
            for (int x : row){
               System.out.print(x + " ");
            }
            System.out.println();
        }
        if (isMagicSquare(arr)) {
            System.out.println("Magic");
        } else{
            System.out.println("Not magic");
        }
        
    }
}
