import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class Ex13Listed{
    final static int SIZE = 49;
    public static void main(String[] args){
        Random rand = new Random();
        ArrayList<Integer> arr = new ArrayList<>(49);
        for (int i = 0;i<SIZE;i++){
            arr.add(i + 1);
        }
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0;i<6;i++){
            int pos = rand.nextInt(arr.size());
            res.add(arr.get(pos));
            arr.remove(pos);
        }
        Collections.sort(res);
        System.out.println(arr);
        System.out.println(res);
    }
}
