package bias;

import java.util.*;

public class Simulation {
  public static final int PROMOTION_BIAS = 2;
  public static final int ATTRITION = 15;
  public static final int[] NUM_EMPLOYEES_AT_EACH_LEVEL =
      new int[]{500, 350, 200, 150, 100, 75, 40, 10};
  public static final int LEVELS = NUM_EMPLOYEES_AT_EACH_LEVEL.length;
  public static final int PROMO_CYCLES = 20;
  public static final int SIMULATIONS = 20;
  public static final PromotionModel PROMOTION_MODEL = PromotionModel.TOURNAMENT;
  public static final AttritionModel ATTRITION_MODEL = AttritionModel.RANDOM;


  private final Map<Integer, List<Employee>> levelToEmployees = new HashMap<>();
  private final Random rand = new Random();
  private Gender nextGender = (rand.nextBoolean()) ? Gender.MALE : Gender.FEMALE;

  public Simulation() {
  }

  public static void main(String[] args) {

    System.out.format("Promotion Bias  : %d%n", PROMOTION_BIAS);
    System.out.format("Attrition Rate  : %d%n", ATTRITION);
    System.out.format("Promotion Cycles: %d%n", PROMO_CYCLES);
    System.out.format("Promotion Model : %s%n", PROMOTION_MODEL);
    System.out.format("Attrition Model : %s%n", ATTRITION_MODEL);
    System.out.format("Simulations     : %d%n", SIMULATIONS);
    System.out.println();

    Result grandTotal = new Result();

    for (int i = 0; i < SIMULATIONS; i++) {
      Simulation sim = new Simulation();
      sim.run();

      Result r = sim.getResult();
      for (int level = 0; level < LEVELS; level++) {
        grandTotal.add(level, r.getCountAtLevel(level));
      }
    }

    printResult(grandTotal, SIMULATIONS);
  }

  private static void printResult(Result r, int simulations) {
    System.out.println("Level  Men            Women");
    System.out.println("-----  -------------  -------------");
    for (int level = 0; level < LEVELS; level++) {
      System.out.format("%5d  %5.1f (%4.1f%%)  %5.1f (%4.1f%%)%n",
          level, ((double) r.getCountAtLevel(level, Gender.MALE)) / simulations,
          r.getPercentAtLevel(level, Gender.MALE) * 100,
          ((double) r.getCountAtLevel(level, Gender.FEMALE)) / simulations,
          r.getPercentAtLevel(level, Gender.FEMALE) * 100);
    }
  }

  public void run() {
    seed();
    for (int cycle = 0; cycle < PROMO_CYCLES; cycle++) {
      attrit();
      talentReview();
      promote();
      hire();
    }
  }

  private Result getResult() {
    Result r = new Result();
    for (int level = 0; level < LEVELS; level++) {
      List<Employee> employees = levelToEmployees.get(level);
      for (Employee employee : employees) {
        r.add(level, employee);
      }
    }

    return r;
  }

  private Gender nextGender() {
    nextGender = (nextGender == Gender.MALE) ? Gender.FEMALE : Gender.MALE;
    return nextGender;
  }

  private void seed() {
    for (int level = 0; level < LEVELS; level++) {
      List<Employee> employees = new ArrayList<>();
      levelToEmployees.put(level, employees);

      for (int i = 0; i < NUM_EMPLOYEES_AT_EACH_LEVEL[level]; i++) {
        employees.add(new Employee(nextGender()));
      }
    }
  }

  private void attrit() {
    for (Integer level : levelToEmployees.keySet()) {
      List<Employee> currentEmployees = levelToEmployees.get(level);
      List<Employee> retained = new ArrayList<>();

      if (ATTRITION_MODEL == AttritionModel.RANDOM) {
        // Return random employees.
        for (Employee e : currentEmployees) {
          if (rand.nextInt(100) >= ATTRITION) {
            retained.add(e);
          }
        }
      } else {
        // Remove low performers.
        Collections.sort(currentEmployees);
        int numberToAttrit = (int) ((double) ATTRITION / 100 * currentEmployees.size());
        for (int i=numberToAttrit; i<currentEmployees.size(); i++) {
          retained.add(currentEmployees.get(i));
        }
      }
      levelToEmployees.put(level, retained);
    }
  }

  private void talentReview() {
    for (List<Employee> employees : levelToEmployees.values()) {
      for (Employee employee : employees) {
        int score = rand.nextInt(100);
        if (employee.gender == Gender.MALE) {
          score += PROMOTION_BIAS;
        }

        if (PROMOTION_MODEL == PromotionModel.TOURNAMENT) {
          // The tournament model factors previous ratings into current ratings. This
          // leads to more gender disparity at the top organizational level.
          employee.addScore(score);
        } else {
          // The contest model ignores previous scores, leading to more balance
          // between men and women at the top level.
          employee.resetScore(score);
        }
      }

    }
  }

  private void promote() {
    for (int seniorLevel = LEVELS - 1, juniorLevel = LEVELS - 2; seniorLevel > 0;
         seniorLevel--, juniorLevel--) {
      List<Employee> seniorEmployees = levelToEmployees.get(seniorLevel);
      List<Employee> juniorEmployees = levelToEmployees.get(juniorLevel);

      int numPromotions = Math.min(juniorEmployees.size(),
          NUM_EMPLOYEES_AT_EACH_LEVEL[seniorLevel] - seniorEmployees.size());

      // After sorting, index 0 has the lowest score
      Collections.sort(juniorEmployees);

      for (int i = 0, fromIndex = juniorEmployees.size() - 1; i < numPromotions;
           i++, fromIndex--) {
        seniorEmployees.add(juniorEmployees.remove(fromIndex));
      }
    }
  }

  private void hire() {
    List<Employee> entryLevelEmployees = levelToEmployees.get(0);
    int numHires = NUM_EMPLOYEES_AT_EACH_LEVEL[0] - entryLevelEmployees.size();

    for (int i = 0; i < numHires; i++) {
      entryLevelEmployees.add(new Employee(nextGender()));
    }
  }
}
