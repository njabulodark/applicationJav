package application;

import application.Universities.Cao;
// import application.Universities.Nwu;
import application.Universities.Uj;
import application.Universities.Uwc;

public class Main {
    public static void main(String[] args) {
        String processName = "chrome.exe";

        try {
            // Execute the taskkill command to terminate the specified process
            Process process = Runtime.getRuntime().exec("taskkill /F /IM " + processName);

            // Wait for the process to complete
            process.waitFor();

            // Check the exit value to determine if the process was terminated successfully
            if (process.exitValue() == 0) {
                System.out.println("Process " + processName + " terminated successfully.");
            } else {
                System.out.println("Failed to terminate process " + processName + ".");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uwc uwc = new Uwc();
        uwc.run();

        // Cao cao = new Cao();
        // cao.run();

        // Nwu nwu = new Nwu();
        // nwu.run();
        // System.out.println( cao.application() );
        // boolean control = true;
        // while (control) {
        //     try {
        //         String processName = "chrome.exe";

        //         try {
        //             // Execute the taskkill command to terminate the specified process
        //             Process process = Runtime.getRuntime().exec("taskkill /F /IM " + processName);

        //             // Wait for the process to complete
        //             process.waitFor();

        //             // Check the exit value to determine if the process was terminated successfully
        //             if (process.exitValue() == 0) {
        //                 System.out.println("Process " + processName + " terminated successfully.");
        //             } else {
        //                 System.out.println("Failed to terminate process " + processName + ".");
        //             }
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }

        //         Uj uj = new Uj();
        //         uj.run();
        //         control = false;
        //     } catch (Exception e) {
        //         // TODO: handle exception
        //     }
            
        // }



    }
}
