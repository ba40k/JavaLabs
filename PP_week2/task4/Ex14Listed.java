import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
public class Ex14Listed{
    public static ArrayList<ArrayList<Integer>> readArray() throws ArrayIndexOutOfBoundsException, IOException, NumberFormatException{
        Scanner in = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        while (in.hasNextLine()){
            String line = in.nextLine();
            String[] temp = line.split(" ");
            if (arr.size() != 0){
                if (line.isEmpty()) break;
                if (temp.length != arr.get(0).size()){
                    ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Wrong array size, probably it's not a square!");
                    throw e;
                }
            }
            ArrayList<Integer> row = new ArrayList<>();
            for (String s : temp){
                try{
                    row.add(Integer.parseInt(s));
                } catch(NumberFormatException e){
                    throw e;
                }
            }
            
            arr.add(row);
        }
        if (arr.size() == 0){
            IOException e = new IOException("Wrong input");
            throw e;
        }
        in.close();
        return arr;
    } 
    public static boolean isMagicSquare(ArrayList<ArrayList<Integer>> arr){
        int rowSum = 0;
        for (int i = 0;i<arr.size();i++){
            int temp = 0;
            for (int j = 0;j<arr.get(i).size();j++){
                temp += arr.get(i).get(j);
            }
            if (i == 0){
                rowSum = temp;
            } else{
                if (rowSum != temp) return false;
            }
        }
        int colSum = 0;
        for (int i = 0;i<arr.size();i++){
            int temp = 0;
            for (int j = 0;j<arr.get(i).size();j++){
                temp += arr.get(j).get(i);
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
        for (int i =0;i<arr.size();i++){
            firstDiagSum += arr.get(i).get(i);
            secDiagSum += arr.get(arr.size() -1 - i).get(i);
        }
        if (firstDiagSum != secDiagSum || firstDiagSum!=rowSum) return false;
        return true;
    }
    public static void main(String[] args){
        
        ArrayList<ArrayList<Integer>> arr = null;
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
        for (ArrayList<Integer> row : arr){
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
