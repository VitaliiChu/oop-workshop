package checkout;

public class Factor implements Reward {
    private Category category;
    private int factor;

    public Factor(Category category, int factor) {
        this.category = category;
        this.factor = factor;
    }

    @Override
    public void scorePoints(Check check) {
        int points = check.getCostByCategory(this.category);
        check.addPoints(points * (this.factor - 1));
    }
}
