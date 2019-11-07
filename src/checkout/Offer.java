package checkout;

import java.time.LocalDate;

abstract class Offer {
    private LocalDate expirationDate;

    Offer(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    boolean notExpired() {
        return LocalDate.now().isBefore(expirationDate);
    }

    abstract void apply(Check check);
}
