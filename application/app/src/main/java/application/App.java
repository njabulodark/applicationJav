package application;

public class App {
    public static void main(String[] args) {
        // String processName = "chrome.exe";
        // String processName = "firefox";

        // check operating system
        String os = System.getProperty("os.name").toLowerCase();
        
        boolean state = true;
        
        while (state) {
            System.out.println("checking if the operating system is windows or not: " + os);
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
                    Process process = Runtime.getRuntime().exec("pkill firefox");
                    process.waitFor();
                    process = Runtime.getRuntime().exec("free -h && sudo sysctl -w vm.drop_caches=3 && sudo sync && echo 3 | sudo tee /proc/sys/vm/drop_caches && free -h\r\n");
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
                System.out.println("running flight");
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
    
    }
}
