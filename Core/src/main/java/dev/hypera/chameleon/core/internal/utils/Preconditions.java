package dev.hypera.chameleon.core.internal.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Preconditions {

	public static void check(boolean exp, @NotNull String errorMessage) {
		if (exp) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void check(@Nullable Object obj, @NotNull Predicate<Object> predicate, @NotNull String errorMessage) {
		if (predicate.test(obj)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

}
