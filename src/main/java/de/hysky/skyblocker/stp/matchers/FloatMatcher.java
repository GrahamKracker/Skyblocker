package de.hysky.skyblocker.stp.matchers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.floats.FloatPredicate;

public record FloatMatcher(float value, NumericOperator operator) implements FloatPredicate {
	public static final Codec<FloatMatcher> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.FLOAT.fieldOf("value").forGetter(FloatMatcher::value),
			NumericOperator.CODEC.optionalFieldOf("operator", NumericOperator.EQUAL).forGetter(FloatMatcher::operator))
			.apply(instance, FloatMatcher::new));

	@Override
	public boolean test(float input) {
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
