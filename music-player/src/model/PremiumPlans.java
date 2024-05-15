package model;

public enum PremiumPlans {
    DAYS_30(5), DAYS_60(9), DAYS_180(14);

    private final int cost;

    PremiumPlans(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
