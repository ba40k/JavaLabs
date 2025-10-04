class Task3 {
    public static void main(String[] args) {
        String str = "Hello 123 world, this is 456 test. Some 789 text";
        System.out.println(str.replaceAll("\\d+", "").replaceAll("\\s+", " ").trim());
    }
}
class Task2 {
    public static void main(String[] args) {
        String str = "One two three раз два три one1 two2 123";
        String[] words = str.split(" ");
        int cnt = 0;
        for (String s : words) {
            if (s.matches("[a-zA-Z]+")) count++;
        }
        System.out.println(count);
    }
}
public class Task1 {
    public static void main(String[] args) {
        int a = 3;
        int b = 56;
        StringBuilder sb = new StringBuilder();
        sb.append(a).append("+").append(b).append("=").append(a + b).append("\n");
        sb.append(a).append("-").append(b).append("=").append(a - b).append("\n");
        sb.append(a).append("*").append(b).append("=").append(a * b);
        System.out.println(sb);
    }
}
