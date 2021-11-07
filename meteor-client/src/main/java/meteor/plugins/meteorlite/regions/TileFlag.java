package meteor.plugins.meteorlite.regions;

import lombok.Value;

@Value
public class TileFlag {
	int x;
	int y;
	int z;
	int flag;
	int region;
}
