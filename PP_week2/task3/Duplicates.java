import java.util.ArrayList;

public class Duplicates
{
   /**
       This method goes through the array of integers identified by
       the only parameter, creating a new ArrayList from the array,
       eliminating duplicates from the original array.
       @param theArray, an array of integer
       @return uniqueIntAL, the new ArrayList

   */
   public static ArrayList copyArray(int[] anArray)
   {
      ArrayList<Integer> uniqueIntAl = new ArrayList<>();
      outer:
      for (int x : anArray){
         for (int y : uniqueIntAl){
            if (x == y){
               continue outer;
            }
         }
         uniqueIntAl.add(x);
      }
      return uniqueIntAl;

   }
}
