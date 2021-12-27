package shwxr7.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
  public static List<Integer> generateASequenceOfOnesWithProbability(int probability, int length) {
    var sequence = new ArrayList<Integer>();

    for (int i = 0; i < length; i++) {
      sequence.add(getAOneWithProbability(probability));
    }

    return sequence;
  }

  public static Integer getAOneWithProbability(int probability) {
    return new Random().nextInt(100) < probability ? 1 : 0;
  }
}
