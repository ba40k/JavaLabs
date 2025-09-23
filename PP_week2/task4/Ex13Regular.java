import java.util.Random;
public class Ex13Regular{
   final static int SIZE = 49;
   public static int[] removeAtPos(int[] arr, int pos){
       int[] newArr = new int[arr.length - 1];
       int shift = 0;
       for (int i = 0;i<arr.length;i++){
           if (i == pos) {
               shift = 1;
               continue;
           };
           newArr[i - shift] = arr[i];
       }
       return newArr;
   }
   public static int[] sort(int[] arr){
       boolean[] cnt = new boolean[SIZE];
       for (int x : arr){
           if (x == 0) continue;
           cnt[x - 1] = true;
       }
       int[] newArr = new int[6];
       int curPos = 0;
       for (int i = 0;i<SIZE;i++){
            if (cnt[i]) {
                newArr[curPos] = i + 1;
                curPos++;
            }
       }
       return newArr;
   }
   public static void printArray(int[] arr){
        for (int x : arr){
            System.out.print(x + " ");
        }
   }
   public static void main(String args[]){
       Random rand = new Random();
       int[] arr = new int[SIZE];
       int[] res = new int[6];
       for (int i = 0;i<SIZE;i++){
           arr[i] = i + 1;
       }
       for (int i = 0;i<6;i++){
           int pos = rand.nextInt(arr.length);
           res[i] = arr[pos];
           arr = removeAtPos(arr, pos);
           res = sort(res);
       }
       printArray(res);
       System.out.println();
       printArray(arr);
   }
}
