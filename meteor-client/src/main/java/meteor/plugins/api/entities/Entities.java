package meteor.plugins.api.entities;

import net.runelite.api.SceneEntity;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public abstract class Entities<T extends SceneEntity> {
	protected abstract List<T> all(Predicate<? super T> filter);

	public List<T> all(String... names) {
		return all(x -> {
			if (x.getName() == null) {
				return false;
			}

			for (String name : names) {
				if (name.equals(x.getName())) {
					return true;
				}
			}

			return false;
		});
	}

	public List<T> all(int... ids) {
		return all(x -> {
			for (int id : ids) {
				if (id == x.getId()) {
					return true;
				}
			}

			return false;
		});
	}

	public T nearest(Predicate<? super T> filter) {
		return all(x -> x.getId() != -1 && filter.test(x)).stream()
						.min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(Players.getLocal())))
						.orElse(null);
	}

	public T nearest(String... names) {
		return nearest(x -> {
			if (x.getName() == null) {
				return false;
			}

			for (String name : names) {
				if (name.equals(x.getName())) {
					return true;
				}
			}

			return false;
		});
	}

	public T nearest(int... ids) {
		return nearest(x -> {
			for (int id : ids) {
				if (id == x.getId()) {
					return true;
				}
			}

			return false;
		});
	}
}
