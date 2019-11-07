package checkout;

public class TotalCost implements Condition {
    private int totalCost;

    public TotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean isAllowed(Check check) {
        return this.totalCost <= check.getTotalCost();
    }
}
