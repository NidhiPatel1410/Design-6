
// Bruteforce
import java.util.*;

// public class DesignSearchAutoCompleteSystem {
//     HashMap<String, Integer> map;
//     StringBuilder sb;

//     public DesignSearchAutoCompleteSystem(String[] sentences, int[] times) {
//         map = new HashMap<>();
//         sb = new StringBuilder();
//         for (int i = 0; i < sentences.length; i++) {
//             String sentence = sentences[i];
//             map.put(sentence, map.getOrDefault(sentence, 0) + times[i]);
//         }
//     }

//     public List<String> input(char c) {

//         if (c == '#') {
//             String s = sb.toString();
//             map.put(s, map.getOrDefault(s, 0) + 1);
//             System.out.print(s + map.get(s));
//             sb = new StringBuilder();
//             return new ArrayList<>();
//         }
//         sb.append(c);
//         PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
//             if (map.get(a) == map.get(b)) {
//                 return b.compareTo(a);
//             }
//             return map.get(a) - map.get(b);
//         });
//         for (String s : map.keySet()) {
//             if (s.startsWith(sb.toString())) {
//                 pq.add(s);
//                 if (pq.size() > 3) {
//                     pq.poll();
//                 }
//             }
//         }

//         List<String> result = new ArrayList<>();
//         while (!pq.isEmpty()) {
//             result.add(0, pq.poll());
//         }
//         return result;

//     }

//     public static void main(String[] args) {
//         String[] sentences = new String[] { "i love leetcode", "ice cream", "ironman", "iron" };
//         int[] times = new int[] { -1, -1, -1, -1 };
//         DesignSearchAutoCompleteSystem d = new DesignSearchAutoCompleteSystem(sentences, times);
//         List<String> r = d.input('#');
//         for (String st : r) {
//             System.out.print(st + " : ");
//         }
//     }
// }

// // Intermediate
// public class DesignSearchAutoCompleteSystem {
//     // HashMap<String, Integer> map;
//     StringBuilder sb;

//     class TrieNode {
//         HashMap<Character, TrieNode> children;
//         HashMap<String, Integer> map;

//         public TrieNode() {
//             this.children = new HashMap<>();
//             this.map = new HashMap<>();
//         }
//     }

//     TrieNode root;

//     public void insert(String word, int time) {
//         TrieNode curr = root;
//         for (int i = 0; i < word.length(); i++) {
//             char c = word.charAt(i);
//             if (!curr.children.containsKey(c)) {
//                 curr.children.put(c, new TrieNode());
//             }
//             curr = curr.children.get(c);
//             curr.map.put(word, curr.map.getOrDefault(word, 0) + time);
//         }
//     }

//     public HashMap<String, Integer> getList(String prefix) {
//         TrieNode curr = root;
//         for (int i = 0; i < prefix.length(); i++) {
//             char c = prefix.charAt(i);
//             if (!curr.children.containsKey(c)) {
//                 return new HashMap<>();
//             }
//             curr = curr.children.get(c);
//         }
//         return curr.map;
//     }

//     public DesignSearchAutoCompleteSystem(String[] sentences, int[] times) {
//         root = new TrieNode();
//         sb = new StringBuilder();
//         for (int i = 0; i < sentences.length; i++) {
//             String sentence = sentences[i];
//             insert(sentence, times[i]);
//         }
//     }

//     public List<String> input(char c) {

//         if (c == '#') {
//             String s = sb.toString();
//             insert(s, 1);
//             sb = new StringBuilder();
//             return new ArrayList<>();
//         }
//         sb.append(c);
//         HashMap<String, Integer> map = getList(sb.toString());
//         PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
//             if (map.get(a) == map.get(b)) {
//                 return b.compareTo(a);
//             }
//             return map.get(a) - map.get(b);
//         });

//         for (String s : map.keySet()) {
//             pq.add(s);
//             if (pq.size() > 3) {
//                 pq.poll();
//             }
//         }
//         List<String> result = new ArrayList<>();
//         while (!pq.isEmpty()) {
//             result.add(0, pq.poll());
//         }
//         return result;
//     }

//     public static void main(String[] args) {
//         String[] sentences = new String[] { "i love leetcode", "ice cream", "ironman", "iron" };
//         int[] times = new int[] { 4, 5, 4, 2 };
//         DesignSearchAutoCompleteSystem d = new DesignSearchAutoCompleteSystem(sentences, times);
//         List<String> r = d.input('i');
//         for (String st : r) {
//             System.out.print(st + " : ");
//         }
//     }
// }

