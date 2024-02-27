package application;

public class App {
    public static void main(String[] args) {
        // String processName = "chrome.exe";
        // String processName = "firefox";

        // check operating system
        String os = System.getProperty("os.name").toLowerCase();
        
        boolean state = true;
        
        while (state) {

            if (os.indexOf("win") >= 0) {
                String processName = "firefox.exe";

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
            } else {
                try {
                        Process process = Runtime.getRuntime().exec("pkill -f firefox");
                        process.waitFor();
                        process = Runtime.getRuntime().exec("pkill -f java");
                        process.waitFor();
                        if (process.exitValue() == 0) {
                                System.out.println("Process firefox terminated successfully.");
                            } else {
                                    System.out.println("Failed to terminate process firefox.");
                                }
                } catch (Exception e) {
                        // e.printStackTrace();
                    }
                            
            }
            try {
                Flight flight = new Flight();
                flight.run();

                if (flight.failure == false) {
                    state = false;
                } else {
                    state = true;
                }
            } catch (Exception e) {
                // TODO: handle exception
                state = true;
            }
        }
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
