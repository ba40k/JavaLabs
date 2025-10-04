import java.util.Scanner;
import java.util.ArrayList;
public class ex15Listed{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = 0;
        try{
            n = in.nextInt();
        } catch (NumberFormatException e){
            throw e;
        }
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        for (int i = 0;i<n;i++){
            arr.add(new ArrayList<>());
            arr.get(i).add(1);
            for (int j = 1;j<i;j++){
                arr.get(i).add(arr.get(i-1).get(j-1) + arr.get(i - 1).get(j));
            }
            arr.get(i).add(1);
        }
        for (ArrayList<Integer> row : arr){
            for (int x : row){
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
