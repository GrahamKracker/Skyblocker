package de.hysky.skyblocker.stp.matchers;

import java.util.function.IntPredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record IntMatcher(int value, NumericOperator operator) implements IntPredicate {
	public static final Codec<IntMatcher> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("value").forGetter(IntMatcher::value),
			NumericOperator.CODEC.optionalFieldOf("operator", NumericOperator.EQUAL).forGetter(IntMatcher::operator))
			.apply(instance, IntMatcher::new));

	@Override
	public boolean test(int input) {
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
