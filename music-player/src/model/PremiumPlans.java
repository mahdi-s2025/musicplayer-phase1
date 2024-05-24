package model;

public enum PremiumPlans {
    DAYS_30(5, 30), DAYS_60(9, 60), DAYS_180(14, 180);

    private final int cost;
    private final int period;

    PremiumPlans(int cost, int period) {
        this.cost = cost;
        this.period = period;
    }

    public int getCost() {
        return cost;
    }
    public int getPeriod() {
        return period;
    }
}
