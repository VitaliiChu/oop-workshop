package checkout;

import java.time.LocalDate;

public abstract class Offer {
    private LocalDate expirationDate;

    Offer(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    private boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    protected abstract void chargePoints(Check check);

    void apply(Check check) {
        if (!isExpired()) {
            chargePoints(check);
        }
    }
}