// In this problem, using a hashmap to store the strings with their frequencies, and trie to store the strings along with list of 3
// answer with each node. Since we want to get top 3 words with high frquencies that startswith input prefix, we are using trie as
// prefix tree, where we can quickly lookup and return the list of top 3 words. Also, hashmap for O(1) lookup for comparing the 
// frequencies.

// Time Complexity : O(nl)
// Space Complexity : O(nl)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// Best Approach:
// Intermediate
public class DesignSearchAutoCompleteSystem {
    // TrieNode class
    class TrieNode {
        // Here we have hashmap as children, with 26 chars as key and trienode as value.
        // In earlier implementations we used the array of length 26.
        HashMap<Character, TrieNode> children;
        // Each node will have list consisting of top 3 most searched words
        List<String> pq;

        // Constructor
        public TrieNode() {
            this.children = new HashMap<>();
            this.pq = new ArrayList<>();
        }
    }

    // Hashmap for storing all the words with their frquency. We need this becuase
    // in trie we are only storing top 3 words, so we dont want to loose other
    // words, since in future if their frequency increases we need to replace in the
    // trie and put this word in top 3 list
    HashMap<String, Integer> map;
    // Stringbuilder for building the prefix or word
    StringBuilder sb;
    TrieNode root;

    // Insert words in trie method
    public void insert(String word, int time) {
        // Take the current node and set it to root
        TrieNode curr = root;
        // Go over the each char in word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // If there is no char as the children of trienode, the put this char with new
            // trienode
            if (!curr.children.containsKey(c)) {
                curr.children.put(c, new TrieNode());
            }
            // Move to that children
            curr = curr.children.get(c);
            // Get the list of top 3 words associated to it
            List<String> temp = curr.pq;
            // Add the current word to it
            temp.add(word);
            // Now sort list as per their frquencies
            Collections.sort(temp, (a, b) -> {
                // If same frequencies then lexicographically smaller should come first
                if (map.get(a) == map.get(b)) {
                    return a.compareTo(b);
                }
                // high frequencies first
                return map.get(b) - map.get(a);
            });
            // Now if the list size is more than 3, remove the last string, since we know it
            // is sorted
            if (temp.size() > 3) {
                temp.remove(temp.size() - 1);
            }
            // Replace the current trienode's list with this changed list
            curr.pq = temp;
        }
    }

    // To get the top 3 words that startswith input prefix
    public List<String> getList(String prefix) {
        // Take the current node
        TrieNode curr = root;
        // Go over the prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            // If no char, then return empty list
            if (!curr.children.containsKey(c)) {
                return new ArrayList<>();
            }
            // Else move to that children
            curr = curr.children.get(c);
        }
        // And at end return list associated with that prefix
        return curr.pq;
    }

    public DesignSearchAutoCompleteSystem(String[] sentences, int[] times) {
        // Initialize all
        root = new TrieNode();
        map = new HashMap<>();
        sb = new StringBuilder();
        // Go over sentences and put in map and in trie
        for (int i = 0; i < sentences.length; i++) {
            String sentence = sentences[i];
            map.put(sentence, map.getOrDefault(sentence, 0) + times[i]);
            insert(sentence, times[i]);
        }
    }

    // When user types a char
    public List<String> input(char c) {
        // If it is #, means we have to save this word with the updated frequency in the
        // map and in trie
        if (c == '#') {
            String s = sb.toString();
            map.put(s, map.getOrDefault(s, 0) + 1);
            insert(s, map.getOrDefault(s, 0) + 1);
            // Reset stringbuilder
            sb = new StringBuilder();
            // Then return empty arraylist
            return new ArrayList<>();
        }
        // Append that our prefix
        sb.append(c);
        // Get the list associated with prefix and return
        return getList(sb.toString());
    }

    public static void main(String[] args) {
        String[] sentences = new String[] { "i love leetcode", "ice cream", "ironman", "iron" };
        int[] times = new int[] { 4, 5, 4, 2 };
        DesignSearchAutoCompleteSystem d = new DesignSearchAutoCompleteSystem(sentences, times);
        List<String> r = d.input('i');
        for (String st : r) {
            System.out.print(st + " : ");
        }
    }
}
