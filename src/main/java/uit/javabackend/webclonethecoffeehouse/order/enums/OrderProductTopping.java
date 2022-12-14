package uit.javabackend.webclonethecoffeehouse.order.enums;

public enum OrderProductTopping {
    KEM_PHO_MAI_MACCHIATO(10000),
    SHOT_ESPRESSO(10000),
    DAO_MIENG(10000),
    TRAI_VAI(10000),
    HAT_SEN(10000),
    TRAI_NHAN(10000),
    THACH_CA_PHE(10000),
    SOT_CARAMEL(10000),
    TRAN_CHAU_TRANG(10000),
    NONE(0);
    private final int price;

    OrderProductTopping(int price){

        this.price = price;
    }

    public int GetPrice(){
        return price;
    }
    public boolean IsEmpty(){
        return this.equals(OrderProductTopping.NONE);
    }
    public boolean Compare(int price){
        return this.price == price;
    }

    public static OrderProductTopping GetValue(int _price)
    {
        OrderProductTopping[] toppings = OrderProductTopping.values();
        for(int i = 0; i < toppings.length; i++)
        {
            if(toppings[i].Compare(_price))
                return toppings[i];
        }
        return OrderProductTopping.NONE;
    }


}
