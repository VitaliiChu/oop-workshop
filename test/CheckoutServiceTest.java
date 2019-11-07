import checkout.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckoutServiceTest {

    private Product milk_7;
    private Product milk_8;
    private CheckoutService checkoutService;
    private Product bred_3;
    private LocalDate tomorrow;
    private LocalDate yesterday;
    private Discount milkDiscount;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();
        checkoutService.openCheck();

        milk_7 = new Product(7, "Milk", Category.MILK);
        milk_8 = new Product(8, "Milk", Category.MILK);
        bred_3 = new Product(3, "Bred");
        tomorrow = LocalDate.now().plusDays(1);
        yesterday = LocalDate.now().minusDays(1);
        milkDiscount = new Discount(milk_7, 50);
    }

    @Test
    void closeCheck__withOneProduct() {
        checkoutService.addProduct(milk_7);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(7));
    }

    @Test
    void closeCheck__withTwoProducts() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalCost(), is(10));
    }

    @Test
    void addProduct__whenCheckIsClosed__opensNewCheck() {
        checkoutService.addProduct(milk_7);
        Check milkCheck = checkoutService.closeCheck();
        assertThat(milkCheck.getTotalCost(), is(7));

        checkoutService.addProduct(bred_3);
        Check bredCheck = checkoutService.closeCheck();
        assertThat(bredCheck.getTotalCost(), is(3));
    }

    @Test
    void closeCheck__calcTotalPoints() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(10));
    }

    @Test
    void useOffer__addOfferPointsFlat() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new BonusOffer(tomorrow, new TotalCost(15), new Flat(2)));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(19));
    }

    @Test
    void useOffer__addOfferPointsFactor() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new BonusOffer(tomorrow, new TotalCost(15), new Factor(Category.MILK, 2)));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(31));
    }

    @Test
    void useOffer__addOConditionCostByCategory() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new BonusOffer(tomorrow, new CostByCategory(Category.MILK, 10), new Factor(Category.MILK, 2)));
        Check check = checkoutService.closeCheck();
        assertThat(check.getTotalPoints(), is(31));

        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new BonusOffer(tomorrow, new CostByCategory(Category.MILK, 10), new Factor(Category.MILK, 2)));
        check = checkoutService.closeCheck();
        assertThat(check.getTotalPoints(), is(10));
    }

    @Test
    void useOffer__addOfferDiscount() {
        checkoutService.addProduct(milk_8);
        checkoutService.addProduct(milk_8);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new DiscountOffer(tomorrow, new TotalCost(7), new Procent(milkDiscount)));
        Check check = checkoutService.closeCheck();
        assertThat(check.getTotalPoints(), is(11));
    }
}
