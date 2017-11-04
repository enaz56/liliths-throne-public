package com.lilithsthrone.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.lilithsthrone.controller.MainController;
import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.Properties;
import com.lilithsthrone.game.character.CharacterUtils;
import com.lilithsthrone.game.character.NameTriplet;
import com.lilithsthrone.game.character.PlayerCharacter;
import com.lilithsthrone.game.character.QuestLine;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.MapDisplay;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.story.CharacterCreation;
import com.lilithsthrone.game.dialogue.utils.OptionsDialogue;
import com.lilithsthrone.game.inventory.enchanting.TFEssence;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.CreditsSlot;
import com.lilithsthrone.world.Generation;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.LilayasHome;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @since 0.1.0
 * @version 0.1.88
 * @author Innoxia
 */
public class Main extends Application {

	public static Game game;

	public static MainController mainController;

	public static Scene mainScene;

	public static Stage primaryStage;
	public static String author = "Innoxia";

	public static final String VERSION_NUMBER = "0.1.88",
			VERSION_DESCRIPTION = "Early Alpha";

	public static final Image WINDOW_IMAGE = new Image("/com/lilithsthrone/res/images/windowIcon32.png");

	private static Properties properties;
	
	public static String patchNotes =
			
		"<h1 style='text-align:center;'>Version " + Main.VERSION_NUMBER + "</h1>"
//		+ "<h6 style='text-align:center;'><b style='color:" + Colour.GENERIC_TERRIBLE.toWebHexString() + ";'>Moderately Buggy Preview!</b></h6>"
//		"<h6 style='text-align:center;'><b style='color:" + Colour.GENERIC_BAD.toWebHexString() + ";'>Very-early Alpha!</b></h6>"
		
		+"<p><b style='color:"+Colour.GENERIC_EXCELLENT.toWebHexString()+";'>Important information:</b> <i>If you don't see a mini-map in the bottom-left corner of the screen after starting the game, please update your java!</i></p>"
		
		+ "<p>"
			+ "Hello once again everyone! :3"
		+ "</p>"
			
		+ "<p>"
			+ "I've been silly once again... I gave myself less goals for this version, so I thought 'I'll add in Halloween content as I have so much extra time!'."
			+ " The Halloween content then took up most of the time I had over these last two weeks, leaving me with 3 days to get v0.1.88's goals done..."
		+ "</p>"
			
		+ "<p>"
			+ "I've done as much as I could, but I didn't manage to get a lot of things I'd planned finished."
			+ " I've learned from my mistake, and I promise that I'm not going to give myself any 'extra hidden goals' next week."
			+ " I'll therefore be able to get everything from 0.1.88 finished off, as well as getting progress done on 0.1.89 done for the next public release. ;_;"
		+ "</p>"
		
		+ "<p>"
			+ "Thank you all for playing Lilith's Throne! And a very big thank you to all the people supporting me on Patreon! :3"
		+ "</p>"
		
		+ "<p>"
			+ "If you wanted to ask me any specific questions about the game, you can either find me on my blog, or on the Lilith's Throne Discord. You can find a link to the discord on my blog. ^^"
		+ "</p>"
			
		+ "</br>"

		+ "<list>"
		
			+ "<h6>v0.1.88</h6>"
			
			+"<li>Gameplay:</li>"
			+"<ul><b>Added:</b> Fluid addiction mechanics. Ingesting addictive fluid will increase your 'addiction level'; no negative effects are incurred if you keep satisfying your addiction, but if you go more than 24 hours without a 'fix', you'll start experiencing withdrawal symptoms.</ul>"
			+"<ul>Addictive fluids only work for cum at the moment, and don't have many unique lines of dialogue, but I'll add in addictive milk & girlcum, as well as dialogue, for the next release!</ul>"
			+"<ul>Increased inventory capacity from 24 to 32. (The inventory screen might get a little laggy when full, but I have a fix planned for this, which I'll try and implement for the next version.)</ul>"
				
