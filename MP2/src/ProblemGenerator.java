import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class ProblemGenerator {
/*Implement a random problem generator, that randomly generates five widget definitions of length N,
  where N is a parameter that you can specify (in the required problem, N=5).
  Hand in a plot of the number of nodes expanded by each of your two A* search algorithms, as a function of N, for 3<=N<=8.
 */
    public Vector<Widget> widgets = new Vector<>();

    public ProblemGenerator(int N) {
        Random rand = new Random();
        for(int w = 0; w < 5; w++) {
            String s = new String();
            for(int i = 0; i < N; i++){
                int random_number = ThreadLocalRandom.current().nextInt(65, 70);
                char random_char = (char) random_number;
                if(i==0) s += random_char;
                else {
                    if (s.charAt(i-1) == random_char) i--;
                    else s+= random_char;
                }
            }
            Widget widget = new Widget(s);
            widgets.add(widget);
        }
    }
}
