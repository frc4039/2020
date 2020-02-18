package frc.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeButtonList {

	static String controllerNames = "m_driverController|m_operatorController";
	static HashMap<String, String> buttonNames = new HashMap<String, String>();

	public static void main(String[] args){
		setUpButtonNames();
		try {
			String fileContents = Files.readString(Paths.get("src/main/java/frc/robot/RobotContainer.java"));
			Pattern classPattern = Pattern.compile("\\{(.*)\\}$", Pattern.DOTALL);
			Matcher classMatcher = classPattern.matcher(fileContents);
			ArrayList<String> controlList = new ArrayList<String>();

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
						// System.out.println(s);
						Matcher controllerMatcher = Pattern.compile("(m_operatorController|m_driverController)").matcher(s);
						if(controllerMatcher.find()){
							String controller = controllerMatcher.group(1);
							Matcher triggerMatcher = Pattern.compile("(JoystickButton|POVButton)").matcher(s);
							if(triggerMatcher.find()){
								String trigger = triggerMatcher.group(1);
								Matcher buttonMatcher = Pattern.compile(trigger + "\\(.*?, (\\d+|Button\\.k.*\\.value)\\)").matcher(s);
								if(buttonMatcher.find()){
									String button = buttonMatcher.group(1);
									button = button.replace("Button.k", "");
									button = button.replace(".value", "");

									Matcher commandMatcher = Pattern.compile("new .*\\(.*\\).*\\.(.*)\\(new (.*)\\((.*)\\)\\)").matcher(s);
									if(commandMatcher.find()){
										String action = commandMatcher.group(1);
										String command = commandMatcher.group(2);
										String commandArgsS = commandMatcher.group(3);
										// System.out.printf("%s %s %s %s %s %s\n", getFormattedButtonString(controller), getFormattedButtonString(trigger), getFormattedButtonString(button), getFormattedButtonString(action), command, commandArgsS);

										String[] commandArgs = commandArgsS.replace(" ", "").split(",");
										ArrayList<String> commandsss = new ArrayList<String>();
										for(String arg : commandArgs){
											if(!arg.startsWith("m_")){
												commandsss.add(arg);
											}
										}
										if(commandsss.size() > 0){
											String finalArgs = String.join(", ", commandsss);
											String finalFunction = String.format("%s, %s %s: %s (%s)", getFormattedButtonString(controller), getFormattedButtonString(trigger).replace("***", getFormattedButtonString(button)), getFormattedButtonString(action), command, finalArgs);
											controlList.add(finalFunction);
										} else {
											String finalFunction = String.format("%s, %s %s: %s", getFormattedButtonString(controller), getFormattedButtonString(trigger).replace("***", getFormattedButtonString(button)), getFormattedButtonString(action), command);
											controlList.add(finalFunction);
										}
										
									}
								}
							}
						}
					}
				}

				// Print file
				BufferedWriter output = new BufferedWriter(new FileWriter("buttons.txt"));
				// Collections.sort(controlList);	
				Iterator<String> i = controlList.iterator();
				while(i.hasNext()){
					output.write(i.next());
					output.write("\r\n");
				}
				output.close();
				System.out.println("Button mapping file generated! Saved to ${rootDir}/buttons.txt");
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

	public static void setUpButtonNames(){
		// Controllers
		buttonNames.put("m_driverController", "Driver Controller");
		buttonNames.put("m_operatorController", "Operator Controller");

		// Buttons/triggers
		buttonNames.put("JoystickButton", "*** Button");
		buttonNames.put("POVButton", "D-Pad ***");
		buttonNames.put("StickLeft", "Left Stick");
		buttonNames.put("StickRight", "Right Stick");
		buttonNames.put("BumperLeft", "Left Bumper");
		buttonNames.put("BumperRight", "Right Bumper");

		// Trigger actions
		buttonNames.put("whileHeld", "(Hold)");
		buttonNames.put("whenHeld", "(Hold, interruptable)");
		buttonNames.put("toggleWhenPressed", "(Toggle on/off)");
		buttonNames.put("whileActiveOnce", "(Hold, interruptable)");
		buttonNames.put("whileActiveContinuous", "(Hold)");
		buttonNames.put("whenReleased", "(When button is released)");
		buttonNames.put("whenPressed", "(When pressed)");
		buttonNames.put("whenInactive", "(Runs when not held)");
		buttonNames.put("whenActive", "(When activated)");
		buttonNames.put("toggleWhenActive", "(Toggles on/off)");
		buttonNames.put("cancelWhenPressed", "(Cancels command when pressed)");
		buttonNames.put("cancelWhenActive", "(Cancels command when activated)");

		// D-Pad POV angles
		buttonNames.put("0", "Up");
		buttonNames.put("90", "Left");
		buttonNames.put("180", "Down");
		buttonNames.put("270", "Right");
	}
}