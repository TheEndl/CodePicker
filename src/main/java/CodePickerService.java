import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CodePickerService extends Thread{
    private CodePicker codePicker;
    private int threadNumber;
    private String answer;
    private volatile static boolean isRunning = true;

    /**
     * @param threadNumber - number of thread
     */
    public CodePickerService(int threadNumber, CodePicker codePicker){
        this.threadNumber = threadNumber;
        this.codePicker = codePicker;
    }

    /**
     * @return - number of current thread
     */
    public int getThreadNumber() {
        return threadNumber;
    }

    /**
     * @param threadNumber - sets number to current thread
     */
    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    /**
     * @return - answer from searching
     */
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return - status of current thread
     */
    static boolean isRunning() {
        return isRunning;
    }

    /**
     * @param isRunning - sets status for current thread
     */
    static void setIsRunning(boolean isRunning) {
        CodePickerService.isRunning = isRunning;
    }

    /**
     * Searching wiki page by code
     */
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            org.apache.http.client.HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://snapsa9q.beget.tech/test.php");

            for(int i = threadNumber; i <= Integer.MAX_VALUE/codePicker.getThreadCount(); i++)
            {
                while(isRunning()){
                    List<NameValuePair> arguments = new ArrayList<NameValuePair>(1);
                    arguments.add(new BasicNameValuePair("code", String.valueOf(threadNumber)));
                    try {
                        post.setEntity(new UrlEncodedFormEntity(arguments));
                        HttpResponse response = client.execute(post);
                        answer = EntityUtils.toString(response.getEntity());
                        if (answer.toLowerCase().contains("wiki")){
                            System.out.println("Answer from Thread: "+Thread.currentThread().getName()+"\n" + answer);
                            setIsRunning(false);
                            CodePicker.stopService();
                        }
                        threadNumber = threadNumber +25;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}