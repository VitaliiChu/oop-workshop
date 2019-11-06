package checkout;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    private Check check;
    private List<Offer> offers;

    public void openCheck() {
        check = new Check();
        offers = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (check == null) {
            openCheck();
        }
        check.addProduct(product);
    }

    public Check closeCheck() {
        Check closedCheck = check;

        for (Offer offer : offers) {
            offer.apply(check);
        }
        check = null;

        return closedCheck;
    }

    public void useOffer(Offer offer) {
        offers.add(offer);
    }
}
