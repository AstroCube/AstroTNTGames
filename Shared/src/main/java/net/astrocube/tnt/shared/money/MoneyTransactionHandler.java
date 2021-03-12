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

    /**
     * Add balance to the user actual money.
     * @param player to add
     * @param quantity to add
     */
    void addMoney(String player, int quantity);

    /**
     * Remove balance to the user actual money.
     * @param player to add
     * @param quantity to add
     */
    void withdrawMoney(String player, int quantity);

}
