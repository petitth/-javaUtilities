package edu.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemUtil {

    private SystemUtil() { }

    /**
     * Launch a command.
     * @param cmd command in a string
     * @return command output
     * @throws IOException any io exception
     * @throws InterruptedException any interrupted exception
     */
    public static String launchCommand(String cmd) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmd);
        StringBuilder result = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        process.waitFor();
        return result.toString();
    }

    /**
     * Launch a command. This second version is more appropriated for arguments with blank spaces.
     * @param cmdArray command in a string array
     * @return command output
     * @throws IOException any io exception
     * @throws InterruptedException any interrupted exception
     */
    public static String launchCommand(String[] cmdArray) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmdArray);
        StringBuilder result = new StringBuilder();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        process.waitFor();
        return result.toString();
    }

}
