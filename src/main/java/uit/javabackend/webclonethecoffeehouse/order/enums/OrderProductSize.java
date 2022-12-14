package uit.javabackend.webclonethecoffeehouse.order.enums;

public enum OrderProductSize {
    NHO(0),
    TRUNG_BINH(6000),
    LON(10000),
    None(0);
    private final int price;

    OrderProductSize(int price){

        this.price = price;
    }

    public int GetPrice(){
        return price;
    }
    public boolean IsEmpty(){
        return this.equals(OrderProductSize.None);
    }
    public boolean Compare(int price){
        return this.price == price;
    }

    public static OrderProductSize GetValue(int _price)
    {
        OrderProductSize[] sizes = OrderProductSize.values();
        for(int i = 0; i < sizes.length; i++)
        {
            if(sizes[i].Compare(_price))
                return sizes[i];
        }
        return OrderProductSize.None;
    }


}
