package checkout;

import java.time.LocalDate;

public class BonusOffer extends Offer {
    private Condition condition;
    private Reward reward;

    public BonusOffer(LocalDate expirationDate, Condition condition, Reward reward) {
        super(expirationDate);
        this.condition = condition;
        this.reward = reward;
    }


    @Override
    void apply(Check check) {
        if (notExpired() && condition.isAllowed(check)) {
            reward.scorePoints(check);
        }
    }
}
