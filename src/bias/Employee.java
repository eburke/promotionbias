package bias;

public class Employee implements Comparable<Employee> {
  public final Gender gender;
  private int rating = 50;

  public Employee(Gender gender) {
    this.gender = gender;
  }

  public int getRating() {
    return this.rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }


  @Override
  public int compareTo(Employee other) {
    if (rating > other.rating) {
      return 1;
    } else if (rating < other.rating) {
      return -1;
    }
    return 0;
  }
}
