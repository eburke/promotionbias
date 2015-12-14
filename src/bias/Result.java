package bias;

import java.util.HashMap;
import java.util.Map;

public class Result {
  private Map<Integer, Count> levelToCount = new HashMap<>();

  public Result() {
  }

  public void add(int level, Count count) {
    Count current = getCountAtLevel(level);
    current.add(count);
  }

  public void add(int level, Employee employee) {
    Count c = levelToCount.get(level);
    if (c == null) {
      c = new Count();
      levelToCount.put(level, c);
    }
    if (employee.gender == Gender.MALE) {
      c.males++;
    } else {
      c.females++;
    }
  }

  public double getPercentAtLevel(int level, Gender gender) {
    return ((double) getCountAtLevel(level, gender)) / getTotalAtLevel(level);
  }

  public int getTotalAtLevel(int level) {
    return getCountAtLevel(level, Gender.MALE) + getCountAtLevel(level, Gender.FEMALE);
  }

  public int getCountAtLevel(int level, Gender gender) {
    Count count = getCountAtLevel(level);
    return (gender == Gender.MALE) ? count.males : count.females;
  }

  public Count getCountAtLevel(int level) {
    Count count = levelToCount.get(level);
    if (count == null) {
      count = new Count();
      levelToCount.put(level, count);
    }
    return count;
  }

}
