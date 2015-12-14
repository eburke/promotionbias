package bias;

public enum PromotionModel {
  /**
   * Do not consider historical performance ratings when promoting.
   */
  CONTEST,

  /**
   * Consider historical performance ratings when promoting. This leads to more bias
   * at the top level of the organization.
   */
  TOURNAMENT
}
