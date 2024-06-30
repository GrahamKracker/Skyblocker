package de.hysky.skyblocker.stp.matchers;

import java.util.function.DoublePredicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DoubleMatcher(double value, NumericOperator operator) implements DoublePredicate {
	public static final Codec<DoubleMatcher> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.DOUBLE.fieldOf("value").forGetter(DoubleMatcher::value),
			NumericOperator.CODEC.optionalFieldOf("operator", NumericOperator.EQUAL).forGetter(DoubleMatcher::operator))
			.apply(instance, DoubleMatcher::new));

	@Override
	public boolean test(double input) {
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
