public class LetterFreqCount
{
   /**
      Counts the frequencies of letters A-Za-z in a string
      @param str a string
      @return an array of 26 counts. The i-th count is the number of occurrences
      of 'A' + i or 'a' + i.
   */
   public int[] letterFrequencies(String str)
   {
      int[] cnt = new int[26];
      for (int i = 0;i<str.length();i++){

         char ch = Character.toLowerCase(str.charAt(i));
         if (ch>'z' || ch<'a'){
            continue;
         }
         cnt[(int)(ch - 'a')]++;
      }
      return cnt;
 
 
   }
}
