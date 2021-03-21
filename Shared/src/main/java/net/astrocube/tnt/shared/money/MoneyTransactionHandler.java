package net.astrocube.tnt.shared.money;

public interface MoneyTransactionHandler {

    /**
     * @param player to find
     * @return actual money of the player
     */
    int getActualMoney(String player);

    /**
     * @param player to find
     * @return actual formatted money of the player
     */
    String getFormattedMoney(String player);

}
