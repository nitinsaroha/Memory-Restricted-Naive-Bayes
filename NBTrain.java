import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NBTrain{

    //split the text and convert it into documents with comma separated instances
    static Vector<String> tokenizeDoc(String cur_doc) {
        String[] words = cur_doc.split("\\s+");
        Vector<String> tokens = new Vector<String>();
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("\\W|_", "");
            if (words[i].length() > 0) {
                tokens.add(words[i].toLowerCase());
            }
        }
        return tokens;  
    }
    // function to print hashtable
    static void outputHashTable(LinkedHashMap<String, Integer> C){
        for (String key : C.keySet()) {
           System.out.println(key + " " + C.get(key));
        }        
    }

     // Main function
    public static void main(String[] args) throws IOException {
        // tokenizeDoc(cur_doc).toString()
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // variable to store current document
        String cur_doc = null;
        // classes or categories or labels
        String[] classes = {"CCAT", "ECAT", "GCAT", "MCAT"};
        // hashtable to store the words and counters
        LinkedHashMap<String, Integer> C = new LinkedHashMap<String, Integer>();
        // counter for all instances
        int totalClassInstances = 0;
        // read input per document
        while((cur_doc = input.readLine()) != null) {
            // split the document into labels and description
            String[] splitCurDoc = cur_doc.split("\t");
            // spit the label strings into labels array
            String[] labelsArray = splitCurDoc[0].split(",");
            // contains all the word of current doc using tokinize fucntion passing description part of current doc
            Vector<String> indivWord = tokenizeDoc(splitCurDoc[1]);
            // initialize the list to contain the labels we need for this assignment
            ArrayList<String> finalLabelsArray = new ArrayList<>();
            // remove unwanted labels according to the assignment
            for (int i = 0; i < classes.length; i++) {
                for (int j = 0; j < labelsArray.length; j++) {
                    if(classes[i].equals(labelsArray[j])){
                        finalLabelsArray.add(classes[i]);
                    }
                }
            }
           
            // print the standard output.
            for (int i = 0; i < finalLabelsArray.size(); i++) {
                // increment class instances variable
                totalClassInstances++;
                // variable and logic for stroing total instances of each classes
                String classTempLabel = finalLabelsArray.get(i);
                if (C.containsKey(finalLabelsArray.get(i))) {
                    int labelCount = C.get(classTempLabel);
                    C.put(classTempLabel, labelCount + 1);
                }else{
                    C.put(classTempLabel, 1);
                }

                // for each class calculating the no. of occurence of each word and store in hashtable C
                for (int j = 0 ; j < indivWord.size(); j++ ) {
                    String wordTempLabel = finalLabelsArray.get(i) + "^," + indivWord.get(j);

                    if (C.containsKey(wordTempLabel)) {
                        C.put(wordTempLabel, C.get(wordTempLabel) + 1);
                    }else{
                        C.put(wordTempLabel, 1);
                    }
                }

                // sending for calculating vocab size
                for (int k  = 0; k < indivWord.size(); k++) {
                    String onlyWordForVocab = "A=" + indivWord.get(k);

                    if (C.containsKey(onlyWordForVocab)) {
                        C.put(onlyWordForVocab, C.get(onlyWordForVocab) + 1);
                    }else{
                        C.put(onlyWordForVocab, 1);
                    }
                }
                
                // counter for total no. of words for each class or document
                String wordsPerClassLabel = finalLabelsArray.get(i) + "*";
                if(C.containsKey(wordsPerClassLabel)){
                    C.put(wordsPerClassLabel, C.get(wordsPerClassLabel) + indivWord.size());
                }else{
                    C.put(wordsPerClassLabel, indivWord.size());
                }
            }// end of for loop

            // since memory is limited, if heapsize is greater then 100mb print the hashtable C
            // and clear the hashtable
            long heapSize = Runtime.getRuntime().totalMemory();
            if(heapSize >= 90000000){
                outputHashTable(C);
                C.clear();
            }
        }// end of while loop
        //print the total of instances of all classes
        System.out.println(totalClassInstances + " " + "1");
        // print the hashtable
        outputHashTable(C);
    }//end of main function
}