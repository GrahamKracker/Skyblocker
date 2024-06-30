package de.hysky.skyblocker.stp.matchers;

import java.util.function.LongPredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LongMatcher(long value, NumericOperator operator) implements LongPredicate {
	public static final Codec<LongMatcher> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.LONG.fieldOf("value").forGetter(LongMatcher::value),
			NumericOperator.CODEC.optionalFieldOf("operator", NumericOperator.EQUAL).forGetter(LongMatcher::operator))
			.apply(instance, LongMatcher::new));

	@Override
	public boolean test(long input) {
		return switch (operator) {
			case EQUAL -> input == value;
			case NOT_EQUAL -> input != value;
			case LESS_THAN -> input < value;
			case LESS_THAN_OR_EQUAL_TO -> input <= value;
			case GREATER_THAN -> input > value;
			case GREATER_THAN_OR_EQUAL_TO -> input >= value;
		};
	}
}
