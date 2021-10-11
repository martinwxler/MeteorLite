package meteor.plugins.cettitutorial.tasks;

import com.questhelper.quests.mourningsendparti.MourningsEndPartI;
import lombok.extern.java.Log;
import meteor.PluginTask;
import meteor.eventbus.Subscribe;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.input.Keyboard;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.cettitutorial.CettiTutorialConfig;
import meteor.plugins.cettitutorial.CettiTutorialPlugin;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static osrs.Client.logger;

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

		if (Game.getClient().getWidget(558, 7) != null) {
			if (Game.getClient().getWidget(558, 12).getText().contains("*")) {
				if (Game.getClient().getWidget(558, 12).getText().equals("*")) {
					Keyboard.type(config.userName()); // CHANGE TO CONFIG

				} else if (Game.getClient().getWidget(558, 13).getText().toLowerCase().contains("try clicking one of our suggestions")) {
					int selectName = new Random().nextInt((17 - 15) + 1) + 15;
					logger.info("Setting name to " + selectName);
					Game.getClient().getWidget(558, selectName).interact("Set name"); // not working for some reason
					Time.sleep(1000, 2000);

				} else if (Game.getClient().getWidget(558, 13).getText().toLowerCase().contains("you may set this name now")) {
					Game.getClient().getWidget(558, 19).interact("Set name");
					Time.sleep(1000, 2000);

				} else {
					Game.getClient().getWidget(558, 18).interact("Look up name");
					Time.sleep(1000, 2000);
				}
			} else {
				Game.getClient().getWidget(558, 7).interact("Enter name");
				Time.sleep(400, 600);
			}
		}
	}

	private void setFemale() {
		if (config.setFemale()) {
			if (Game.getClient().getWidget(679, 66).getActions().contains("Female")) {
				logger.info("Selecting Female");
				Game.getClient().getWidget(679, 66).interact("Female");
			}
		}
	}

	private void setRandomAppearance() {


		// TODO: Remove beard (17) from appearance list if female
		if (config.randomAppearance()) {
			List<Integer> appearanceOptions = Arrays.asList(13, 17, 21, 25, 29, 33, 37, 44, 48, 52, 56, 60);
			for (int option : appearanceOptions) {
				int random = new Random().nextInt((30 - 1) + 1) + 1;
				for (int i = 0; i < random; ++i) {
					Game.getClient().getWidget(679, option).interact("Select");
					Time.sleep(150, 300);
				}
			}
		}
	}

	private void confirmAppearance() {
		if (Game.getClient().getWidget(679, 2) != null) {
			setFemale();
			setRandomAppearance();
			Game.getClient().getWidget(679, 68).interact("Confirm");
			Time.sleep(600, 800);
		}
	}

	@Override
	public int execute() {
		logger.info("Creating Character");
		enterUsername();
		confirmAppearance();
		return 1000;
	}
}
