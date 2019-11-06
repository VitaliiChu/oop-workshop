package checkout;

import java.time.LocalDate;

public abstract class Offer {
    private LocalDate expirationDate;

    Offer(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    private boolean notExpired() {
        return LocalDate.now().isBefore(expirationDate);
    }

    protected abstract void calcPoints(Check check);

    void apply(Check check) {
        if (notExpired()) {
            calcPoints(check);
        }
    }
}
