package checkout;

import java.time.LocalDate;

public class DiscountOffer extends Offer {
    Condition condition;
    DiscountRule rule;

    public DiscountOffer(LocalDate expirationDate, Condition condition, DiscountRule rule) {
        super(expirationDate);
        this.condition = condition;
        this.rule = rule;
    }

    @Override
    void apply(Check check) {
        if (notExpired() && condition.isAllowed(check)) {
            rule.calcDiscount(check);
        }
    }
}
