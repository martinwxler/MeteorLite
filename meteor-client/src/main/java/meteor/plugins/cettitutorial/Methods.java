package meteor.plugins.cettitutorial;

public class Methods {

	public enum GameMode {
		REGULAR("Regular"),
		IRONMAN("Ironman"),
		HARDCORE_IRONMAN("Hardcore Ironman"),
		ULTIMATE_IRONMAN("Ultimate Ironman");

		public final String name;

		@Override
		public String toString() {
			return name;
		}

		GameMode(String name) {
			this.name = name;
		}
	}
}
