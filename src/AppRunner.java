import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private MoneyReceiver MoneyReceiver;
    private CardAcceptor cardAcceptor;
    private CoinAcceptor coinAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        cardAcceptor = new CardAcceptor(150);
        coinAcceptor = new CoinAcceptor(100);
        MoneyReceiver = setPaymentMethod();
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        if(isCoinAcceptor()){
            print("Монет на сумму: " + MoneyReceiver.getAmount());
        } else{
            print("Денег на карте: " + MoneyReceiver.getAmount());
        }

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (MoneyReceiver.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        if(isCoinAcceptor()){
            print(" a - Пополнить баланс");
        }
        showActions(products);
        print(" o - поменять метод оплаты");
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action) && isCoinAcceptor()) {
            MoneyReceiver.setAmount(MoneyReceiver.getAmount() + 10);
            print("Вы пополнили баланс на 10");
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    MoneyReceiver.makePayment(products.get(i));
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else if("o".equalsIgnoreCase(action)){
                changePaymentMethod();
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                chooseAction(products);
            }
        }


    }

    private void changePaymentMethod() {
        if(this.MoneyReceiver instanceof CoinAcceptor){
            this.MoneyReceiver = this.cardAcceptor;
        }
        else{
            this.MoneyReceiver = this.coinAcceptor;
        }
    }

    private boolean isCoinAcceptor(){
        if(this.MoneyReceiver instanceof CoinAcceptor){
            return true;
        }
        return false;
    }

    private MoneyReceiver setPaymentMethod() {
        print("Выберите начальный метод оплаты: ");
        print(" a - оплата картой");
        print(" b - оплата монетами");
        String action;
        while(true){
            action = fromConsole().substring(0, 1);
            if ("a".equalsIgnoreCase(action)) {
                return this.cardAcceptor;
            } else if ("b".equalsIgnoreCase(action)) {
                return this.coinAcceptor;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
            }
        }

    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
