import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class MergeCounts{

    // function to print the final model input
    static void outputPreviousKey(String previousKey, int sumForPreviousKey){
        if (previousKey != null) {
            System.out.println(previousKey + " " + sumForPreviousKey);
        }
    }

    // Main function
    public static void main(String[] args) throws IOException {

        BufferedReader inputfromNBTrain = new BufferedReader(new InputStreamReader(System.in));
        // varibale to store input one line at a time
        String cur_input = null;
        // temporary varibale to store previous key
        String previousKey = null;
        // temporary variable to store count of key
        int sumForPreviousKey = 0;
        // take input one at a time
        while((cur_input = inputfromNBTrain.readLine()) != null){
            // split the input into key and count
            String[] split_input = cur_input.split("\\s+");
            // store the key into event
            String event = split_input[0];
            // store the current count into delta
            int delta = Integer.parseInt(split_input[1]);
            // logic to increment count of one key
            if(event.equals(previousKey)){
                sumForPreviousKey += delta;
            }else{
                // straight away output the key, the moment there is a mismatch with the next key
                outputPreviousKey(previousKey, sumForPreviousKey);
                //  store current key in previous key for comparison
                previousKey = event;
                sumForPreviousKey = delta;
            }
        }
        outputPreviousKey(previousKey, sumForPreviousKey);
    }
}
