package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import java.util.*;

public class WordNet {

    private final NGramMap ngm;

    public WordNet(String synFilename, String hypFilename, NGramMap ngm2) {
        ngm = ngm2;
        graph = new Graph(synFilename, hypFilename);
    }


    private final Graph graph;

    // return graph.youDoSomething();

    public String doSomething(List<String> words, int k, int startYear, int endYear) {
        Set<String> findWord = graph.youDoSomething(words);
        List<String> wordList = new ArrayList<>();
        HashMap<Double, ArrayList<String>> wordMap = new HashMap<>();

        if (k == 0) {
            wordList.addAll(findWord);
        }

        if (k != 0) {
            wordList = new ArrayList<>();

            //@source Yebon Byun
            for (String word : findWord) {
                List<Double> list = ngm.countHistory(word, startYear, endYear).data();
                Double d = 0.0;

                if (list.isEmpty()) {
                    continue;
                } else {
                    for (Double d2 : list) {
                        d = d + d2;
                    }

                    if (wordMap.containsKey(d)) {
                        continue;
                    } else {
                        wordMap.put(d, new ArrayList<>());
                    }
                    ArrayList<String> al = wordMap.get(d);
                    al.add(word);
                }
            }

            List<Double> list = new ArrayList<>(wordMap.keySet());
            Collections.sort(list);
            int integer = 0;

            for (int i = list.size() - 1; i >= 0; i--) {
                Collections.sort(wordMap.get(list.get(i)));
                for (String list2 : wordMap.get(list.get(i))) {
                    if (integer >= k) {
                        break;
                    }
                    integer++;
                    wordList.add(list2);
                }
            }
        }
        Collections.sort(wordList);
        if (!wordList.isEmpty()) {
            String s = wordList.get(0);
            for (int i = 1; i <= wordList.size() - 1; i++) {
                s = s + ", " + wordList.get(i);
            }
            return "[" + s + "]";
        }
        return "[]";
    }
}



