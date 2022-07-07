/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int numberOfWords = 0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            ++numberOfWords;
            double probability = 1.0 / numberOfWords;
            if (StdRandom.bernoulli(probability)) {
                champion = string;
            }
        }
        StdOut.println(champion);
    }
}
