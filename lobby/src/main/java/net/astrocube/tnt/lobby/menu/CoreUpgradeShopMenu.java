package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.MenuUtils;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.tnt.lobby.statistic.LobbyScoreboardProvider;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.configuration.PerkConfiguration;
import net.astrocube.tnt.shared.perk.configuration.PerkConfigurationCache;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CoreUpgradeShopMenu implements UpgradeShopMenu {

	private @Inject MessageHandler messageHandler;
	private @Inject TNTMenuHelper tntMenuHelper;
	private @Inject PerkConfigurationCache perkConfigurationCache;
	private @Inject TNTPerkProvider tntPerkProvider;
	private @Inject GenericHeadHelper genericHeadHelper;
	private @Inject Plugin plugin;
	private @Inject NumberFormat numberFormat;
	private @Inject UpgradeConfirmationMenu upgradeConfirmationMenu;
	private @Inject LobbyScoreboardProvider lobbyScoreboardProvider;

	public void open(
			Player player,
			int money,
			PerkConfiguration.Purchasable.Type type,
			ItemStack icon,
			Consumer<Player> goBack
	) {

		GUIBuilder builder = GUIBuilder.builder(
				messageHandler.get(player, "child.run.title")
		);

		tntMenuHelper.addDefaultButtons(
				builder,
				player,
				money,
				goBack
		);

		builder.addItem(
				genericHeadHelper.generateDecorator(
						icon,
						13
				)
		);

		try {

			TNTPerkManifest manifest = tntPerkProvider
					.getManifest(player.getDatabaseIdentifier())
					.orElseThrow(() -> new GameControlException("Manifest not found."));

			String typeSort = "";

			switch (type) {
				case SPLEEF_JUMP: {
					typeSort = manifest.getSpleefJumpTier();
					break;
				}
				case RUN_JUMP: {
					typeSort = manifest.getRunJumpTier();
					break;
				}
				case SPLEEF_SHOT: {
					typeSort = manifest.getSpleefTripleShot();
					break;
				}
				default: {
					break;
				}
			}

			List<PerkConfiguration.Purchasable> purchasableList = perkConfigurationCache.getCachedItems().stream()
					.filter(purchasable -> purchasable.getType() == type)
					.sorted(Comparator.comparing(PerkConfiguration.Purchasable::getOrder))
					.collect(Collectors.toList());

			boolean active = false;
			boolean upgradable = false;
			int index = 19;
			for (PerkConfiguration.Purchasable purchasable : purchasableList) {

				while (MenuUtils.isMarkedSlot(index)) {
					index++;
				}

				if (!active) {

					if (purchasable.getName().equalsIgnoreCase(typeSort)) {
						active = true;
					}

					addToGUI(
							builder,
							generatePurchasableGlass(
									purchasable,
									player,
									PurchasableGlass.OWNED,
									null
							),
							index
					);

					index++;
					continue;

				}

				if (!upgradable) {

					upgradable = true;

					if (purchasable.getPrice() > manifest.getMoney()) {

						addToGUI(
								builder,
								generatePurchasableGlass(
										purchasable,
										player,
										PurchasableGlass.MONEY,
										null
								),
								index
						);

					} else {

						addToGUI(
								builder,
								generatePurchasableGlass(
										purchasable,
										player,
										PurchasableGlass.NEXT,
										() -> open(
												player,
												money,
												type,
												icon,
												goBack
										)
								),
								index
						);

					}

					index++;
					continue;

				}

				addToGUI(
						builder,
						generatePurchasableGlass(
								purchasable,
								player,
								PurchasableGlass.INSUFFICIENT,
								null
						),
						index
				);

				index++;

			}

			player.openInventory(builder.build());

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while opening upgrade shop menu", e);
		}

	}

	private void addToGUI(GUIBuilder builder, ShapedMenuGenerator.BaseClickable baseClickable, int slot) {
		builder.addItem(
				ItemClickable.builder(slot)
						.setItemStack(baseClickable.getStack())
						.setAction(event -> {
							baseClickable.getClick().accept((Player) event.getWhoClicked());
							return true;
						}).build()
		);
	}

	private ShapedMenuGenerator.BaseClickable generatePurchasableGlass(
			PerkConfiguration.Purchasable purchasable,
			Player player,
			PurchasableGlass glassType,
			Runnable reject
	) {

		StringList list = messageHandler.replacingMany(
				player, purchasable.getType().getTranslation() + ".lore",
				"%jumps%", purchasable.getOrder(),
				"%price%", numberFormat.format(purchasable.getPrice())
		);

		String translation = messageHandler.get(player, glassType.getTranslation());

		list.add(translation);

		String title = purchasable.getType().getTranslation() + ".title." + purchasable.getName().toLowerCase(Locale.ROOT);

		ItemStack stack = genericHeadHelper.generateMetaAndPlace(
				new ItemStack(Material.STAINED_GLASS_PANE, 1, glassType.getColor()),
				messageHandler.get(
						player,
						title
				),
				list
		);

		return new ShapedMenuGenerator.BaseClickable() {
			@Override
			public Consumer<Player> getClick() {
				return (p) -> {

					switch (glassType) {
						case NEXT: {
							upgradeConfirmationMenu.open(
									player,
									() -> {


										try {
											TNTPerkManifest manifest =
													tntPerkProvider.getManifest(player.getDatabaseIdentifier())
															.orElseThrow(() -> new GameControlException("Manifest not found"));

											switch (purchasable.getType()) {
												case SPLEEF_JUMP: {
													manifest.setSpleefJumpTier(purchasable.getName());
													break;
												}
												case RUN_JUMP: {
													manifest.setRunJumpTier(purchasable.getName());
													break;
												}
												case SPLEEF_SHOT: {
													manifest.setSpleefTripleShot(purchasable.getName());
													break;
												}
												default: {
													throw new GameControlException("Purchasable type not found");
												}
											}

											manifest.setMoney(manifest.getMoney() - purchasable.getPrice());

											tntPerkProvider.update(player.getDatabaseIdentifier(), manifest);
											messageHandler.sendReplacingIn(player, AlertModes.INFO,
													"upgrade.confirm.announce",
													"%upgrade%", messageHandler.get(player, title)
											);
											lobbyScoreboardProvider.setup(player);
											player.closeInventory();

										} catch (Exception e) {
											plugin.getLogger().log(Level.SEVERE, "Error while updating user perks", e);
											messageHandler.sendIn(player, AlertModes.ERROR, "upgrade.error");
											player.closeInventory();
										}

									},
									reject,
									title
							);
							break;
						}
						case MONEY: {
							messageHandler.sendIn(player, AlertModes.ERROR, "upgrade.click.money");
							break;
						}
						case OWNED: {
							messageHandler.sendIn(player, AlertModes.INFO, "upgrade.click.owned");
							break;
						}
						default: {
							messageHandler.sendIn(player, AlertModes.INFO, "upgrade.click.insufficient");
							break;
						}
					}

				};
			}

			@Override
			public ItemStack getStack() {
				return stack;
			}
		};

	}

	@AllArgsConstructor
	private enum PurchasableGlass {
		OWNED("upgrade.action.owned", (short) 5),
		NEXT("upgrade.action.next", (short) 4),
		MONEY("upgrade.action.money", (short) 14),
		INSUFFICIENT("upgrade.action.insufficient", (short) 14);

		private final String translation;
		private final short color;

		public String getTranslation() {
			return translation;
		}

		public short getColor() {
			return color;
		}

	}


}
