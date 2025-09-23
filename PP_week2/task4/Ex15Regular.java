import java.util.Scanner;
public class Ex15Regular{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = 0;
        try{
            n = in.nextInt();
        } catch (NumberFormatException e){
            throw e;
        }
        int[][] arr = new int[n][];
        for (int i = 0;i<n;i++){
            arr[i] = new int[i + 1];
            arr[i][0] = 1;
            arr[i][i] = 1;
            for (int j = 1;j<i;j++){
                arr[i][j] = arr[i-1][j-1] + arr[i - 1][j];
            }
        }
        for (int[] row : arr){
            for (int x : row){
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
