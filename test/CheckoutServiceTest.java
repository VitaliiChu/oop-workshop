import checkout.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CheckoutServiceTest {

    private Product milk_7;
    private CheckoutService checkoutService;
    private Product bred_3;
    private LocalDate tomorrow;
    private LocalDate yesterday;
    private int discountProcent;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();
        checkoutService.openCheck();

        milk_7 = new Product(7, "Milk", Category.MILK);
        bred_3 = new Product(3, "Bred");
        tomorrow = LocalDate.now().plusDays(1);
        yesterday = LocalDate.now().minusDays(1);
        discountProcent = 50;
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
    void useOffer__addOfferPoints() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new AnyGoodsOffer(tomorrow, 6, 2));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(12));
    }

    @Test
    void useOffer__whenCostLessThanRequired__doNothing() {
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new AnyGoodsOffer(tomorrow, 6, 2));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(3));
    }

    @Test
    void useOffer__factorByCategory() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);

        checkoutService.useOffer(new FactorByCategoryOffer(tomorrow, Category.MILK, 2));
        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(31));
    }

    @Test
    void useOffer__useOfferBeforeAddProduct() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);

        checkoutService.useOffer(new AnyGoodsOffer(tomorrow, 15, 2));
        checkoutService.addProduct(bred_3);

        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(19));

        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);

        checkoutService.useOffer(new AnyGoodsOffer(tomorrow, 15, 2));
        checkoutService.addProduct(bred_3);

        check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(19));
    }

    @Test
    void useOffer__beforeAndAfterExpirationDate() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);

        checkoutService.useOffer(new AnyGoodsOffer(tomorrow, 15, 2));
        checkoutService.addProduct(bred_3);

        Check check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(19));

        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(milk_7);

        checkoutService.useOffer(new AnyGoodsOffer(yesterday, 15, 2));
        checkoutService.addProduct(bred_3);

        check = checkoutService.closeCheck();

        assertThat(check.getTotalPoints(), is(17));
    }

    @Test
    void useOffer__addPointsForSpecificProduct() {
        checkoutService.addProduct(milk_7);
        checkoutService.useOffer(new ProductOffer(tomorrow, milk_7, 2));
        checkoutService.addProduct(bred_3);
        Check check = checkoutService.closeCheck();
        assertThat(check.getTotalPoints(), is(12));
    }

    @Test
    void useOffer__applyDiscount() {
        checkoutService.addProduct(milk_7);
        checkoutService.addProduct(bred_3);
        checkoutService.useOffer(new DiscountOffer(tomorrow, milk_7, discountProcent));

        Check check = checkoutService.closeCheck();
        assertThat(check.getTotalCost(), is(6));
    }
}
