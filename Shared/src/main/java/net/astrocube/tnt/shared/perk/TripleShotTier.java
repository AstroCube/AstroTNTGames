package net.astrocube.tnt.shared.perk;


public enum TripleShotTier {

    I(1), II(2), III(3), IV(4), V(5);

    private final int quantity;

    TripleShotTier(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

}