			+"<li>Sex:</li>"
			+"<ul><b>Restructured:</b> Action menu during sex. Actions are now assigned to one of four sub-menus, in an attempt to tidy up the unwieldy mess of actions.</ul>"
			+"<ul>Created a new category of sex actions for your response to incoming partner orgasms. Moved 'Ask for creampie' and 'Ask for pullout' to this new category.</ul>"
			+"<ul>Removed the 'Use item' action, as for the last several versions you've been able to just open your inventory in sex instead.</ul>"
			+"<ul>Added 'Repeat action' where 'Use item' used to be.</ul>"
			+"<ul>Very slightly reduced the base arousal increase values per turn, to compensate for the arousal gains caused by fixing ongoing-actions applying arousal increases to both partners.</ul>"
			+"<ul>Added: Spread pussy action for both player & partner.</ul>"
			+"<ul>Added an action for NPCs to lubricate their fingers with saliva. (This won't result in the old bug of partners continuously self-sucking their fingers; they will only use this action to lubricate their fingers if they intend on fingering you.)</ul>"
			+"<ul>Added: Breast kissing action for both player & partner. (Nipple sucking to come soon.)</ul>"
			+"<ul>Having a request for a different position in sex no longer blocks you from requesting positions again.</ul>"
			+"<ul>Improved the AI's decision making and reaction to requests. They *should* now perform penetrative actions they otherwise wouldn't have if you've requested them to use a certain orifice.</ul>"
				
			+"<li>Clothing & Items:</li>"
			+"<ul>Added: Condom belt. (No femininity requirements, hip slot.) I'll add functionality to increase inventory capacity while wearing this in the future; I ran out of time to implement it for this version.</ul>"
			+"<ul>Improved 'Used condom' item to keep track of colour, cum, and cum's owner.</ul>"
			+"<ul>Added: Angel's Nectar (an addiction-curing potion).</ul>"
				
			+"<li>Other:</li>"
			+"<ul>Improved debug menu's clothing/weapons/items spawn system.</ul>"
			+"<ul>Adjusted all bottled essence rarities.</ul>"
			+"<ul>Improved clothing displacement detection, which, for example, should now allow you to shift aside your underwear after unzipping your trousers.</ul>"
			+"<ul>Brax's forced TF now affects your body size, muscle, hip size, and ass size.</ul>"
			+"<ul>Changed small-size penis descriptors from 'microscopic'->'tiny'->'average' to 'tiny'->'small'->'average'.</ul>"
			+"<ul>Pantyhose and arm-length gloves are no longer restricted to only black and white color variety.</ul>"
			+"<ul>Added grey colour to hair and fur.</ul>"
			+"<ul>Changed harpy hand descriptions from being a single opposable thumb to having two forefingers as well.</ul>"
			+"<ul>Phone menu is now disabled in character creation menus.</ul>"
			+"<ul>Slightly improved condom-drinking descriptions.</ul>"
				
			+"<li>Bugs:</li>"
			+"<ul>Fixed incorrect sex count stat iteration and post-orgasm arcane essence gain text (contributed by Rfpnj).</ul>"
			+"<ul>Minor typo fixes.</ul>"
			+"<ul>You no longer have to provide a surname when changing your name at City Hall.</ul>"
			+"<ul>Characters imported from versions prior to 0.1.86.5 should now no longer start with every fetish, and will spawn with basic clothing.</ul>"
			+"<ul>Fixed offspring introduction dialogue assuming that you were the mother, even if you were the father.</ul>"
			+"<ul>Dialogue title now correctly updates during sex to show you what position you're currently in.</ul>"
			+"<ul>Fixed strange floating point number rounding in slave management windows.</ul>"
			+"<ul>Fixed incorrect descriptions of clothing replacement.</ul>"
			+"<ul>Fixed bug where clothing coupldn't be equipped/removed even if access was available.</ul>"
			+"<ul>Fixed game freeze upon reaching 7 orgasms in sex.</ul>"
			+"<ul>NPCs should now be able to spawn in with androgynous bodies.</ul>"
			
		+"</list>"
		;
	
	public static String disclaimer = "<h6 style='text-align: center; color:"+Colour.GENERIC_ARCANE.toWebHexString()+";'>You must read and agree to the following in order to play this game!</h6>"

			+ "<p>This game is a <b>fictional</b> text-based erotic RPG." + " All content contained within this game forms part of a fictional universe that is not related to real-life places, people or events.</br></br>"

			+ " All of the characters that appear in this story are fictional entities who have given their consent to appear and act in this story."
			+ " If you find yourself concerned for the characters in the story then please be reassured that they are all consenting adults who are speaking lines from a script."
			+ " None of the characters portrayed within this game were or are being harmed in any way during the construction or execution of this game."
			+ " Every character in the game is at least 18 years of age (or is magically the legal age needed to appear in erotic literature in whatever country you are playing this).</br></br>"

			+ "By agreeing to this disclaimer and playing this game you agree to be exposed to graphic sexual and adult content that is presented as part of the game's fictional universe."
			+ " Such content consists of, but is not limited to; graphic depictions of sex, inter-species sex (with fantasy creatures), sexual transformation,"
			+ " rape fantasy/implied lack of consent, mild physical violence, sexual violence, and drug use.</br>"
			+ "Extreme fetish content such as gore/extreme violence, scat, and under/questionable age, is <i>not</i> a part of this game.</br></br>"

