package net.astrocube.tnt.shared.perk.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Set;

public interface PerkConfiguration {

    /**
     * @return list of tiers registered at configuration.
     */
    Set<Purchasable> getTiers();

    /**
     * Purchasable item for configuration
     */
    interface Purchasable {

        /**
         * @return name of the purchase
         */
        String getName();

        /**
         * @return type of the purchase.
         */
        Type getType();

        /**
         * @return order in line to be shown.
         */
        int getOrder();

        /**
         * @return quantity of the new item
         */
        int getQuantity();

        /**
         * @return price of the new item
         */
        int getPrice();

        /**
         * @return if is the default item for new users
         */
        boolean isDefault();

        /**
         * @return required {@link Purchasable}s to gain this one.
         */
        Set<String> getRequired();

        @Getter
        @AllArgsConstructor
        enum Type {

            RUN_JUMP("upgrade.run.double-jump", Material.DIAMOND_BOOTS),
            SPLEEF_JUMP("upgrade.spleef.double-jump", Material.DIAMOND_BOOTS),
            SPLEEF_SHOT("upgrade.spleef.triple-shot", Material.ARROW);

            private final String translation;
            private final Material material;
        }

    }

}
