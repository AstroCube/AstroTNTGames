package net.astrocube.tnt.shared.money;

import com.google.inject.Singleton;

@Singleton
public class CoreMoneyTransactionHandler implements MoneyTransactionHandler {

    @Override
    public int getActualMoney(String player) {
        return 0;
    }

    @Override
    public String getFormattedMoney(String player) {
        return "0";
    }

    @Override
    public void addMoney(String player, int quantity) {

    }

    @Override
    public void withdrawMoney(String player, int quantity) {

    }

}
