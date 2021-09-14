package meteor.plugins.oneclickboner;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("oneclickboner")
public interface OneClickBonerConfig extends Config {
	@Range(textInput = true)
	@ConfigItem(
					keyName = "ID",
					name = "Bones ID",
					description = "The ID of the bones to use",
					position = -1
	)
	default int ID()
	{
		return 0;
	}

	@Range(textInput = true)
	@ConfigItem(
					keyName = "notedID",
					name = "Bones Noted ID",
					description = "The Noted ID of the bones to use",
					position = 0
	)
	default int notedID()
	{
		return 0;
	}

	@ConfigItem(
					keyName = "useLast",
					name = "Use last visited",
					description = "will goto the last visited house using the mailbox. If using your own POH uncheck this option",
					position = 1
	)
	default  boolean useLast(){
		return true;
	}
}
