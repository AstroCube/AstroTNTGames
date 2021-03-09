package net.astrocube.tnt.lobby.statistic;

public interface MoneyTransactionHandler {

    int getActualMoney(String player);

    String getFormattedMoney(String player);

    void addMoney(String player, int quantity);

    void withdrawMoney(String player, int quantity);

}
