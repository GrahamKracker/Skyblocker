package de.hysky.skyblocker.stp.predicates;

import com.mojang.serialization.Codec;

import de.hysky.skyblocker.SkyblockerMod;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.config.configs.GeneralConfig;
import de.hysky.skyblocker.stp.SkyblockerPredicateType;
import de.hysky.skyblocker.stp.SkyblockerPredicateTypes;
import de.hysky.skyblocker.utils.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public record DyedPredicate(boolean dyed) implements SkyblockerTexturePredicate {
	public static final Identifier ID = Identifier.of(SkyblockerMod.NAMESPACE, "dyed");
	public static final Codec<DyedPredicate> CODEC = Codec.BOOL.xmap(DyedPredicate::new, DyedPredicate::dyed);

	@Override
	public boolean test(ItemStack stack) {
		GeneralConfig config = SkyblockerConfigManager.get().general;
		String itemUuid = ItemUtils.getItemUuid(stack);

		return ItemUtils.getCustomData(stack).contains("dye_item") || config.customDyeColors.containsKey(itemUuid) || config.customAnimatedDyes.containsKey(itemUuid);
	}

	@Override
	public SkyblockerPredicateType<?> getType() {
		return SkyblockerPredicateTypes.DYED;
	}
}
