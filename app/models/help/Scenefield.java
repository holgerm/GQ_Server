package models.help;

import java.util.ArrayList;
import java.util.List;

public class Scenefield {

	private String name;
	private String type;
	private int sortingOrder = 0;
	private String defaultValue = "";
	private List<String> possibleValues = new ArrayList<String>();

	public Scenefield(String definition) {
		String parameters = definition.substring("scenefield(".length(),
				definition.length() - 1);
		String[] parts = parameters.split(",");

		if (parts.length < 2) {
			throw new IllegalArgumentException(
					"Scenefield has to contain at least name and type parameters.");
		}

		name = parts[0];
		type = parts[1];

		if (parts.length > 2) {
			try {
				sortingOrder = Integer.valueOf(parts[2]);
			} catch (NumberFormatException e) {
				System.out.print(e.getMessage());
			}
		}

		switch (type) {
		case "QuoteString":
		case "QuoteStringTextArea":
			defaultValue = "\"\"";
			break;
		case "String":
			defaultValue = "";
			break;
		case "int":
			defaultValue = "0";
			break;
		case "boolean":
			defaultValue = "false";
			break;
		default:
			defaultValue = "";
		}

		if (parts.length > 3) {
			defaultValue = parts[3];

			if (type.startsWith("QuoteString")) {
				defaultValue = "\"" + defaultValue + "\"";
			}
		}

		if (parts.length > 4) {
			String[] possValues = parts[4].split(";");
			for (int j = 0; j < possValues.length; j++) {
				possibleValues.add(possValues[j]);
			}
		}

	}

	public List<String> getPossibleValues() {
		return possibleValues;
	}

	public int getSortingOrder() {
		return sortingOrder;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

}
