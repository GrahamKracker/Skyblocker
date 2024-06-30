package de.hysky.skyblocker.stp.predicates;

import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import de.hysky.skyblocker.SkyblockerMod;
import de.hysky.skyblocker.stp.SkyblockerPredicateType;
import de.hysky.skyblocker.stp.SkyblockerPredicateTypes;
import de.hysky.skyblocker.stp.matchers.RegexMatcher;
import de.hysky.skyblocker.stp.matchers.StringMatcher;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public record LorePredicate(int index, Optional<StringMatcher> stringMatcher, Optional<RegexMatcher> regexMatcher) implements SkyblockerTexturePredicate {
	public static final Identifier ID = Identifier.of(SkyblockerMod.NAMESPACE, "lore");
	public static final Codec<LorePredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codecs.NONNEGATIVE_INT.optionalFieldOf("index", -1).forGetter(LorePredicate::index),
			StringMatcher.CODEC.optionalFieldOf("stringMatcher").forGetter(LorePredicate::stringMatcher),
			RegexMatcher.CODEC.optionalFieldOf("regexMatcher").forGetter(LorePredicate::regexMatcher))
			.apply(instance, LorePredicate::new));

	@Override
	public boolean test(ItemStack stack) {
		if (stack.contains(DataComponentTypes.LORE)) {
			List<Text> lore = stack.get(DataComponentTypes.LORE).lines();

			if (index == -1) {
				for (Text line : lore) {
					String stringified = line.getString();

					if (stringMatcher.isPresent() && stringMatcher.get().test(stringified)) return true;
					if (regexMatcher.isPresent() && regexMatcher.get().test(stringified)) return true;
				}
			} else if (index < lore.size()) { //Check if the index is in bounds
				String stringified = lore.get(index).getString();

				if (stringMatcher.isPresent() && stringMatcher.get().test(stringified)) return true;
				if (regexMatcher.isPresent() && regexMatcher.get().test(stringified)) return true;
			}
		}

		return false;
	}

	@Override
	public SkyblockerPredicateType<?> getType() {
		return SkyblockerPredicateTypes.LORE;
	}
}