			+ "By agreeing to this disclaimer and playing this game you also agree that you are <b>at least 18 years of age</b>,"
			+ " or at least the legal age for you to purchase and view pornographic material in your country if that age is over 18.</br></br>"

			+ "As a final note, the creators of this game wish to stress that the content presented within is entirely fictional and does not reflect any of their personal views or opinions."
			+ " This game has been made in the spirit of creating a piece of artistic interactive literature, and it is imperative that you maintain a clear distinction between reality and the fictional events depicted in this game.</p>";
	
	public static List<CreditsSlot> credits = new ArrayList<>();

	// World generation:
	private static Generation gen;
	@Override
	public void start(Stage primaryStage) throws Exception {

		credits.add(new CreditsSlot("Anonymous", "", 0, 2, 9, 3));
		// A. L3 | C.C. E2 | G. E2 | JD E1 | MM E1 | V. R1 E2 | J R1 E1
		
		
		credits.add(new CreditsSlot("A", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Adhana Konker", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Lexi <3", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("48days", "", 0, 0, 2, 1));
		credits.add(new CreditsSlot("A", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Spaghetti Code", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("SchALLieS", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Argmoe", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Arkhan", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("b00marrows", "", 0, 1, 2, 0));
		credits.add(new CreditsSlot("B", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Blacktouch", "", 0, 0, 2, 1));
		credits.add(new CreditsSlot("Blue999", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("B", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("BRobort", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("BloodsailXXII", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Burt", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Calrak", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("CelestialNightmare", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Sxythe", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Lexi the slut", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Vmpireassassin (Chloe)", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("crashtestdummy", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Cursed Rena", "", 0, 0, 1, 1));
		credits.add(new CreditsSlot("Yllarius", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("John Scarlet", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("rinoskin", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Elmsdor", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Gr33n B3ans", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Farseeker", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Fenrakk101", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Fiona", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("ForeverFree2MeTaMax", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Niki Parks", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Georgio154", "", 0, 0, 1, 1));
		credits.add(new CreditsSlot("G", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("J B", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("suka", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Enigamatic Yoshi", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("kenshin5491", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Kestrel", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Kernog", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Knight-Lord Xander", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("L", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Pallid", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Mr L", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("loveless", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("KingofKings", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("waaaghkus", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Nightmare", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("AlphaOneBravo", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Max Nobody", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("N", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Nick LaBlue", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Niko", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Odd8Ball", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Rohsie", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("P.", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Torsten015", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("P H", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Pierre Mura", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Pokys", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("QQQ", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Rakesh", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Master's dumb bitch", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("R", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("redwulfen", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Horagen81", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Saladine", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("S", "", 0, 0, 1, 2));
		credits.add(new CreditsSlot("Shas'O Dal'yth Kauyon Kais Taku", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Crow Invictus", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Sorter", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Spookermen", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("S D", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("S", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("S", "", 0, 0, 0, 0));
		credits.add(new CreditsSlot("Swift Shot", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Tanner Daves", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("Terrance", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Jordan Aitken", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Timmybond24", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Torinir", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("triangleman", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Isidoros", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("Vaelin", "", 0, 0, 2, 0));
		credits.add(new CreditsSlot("V", "", 0, 0, 0, 2));
		credits.add(new CreditsSlot("iloveyouMiaoNiNi", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("William Brown", "", 0, 0, 3, 0));
		credits.add(new CreditsSlot("Wolfregis", "", 0, 0, 0, 3));
		credits.add(new CreditsSlot("Nelson Adams", "", 0, 0, 3, 0));
		
		credits.add(new CreditsSlot("A", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("A t A", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Avery", "", 0, 1, 1, 0));
		credits.add(new CreditsSlot("Baz Goldenclaw", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Blackcanine", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Runehood66", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Crimson", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Alatar", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("pupslut felix", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("glocknar", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Krissy2017", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Grakcnar", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("H", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("J", "", 0, 1, 1, 0));
		credits.add(new CreditsSlot("Bocaj91", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Krejil", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("J", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("J", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("J D", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Kiroberos", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Chris Turpin", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("L", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Muhaku", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Kvernik", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Party Commissar", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("S", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Sheltem", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("S", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("TalonMort", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("T", "", 0, 1, 1, 0));
		credits.add(new CreditsSlot("T. Garou", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("TreenVall", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("V", "", 0, 0, 0, 1));
		credits.add(new CreditsSlot("Whatever", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("W J O", "", 0, 0, 1, 0));
		credits.add(new CreditsSlot("Z", "", 0, 0, 1, 0));
		
		
		credits.sort(Comparator.comparing((CreditsSlot a) -> a.getName().toLowerCase()));
		
		
		Main.primaryStage = primaryStage;

		Main.primaryStage.getIcons().add(WINDOW_IMAGE);

		Main.primaryStage.setTitle("Lilith's Throne " + VERSION_NUMBER + " " + VERSION_DESCRIPTION);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));

		Pane pane = loader.load();

		mainScene = new Scene(pane);

		if (properties.lightTheme) {
			mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
		} else {
			mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
		}

		mainController = loader.getController();

		Main.primaryStage.setScene(mainScene);
		
		Main.primaryStage.show();
		
		try {
			Main.game = new Game();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));
		try {
			if (Main.mainScene == null) {
				pane = loader.load();
				Main.mainController = loader.getController();

				Main.mainScene = new Scene(pane);
				if (Main.getProperties().lightTheme)
					Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
				else
					Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
			}

			Main.primaryStage.setScene(Main.mainScene);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Main.game.setContent(new Response("", "", OptionsDialogue.MENU));
		
	}

	public static void main(String[] args) {
		
		// Create folders:
		File dir = new File("data/");
		dir.mkdir();
		dir = new File("data/saves");
		dir.mkdir();
		dir = new File("data/characters");
		dir.mkdir();
		
		// Load properties:
		if (new File("data/properties.xml").exists()) {
			try {
				properties = new Properties();
				properties.loadPropertiesFromXML();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			properties = new Properties();
			properties.savePropertiesAsXML();
		}
		
		launch(args);
	}
	
	/**
	 * Starts a completely new game. Runs a new World Generation.
	 */
	public static void startNewGame(DialogueNodeOld startingDialogueNode) {
		try {
			Main.game = new Game();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
//		Main.game.setPlayer(new PlayerCharacter("Player", "", 1, Gender.MALE, RacialBody.HUMAN, RaceStage.HUMAN, null, WorldType.CITY, Dominion.CITY_AUNTS_HOME));

		// Generate world:
		if (!(gen == null))
			if (gen.isRunning()) {
				gen.cancel();
			}

		gen = new Generation(WorldType.DOMINION);

		gen.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent t) {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lilithsthrone/res/fxml/main.fxml"));
				Pane pane;
				try {
					if (Main.mainScene == null) {
						pane = loader.load();
						Main.mainController = loader.getController();

						Main.mainScene = new Scene(pane);
						if (Main.getProperties().lightTheme)
							Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet_light.css");
						else
							Main.mainScene.getStylesheets().add("/com/lilithsthrone/res/css/stylesheet.css");
					}

					Main.primaryStage.setScene(Main.mainScene);

				} catch (Exception e) {
					e.printStackTrace();
				}
				Main.game.setPlayer(new PlayerCharacter(new NameTriplet("Player"), "", 1, Gender.M_P_MALE, RacialBody.HUMAN, RaceStage.HUMAN, null, WorldType.LILAYAS_HOUSE_GROUND_FLOOR, LilayasHome.LILAYA_HOME_ENTRANCE_HALL));

				Main.game.setActiveWorld(Main.game.getWorlds().get(WorldType.LILAYAS_HOUSE_GROUND_FLOOR), LilayasHome.LILAYA_HOME_ENTRANCE_HALL, false);
				Main.game.initNewGame(startingDialogueNode);
				
				Main.game.endTurn(0);
				//Main.mainController.processNewDialogue();
			}
		});
		new Thread(gen).start();
	}
	
	public static int getFontSize() {
		return properties.fontSize;
	}

	public static void setFontSize(int size) {
		properties.fontSize = size;
		properties.savePropertiesAsXML();
	}

	public static void quickSaveGame() {
		if (Main.game.isInCombat()) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Cannot quicksave while in combat!");
			
		} else if (Main.game.isInSex()) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Cannot quicksave while in sex!");
			
		} else if (Main.game.getCurrentDialogueNode().getMapDisplay()!=MapDisplay.NORMAL) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Can only quicksave in a normal scene!");
			
		} else {
			Main.getProperties().lastQuickSaveName = "QuickSave_"+Main.game.getPlayer().getName();
			saveGame("QuickSave_"+Main.game.getPlayer().getName(), true);
		}
	}

	public static void quickLoadGame() {
		String name = "QuickSave_"+Main.game.getPlayer().getName();
		
//		if(new File("data/saves/"+name+".lts").exists()) {
			loadGame(name);
//		} else {
//			loadGame(Main.getProperties().lastQuickSaveName);
//		}
	}

	public static void saveGame(String name, boolean allowOverwrite) {
		if (name.length()==0) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Name too short!");
			return;
		}
		if (name.length() > 32) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Name too long!");
			return;
		}
		if (!name.matches("[a-zA-Z0-9]+[a-zA-Z0-9' _]*")) {
			Main.game.flashMessage(Colour.GENERIC_BAD, "Incompatible characters!");
			return;
		}
		
		File dir = new File("data/");
		dir.mkdir();

		dir = new File("data/saves");
		dir.mkdir();
		
		boolean overwrite = false;
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, filename) -> filename.endsWith(".lts"));
			if (directoryListing != null) {
				for (File child : directoryListing) {
					if (child.getName().equals(name+".lts")){
						if(!allowOverwrite) {
							Main.game.flashMessage(Colour.GENERIC_BAD, "Name already exists!");
							return;
						} else {
							overwrite = true;
						}
					}
				}
			}
		}
		
		File file = new File("data/saves/"+name+".lts");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
//			long timeStart = System.nanoTime();
//			System.out.println(timeStart);
			
			try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			  oos.writeObject(Main.game);
			  oos.close();
			}
			
//			System.out.println("Difference: "+(System.nanoTime()-timeStart)/1000000000f);

			properties.lastSaveLocation = name;//"data/saves/"+name+".lts";
			properties.nameColour = Femininity.valueOf(game.getPlayer().getFemininityValue()).getColour().toWebHexString();
			properties.name = game.getPlayer().getName();
			properties.level = game.getPlayer().getLevel();
			properties.money = game.getPlayer().getMoney();
			properties.arcaneEssences = game.getPlayer().getEssenceCount(TFEssence.ARCANE);
			properties.race = game.getPlayer().getRace().getName();
			properties.quest = game.getPlayer().getQuest(QuestLine.MAIN).getName();

			properties.savePropertiesAsXML();


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if(Main.game.getCurrentDialogueNode() == OptionsDialogue.SAVE_LOAD) {
			if(overwrite) {
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()), Colour.GENERIC_GOOD, "Save game overwritten!");
			} else {
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()), Colour.GENERIC_GOOD, "Game saved!");
			}
		} else if(name.equals("QuickSave_"+Main.game.getPlayer().getName())){
			Main.game.flashMessage(Colour.GENERIC_GOOD, "Quick saved!");
		}
	}

	public static void loadGame(String name) {
		
		File file = new File("data/saves/"+name+".lts");
		
		if (file.exists()) {
			try {
				Main.game = new Game();
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				Game loadedGame = (Game) ois.readObject();
				ois.close();
				fin.close();
				Main.game = loadedGame;
				Main.game.reloadContent();
				if (Main.game.getCurrentDialogueNode().getMapDisplay() == MapDisplay.OPTIONS) {
					Main.mainController.openOptions();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static void deleteGame(String name) {
		File file = new File("data/saves/"+name+".lts");

		if (file.exists()) {
			try {
				file.delete();
				Main.game.setContent(new Response("", "", Main.game.getCurrentDialogueNode()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {
			Main.game.flashMessage(Colour.GENERIC_BAD, "File not found...");
		}
	}
	
	public static List<File> getSavedGames() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/saves");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".lts"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}

		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static List<File> getCharactersForImport() {
		List<File> filesList = new ArrayList<>();
		
		File dir = new File("data/characters");
		if (dir.isDirectory()) {
			File[] directoryListing = dir.listFiles((path, name) -> name.endsWith(".xml"));
			if (directoryListing != null) {
				filesList.addAll(Arrays.asList(directoryListing));
			}
		}

		filesList.sort(Comparator.comparingLong(File::lastModified).reversed());
		
		return filesList;
	}
	
	public static void importCharacter(File file) {
		if (file != null) {
			try {
				Main.game.setPlayer(CharacterUtils.startLoadingCharacterFromXML());
				Main.game.setPlayer(CharacterUtils.loadCharacterFromXML(file, Main.game.getPlayer()));
				
				Main.game.setRenderAttributesSection(true);
				Main.game.clearTextStartStringBuilder();
				Main.game.clearTextEndStringBuilder();
				Main.getProperties().setNewWeaponDiscovered(false);
				Main.getProperties().setNewClothingDiscovered(false);
				Main.getProperties().setNewItemDiscovered(false);
				Main.game.getPlayer().calculateStatusEffects(0);

				Main.game.initNewGame(CharacterCreation.START_GAME_WITH_IMPORT);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void saveProperties() {
		properties.savePropertiesAsXML();
	}
}
