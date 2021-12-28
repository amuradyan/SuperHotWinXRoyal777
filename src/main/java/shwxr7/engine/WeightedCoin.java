package shwxr7.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedCoin {
  public static List<Integer> flipACoinNTimes(int probability, int times) {
    var sequence = new ArrayList<Integer>();

    for (int i = 0; i < times; i++) {
      sequence.add(flipACoin(probability));
    }

    return sequence;
  }

  public static Integer flipACoin(int probability) {
    return new Random().nextInt(100) < probability ? 1 : 0;
  }
}
