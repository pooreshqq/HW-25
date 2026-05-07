package model;

public abstract class MoneyReceiver {
    protected int amount;

    public abstract void makePayment(Product product);

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
