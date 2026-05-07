package model;

public class CoinAcceptor extends MoneyReceiver {
    @Override
    public void makePayment(Product product) {
        if(this.amount >= product.getPrice()){
            this.amount -= product.getPrice();
            System.out.printf("Вы купили %s%n", product.getName());
        }
    }

    public CoinAcceptor(int amount) {
        this.amount = amount;
    }
}
