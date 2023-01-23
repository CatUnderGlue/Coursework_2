import java.util.*;
import java.util.stream.Collectors;

public class AdditionalTask {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите текст:");
        String text = sc.nextLine().replaceAll("\\p{Punct}", "").toLowerCase().strip();
        printInfo(getWordsList(text));
    }

    public static List<Map.Entry<String, Integer>> getWordsList(String string){
        Map<String, Integer> wordsMap = new TreeMap<>();
        String[] words = string.split(" ");
        for (String word : words){
            if (!wordsMap.containsKey(word)){
                wordsMap.put(word, 1);
            } else {
                wordsMap.put(word, wordsMap.get(word) + 1);
            }
        }
        return wordsMap.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).collect(Collectors.toList());
    }

    public static void printInfo(List<Map.Entry<String, Integer>> list){
        int count = 0;
        System.out.printf("В тексте: %d слов\n", list.size());
        for (Map.Entry<String, Integer> stringIntegerEntry : list) {
            if (count < 10){
                System.out.printf("%s : %d\n", stringIntegerEntry.getKey(), stringIntegerEntry.getValue());
                count += 1;
            } else {
                break;
            }
        }
    }
}
