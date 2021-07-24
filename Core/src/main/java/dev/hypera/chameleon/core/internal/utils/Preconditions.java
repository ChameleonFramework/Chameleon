package dev.hypera.chameleon.core.internal.utils;

import java.util.function.Predicate;

public class Preconditions {

	public static void check(boolean exp, String errorMessage) {
		if (exp) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	public static void check(Object obj, Predicate<Object> predicate, String errorMessage) {
		if (predicate.test(obj)) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

}
