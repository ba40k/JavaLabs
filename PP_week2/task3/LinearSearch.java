public class LinearSearch
{
   /**
      This method performs a linear search on the array identified by
      the first parameter, while looking for the value indicated by
      the second parameter.
      @param values, an array of integers
      @param valueToFind, an integer to look for in the array values
      @ return, the index (subscript) of the array where the value
            was found OR the length of the array if it was not found.
   */
   public static int findValue(int values[], int valueToFind)
   {

      int res = values.length;
      for (int i = 0;i<values.length;i++){
         if (values[i] == valueToFind){
            res = i;
         }
      }
      return res;

   }
}
