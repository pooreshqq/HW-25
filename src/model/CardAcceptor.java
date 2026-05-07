package model;

import java.util.Scanner;

public class CardAcceptor extends MoneyReceiver{
    private Scanner scanner = new Scanner(System.in);

    public CardAcceptor(int amount) {
        this.amount = amount;
    }

    @Override
    public void makePayment(Product product) {
        if(this.amount >= product.getPrice()){
            enterCardInfo();
            this.amount -= product.getPrice();
            System.out.printf("Вы купили %s%n", product.getName());
        }
    }

    public void enterCardInfo(){
        String cardNumber;
        String cardPassword;
        while(true){
            System.out.print("Введите номер карты. Должно быть 16 цифр! - ");
            cardNumber = scanner.nextLine().trim();
            if(!cardNumber.isBlank()){
                if(cardNumber.length() == 16){
                    try{
                        Long cardNumberCheck = Long.parseLong(cardNumber);
                        break;
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                        System.out.println("Неправильный формат номера карты, введите еще раз");
                        continue;
                    } catch (Exception e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                        continue;
                    }
                }
                else{
                    System.out.println("Неправильная длина номера карты! Должно быть 16 цифр");
                    continue;
                }
            }
            else{
                System.out.println("Нельзя вводить пустую строку!");
                continue;
            }
        }

        while(true){
            System.out.println("Введите одноразовый пароль для этой карты: ");
            cardPassword = scanner.nextLine().trim();
            if(!cardPassword.isBlank()){
                break;
            }
            else{
                System.out.println("Поле ввода пароля не может быть пустым!");
            }
        }


    }
}
