/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package vazkii.botania.common.item.equipment.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelSwordItem;

import java.util.function.Consumer;

public class SoulscribeItem extends ManasteelSwordItem {

	public SoulscribeItem(Properties props) {
		super(BotaniaAPI.instance().getManasteelItemTier(), 3, -1.25F, props);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {
		if (!target.getLevel().isClientSide
				&& target instanceof EnderMan
				&& attacker instanceof Player player) {
			target.hurt(player.damageSources().playerAttack(player), 20);
		}

		stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		return true;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity player, int slot, boolean selected) {}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return amount;
	}
}
