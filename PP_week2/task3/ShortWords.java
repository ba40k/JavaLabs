import java.util.ArrayList;

public class ShortWords
{
   /**
      Returns all short words (length <= 3) in an array of words
      @param words an array of strings
      @return an array list containing the short words in words
   */
   public ArrayList<String> shortWords(String[] words)
   {
      // your work here
      ArrayList<String> res = new ArrayList<>();
      for (String s : words){
         if (s.length() <= 3){
            res.add(s);
         }
      }
      return res;
      
   }
}
