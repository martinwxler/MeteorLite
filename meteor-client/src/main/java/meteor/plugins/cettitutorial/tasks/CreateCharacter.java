package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.input.Keyboard;
import meteor.plugins.cettitutorial.CettiTutorialConfig;
import net.runelite.api.widgets.Widget;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateCharacter implements PluginTask {

	CettiTutorialConfig config;

	public CreateCharacter(CettiTutorialConfig config) {
		super();
		this.config = config;
	}

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) == 1;
	}

	private void enterUsername() {

		Widget setDisplayName = Game.getClient().getWidget(558, 2);
		Widget displayNameField = Game.getClient().getWidget(558, 7);
		Widget displayNameFieldText = Game.getClient().getWidget(558, 12);
		Widget displayNameAvailable = Game.getClient().getWidget(558,13);

		if (setDisplayName == null) {
			return;
		}

		if (displayNameField == null) {
			return;
		}

		if (displayNameFieldText == null) {
			return;
		}

		if (displayNameAvailable == null) {
			return;
		}

		String displayNameText = displayNameFieldText.getText();

		// initial name look up
		if (displayNameAvailable.getText().equals("Please look up a name to see whether it is available.")) {
			if (displayNameText.equals("*")) {
				displayNameField.interact(0);
				Keyboard.type(config.userName());

				Widget lookUpNameButton = Game.getClient().getWidget(558,18);

				if (lookUpNameButton == null) {
					return;
				}

				lookUpNameButton.interact(0);
				return;
			}
		}

		// name not available, select suggestion
		if (displayNameAvailable.getText().contains("Try clicking one of our suggestions, instead")) {
			int selectName = new Random().nextInt((17 - 15) + 1) + 15;

			//TODO: This widget doesn't interact properly and leaves the chosen name saying "Please wait..."
			Widget nameSuggestion = Game.getClient().getWidget(558, selectName);

			if (nameSuggestion == null) {
				return;
			}

			nameSuggestion.interact("Set name");
			return;
		}

		// name is available, set name
		if (displayNameAvailable.getText().contains("Great! The display name")) {

			Widget setNameButton = Game.getClient().getWidget(558, 19);
			if (setNameButton == null) {
				return;
			}

			setNameButton.interact(0);
		}
	}

	private void setFemale() {

		Widget femaleButton = Game.getClient().getWidget(679, 66);

		if (femaleButton == null) {
			return;
		}

		if (config.setFemale()) {
			if (femaleButton.getActions().contains("Female")) {
				femaleButton.interact("Female");
			}
		}
	}

	private void setRandomAppearance() {

		if (config.randomAppearance()) {
			List<Integer> appearanceOptions = Arrays.asList(13, 17, 21, 25, 29, 33, 37, 44, 48, 52, 56, 60);

			for (int option : appearanceOptions) {
				int random = new Random().nextInt((10 - 1) + 1) + 1;
				Widget arrowSelect = Game.getClient().getWidget(679, option);

				if (arrowSelect == null) {
					return;
				}

				if (config.setFemale()) {
					if (option == 17) {
						return;
					}
				}

				for (int i = 0; i < random; ++i) {
					arrowSelect.interact("Select");
				}
			}
		}
	}

	private void confirmAppearance() {
		Widget confirmAppearance = Game.getClient().getWidget(679, 68);

		if (confirmAppearance == null) {
			return;
		}

		setFemale();
		setRandomAppearance();
		confirmAppearance.interact("Confirm");
		Time.sleep(600);
	}

	@Override
	public int execute() {
		enterUsername();
		confirmAppearance();
		return 1000;
	}
}
