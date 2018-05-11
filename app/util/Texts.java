package util;

public class Texts {

	public static String UnityRichText2HTML(String unityRichText) {
		return unityRichText.replace("\n", "<br>");
	}
}
