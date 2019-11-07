package checkout;

public interface Condition {
    boolean isAllowed(Check check);
}
