package uit.javabackend.webclonethecoffeehouse.order.enums;

public enum OrderStatus {
    ORDERED("ordered"),
    PROCESSED("processed"),
    DELIVERED("delivered"),
    REFUNDED("refunded"),
    CANCELED("canceled"),
    ;

    OrderStatus(String value) {
    }

    private String value;
    public String getValue(){
        return value;
    }
}
