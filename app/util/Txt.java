package util;

import controllers.Application;

public class Txt {

	public static String UnityRichText2HTML(String unityRichText) {
		return unityRichText.replace("\n", "<br>");
	}

	public static String Quest() {
		return Application.getLocalPortal().getQuestNameSg();
	}

	public static String NewQuest() {
		String genus = Application.getLocalPortal().getQuestNameGenus();
		switch (genus) {
			case "die":
			case "f":
				return "Neue " + Application.getLocalPortal().getQuestNameSg();
			case "der":
			case "m":
				return "Neuer " + Application.getLocalPortal().getQuestNameSg();
			case "das":
			case "n":
			default:
				return "Neues " + Application.getLocalPortal().getQuestNameSg();
		}
	}

	public static String NewQuest_Accusative() {
		String genus = Application.getLocalPortal().getQuestNameGenus();
		switch (genus) {
			case "die":
			case "f":
				return "Neue " + Application.getLocalPortal().getQuestNameSg();
			case "der":
			case "m":
			case "das":
			case "n":
			default:
				return "Neuen " + Application.getLocalPortal().getQuestNameSg();
		}
	}

	public static String TheQuest() {
		String genus = Application.getLocalPortal().getQuestNameGenus();
		switch (genus) {
			case "die":
			case "f":
				return "Die " + Application.getLocalPortal().getQuestNameSg();
			case "der":
			case "m":
				return "Der " + Application.getLocalPortal().getQuestNameSg();
			case "das":
			case "n":
			default:
				return "Das " + Application.getLocalPortal().getQuestNameSg();
		}
	}

	public static String thisQuest_Dative() {
		String genus = Application.getLocalPortal().getQuestNameGenus();
		switch (genus) {
			case "die":
			case "f":
				return "der " + Application.getLocalPortal().getQuestNameSg();
			case "der":
			case "m":
			case "das":
			case "n":
			default:
				return "dem " + Application.getLocalPortal().getQuestNameSg();
		}
	}

	public static String ArbitraryQuest() {
		String genus = Application.getLocalPortal().getQuestNameGenus();
		switch (genus) {
			case "die":
			case "f":
				return "Beliebige " + Application.getLocalPortal().getQuestNameSg();
			case "der":
			case "m":
			case "das":
			case "n":
			default:
				return "Beliebiges " + Application.getLocalPortal().getQuestNameSg();
		}
	}

}
