package net.astrocube.tnt.shared.perk;


public enum DoubleJumpTier {

    I(3), II(4), III(5), IV(6), V(7);

    private final int quantity;

    DoubleJumpTier(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

}
