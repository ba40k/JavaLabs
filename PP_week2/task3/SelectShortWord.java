public class SelectShortWord
{
   /**
      Returns the nth short word (length <= 3) in an array.
      @param words an array of strings
      @param n an integer > 0
      @return the nth short word in words, or the empty string if there is
      no such word
   */
   public String nthShortWord(String[] words, int n)
   {
      // your work here
      int count = 0;
      for (String s : words){
         if (s.length() <= 3){
            count++;
         }
         if (count == n){
            return s;
         }
      }
      return "";

   }
}
