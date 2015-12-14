package bias;

public class Employee implements Comparable<Employee> {
  public final Gender gender;
  private int totalScore = 0;
  private int numScores = 0;
  private double meanScore = 0;

  public Employee(Gender gender) {
    this.gender = gender;
  }

  /**
   * This adds a new score and recomputes the mean score. It's used in the tournament model of
   * promotion.
   */
  public void addScore(int score) {
    totalScore += score;
    numScores++;
    meanScore = (double) totalScore / numScores;
  }

  /**
   * Reset the score and mean score to the given value. Used in the contest model of promotion.
   */
  public void resetScore(int score) {
    this.totalScore = score;
    this.numScores = 1;
    this.meanScore = score;
  }

  public double getMeanScore() {
    return meanScore;
  }

  @Override
  public int compareTo(Employee other) {
    if (meanScore > other.meanScore) {
      return 1;
    } else if (meanScore < other.meanScore) {
      return -1;
    }
    return 0;
  }
}
