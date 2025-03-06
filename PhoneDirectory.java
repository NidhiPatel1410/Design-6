// In this problem, using a queue for sequential order and hashset for O(1) lookup. Initially put all numbers from 0 to n-1 in both
// queue and in hashset. Now, when get is called, check is queue empty? No, then do poll and give that number and also remove that
// number from set, if yes, queue is empty than just return -1. Then, when check is called, simply check in set, if present means
// available so return true else false. When release is called, add that number in both set and queue if not present.

// Time Complexity : O(1) for all methods, O(maxNumbers) for constructor, but constructor is only initialized once
// Space Complexity : O(maxNumbers) overall
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class PhoneDirectory {
    // Declare set and queue
    HashSet<Integer> set;
    Queue<Integer> q;

    public PhoneDirectory(int maxNumbers) {
        // Initialize
        set = new HashSet<>();
        q = new LinkedList<>();
        // Add all numbers from 0 to n-1 in both queue and set
        for (int i = 0; i < maxNumbers; i++) {
            set.add(i);
            q.add(i);
        }
    }

    public int get() {
        // Check if queue not empty, poll the element and give that
        if (!q.isEmpty()) {
            int popped = q.poll();
            // Also remove from set, indicating that it is no more available
            set.remove(popped);
            return popped;
        }
        return -1;

    }

    public boolean check(int number) {
        // Check in set if present, return true else false
        return set.contains(number);
    }

    public void release(int number) {
        // If this number is not already present, add it in both, indicating that it is
        // available again now
        if (!set.contains(number)) {
            q.add(number);
            set.add(number);
        }
    }

    public static void main(String[] args) {
        PhoneDirectory p = new PhoneDirectory(3);
        System.out.println(p.get());
        System.out.println(p.get());
        System.out.println(p.check(2));
        System.out.println(p.get());
        System.out.println(p.check(2));
        p.release(2);
        System.out.println(p.check(2));
    }

}