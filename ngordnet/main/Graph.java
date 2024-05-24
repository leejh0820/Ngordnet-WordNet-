package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.io.IOException;
import java.util.*;

public class Graph {

    public HashMap<String, Set<String>> sFword = new HashMap<>();
    public HashMap<String, Set<String>> hFword = new HashMap<>();


    // variables : what is our graph representation?
    // adjList, adjMatrix
    public Graph(String synFilename, String hypFilename) {


            In sF = new In(synFilename);
            In hF = new In(hypFilename);

            while (sF.hasNextLine()) {
                String[] line = sF.readLine().split(",");
                String integer = line[0];
                String[] word = line[1].split(" ");
                Set<String> wordList = new HashSet<>(Arrays.asList(word));
                sFword.put(integer, wordList);
            }

            while (hF.hasNextLine()) {
                String[] line = hF.readLine().split(",");
                String integer = line[0];
                Set<String> set;

                if (hFword.containsKey(integer)) {
                    set = hFword.get(integer);
                } else {
                    set = new HashSet<>();
                }
                for (int i = 1; i < line.length; i++) {
                    set.add(line[i]);
                }
                hFword.put(integer, set);
            }

    }


    public Set<String> addWord(String word) {
        Set<String> edge = new HashSet<>();

        for (String i : sFword.keySet()) {
            Set<String> getWord = sFword.get(i);
            if (getWord.contains(word)) {
                addWordHelper(i, edge);
            }
        }
        return edge;
    }

    public Set<String> youDoSomething(List<String> words) {
        Set<String> child = null;

        for (String word : words) {
            if (child == null) {
                child = addWord(word);
            }
            if (child.isEmpty()) {
                return child;
            }
            child.retainAll(addWord(word));
        }
        return child;
    }
    // graph helper functions
    private void addWordHelper(String word, Set<String> edge) {
        Set<String> sFWordList = sFword.get(word);
        Set<String> hFWordList = hFword.get(word);

        edge.addAll(sFWordList);

        if (hFword.containsKey(word)) {
            for (String i : hFWordList) {
                addWordHelper(i, edge);
            }
        }
    }


}
