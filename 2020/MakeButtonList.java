import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;

public class MakeButtonList {

	static String controllerNames = "m_driverController|m_operatorController";
	static HashMap buttonNames = new HashMap<String, String>();

	public static void main(String[] args){
		setUpButtonActions();
		try {
			String fileContents = Files.readString(Paths.get("src/main/java/frc/robot/RobotContainer.java"));
			Pattern classPattern = Pattern.compile("\\{(.*)\\}$", Pattern.DOTALL);
			Matcher classMatcher = classPattern.matcher(fileContents);

			if(classMatcher.find()){
				String classBody = classMatcher.group(0);
				classBody = classBody.replaceAll("\\s+//.*\n", "");
				// classBody = Pattern.compile("\\/\\*(.*?)\\*\\/", Pattern.DOTALL).matcher(classBody).replaceAll("");
				classBody = classBody.replaceAll("(?s)\\/\\*(.*?)\\*\\/", "");
				classBody = classBody.replaceAll("(\\s\\s)+", "");
				classBody = classBody.replaceAll("\\n", "");
				classBody = classBody.replaceAll("\\r", "");
				classBody = classBody.replaceAll("\\t", "");
				Pattern functionPattern = Pattern.compile("\\{([^\\{]*[^\\}])\\}");
				Matcher functionMatcher = functionPattern.matcher(classBody);
				while(functionMatcher.find()){
					String functionBody = functionMatcher.group(1);
					String[] lines = functionBody.split(";");
					for(String s : lines){
						System.out.println(s);
						
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	static String getFormattedButtonString(String name){
		if(buttonNames.containsKey(name)){
			return buttonNames.get(name);
		} else return name;
	}

	static void setUpButtonNames(){
		buttonNames.put("m_driverController", "Driver Controller");
		buttonNames.put("m_operatorController", "Operator Controller");
		buttonNames.put("JoystickButton", "Press the *** Button ");
		buttonNames.put("POVButton", "Press *** on the D-Pad ");
		buttonNames.put("StickLeft", "Left Stick");
		buttonNames.put("StickRight", "Right Stick");
		buttonNames.put("BumperLeft", "Left Bumper");
		buttonNames.put("BumperRight", "Right Bumper");

		// D-Pad POV angles
		buttonNames.put("0", "Up");
		buttonNames.put("90", "Left");
		buttonNames.put("180", "Down");
		buttonNames.put("270", "Right");
	}
}