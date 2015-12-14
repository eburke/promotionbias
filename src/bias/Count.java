package bias;

public class Count {
  int males;
  int females;

  public void add(Count other) {
    this.males += other.males;
    this.females += other.females;
  }
}
