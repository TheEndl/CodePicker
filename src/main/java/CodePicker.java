
import java.util.*;


public class CodePicker {
    private  int threadCount;
    static List<CodePickerService> codePickerServiceList = new ArrayList<CodePickerService>();

    /**
     * @param threadCount - count of threads for search
     */
    public CodePicker(int threadCount){
        this.threadCount = threadCount;
        for (int threadCounter = 0; threadCounter < this.threadCount; threadCounter++ ){
            codePickerServiceList.add(new CodePickerService(threadCounter, this));
        }
    }

    /**
     * @return - count of threads for searching
     */
    public  int getThreadCount() {
        return threadCount;
    }

    /**
     * @param threadCount - sets the count of thread for searching
     */
    public  void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * @return - returns list of CodePickerService threads
     */
    public static List<CodePickerService> getCodePickerServiceList() {
        return codePickerServiceList;
    }

    /**
     * @param codePickerServiceList - takes list of CodePickerServiceList threads
     */
    public static void setCodePickerServiceList(List<CodePickerService> codePickerServiceList) {
        CodePicker.codePickerServiceList = codePickerServiceList;
    }

    /**
     * stating search service
     */
    public void startService(){
            for(Thread thread : codePickerServiceList){
                try {
                    thread.start();
                    System.out.println(thread.getName() + " Started");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        System.out.println("Searching...");
    }

    /**
     * stopping search service
     */
    public static void stopService(){
        for (CodePickerService t : CodePicker.codePickerServiceList) {
            t.interrupt();
        }
    }
}


