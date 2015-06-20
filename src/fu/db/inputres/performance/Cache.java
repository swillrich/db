package fu.db.inputres.performance;

import java.util.HashMap;

public abstract class Cache extends HashMap<String, String[]> {
	public abstract String getIdentifier(String... elements);

	public abstract String[] onIsNotInCacheContaining(String... elements);

	public String[] add(String... elements) {
		String identifier = getIdentifier(elements);
		String[] strings = get(identifier);
		if (strings != null) {
			return strings;
		} else {
			String[] onIsNotContaining = onIsNotInCacheContaining(elements);
			put(identifier, onIsNotContaining);
			return onIsNotContaining;
		}
	}

	public String[] get(String... elements) {
		String identifier = getIdentifier(elements);
		return get(identifier);
	}
}
