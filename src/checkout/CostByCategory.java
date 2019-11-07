package checkout;

public class CostByCategory implements Condition {
    private int costByCategory;
    private Category category;

    public CostByCategory(Category category, int costByCategory) {
        this.costByCategory = costByCategory;
        this.category = category;
    }

    @Override
    public boolean isAllowed(Check check) {
        return check.getCostByCategory(category) > this.costByCategory;
    }
}
