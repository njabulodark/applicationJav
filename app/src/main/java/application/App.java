package application;

public class App {
    public static void main(String[] args) {
        Flight flight = new Flight();
        // String processName = "chrome.exe";
        String processName = "Firefox.exe";
        
        
        do {
            System.out.println("Starting the flight...");
            
            flight.run();
            // try {
            //     // Execute the taskkill command to terminate the specified process
            //     Process process = Runtime.getRuntime().exec("taskkill /F /IM " + processName);
    
            //     // Wait for the process to complete
            //     process.waitFor();
    
            //     // Check the exit value to determine if the process was terminated successfully
            //     if (process.exitValue() == 0) {
            //         System.out.println("Process " + processName + " terminated successfully.");
            //     } else {
            //         System.out.println("Failed to terminate process " + processName + ".");
            //     }
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }

        } while (flight.failure);

        // kill chrom for linux
        // String processName = "chrome"
        // try {
        //     Process process = Runtime.getRuntime().exec("pkill -f " + processName);
        //     process.waitFor();
        //     if (process.exitValue() == 0) {
        //         System.out.println("Process " + processName + " terminated successfully.");
        //     } else {
        //         System.out.println("Failed to terminate process " + processName + ".");
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        flight.run();

        // Ufs ufs = new Ufs();
        // ufs.run();

        // Uwc uwc = new Uwc();
        // uwc.run();

        // Cao cao = new Cao();
        // cao.run();

        // Nwu nwu = new Nwu();
        // nwu.run();
        // System.out.println( cao.application() );
        // boolean control = true;
        // while (control) {
        //     try {
        //         // String processName = "chrome.exe";

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
