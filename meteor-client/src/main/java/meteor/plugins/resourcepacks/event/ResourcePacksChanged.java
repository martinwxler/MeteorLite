package meteor.plugins.resourcepacks.event;

import java.util.List;
import lombok.Value;
import meteor.plugins.resourcepacks.hub.ResourcePackManifest;

@Value
public class ResourcePacksChanged
{
	List<ResourcePackManifest> newManifest;
}
