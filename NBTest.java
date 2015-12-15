import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

public class NBTest{
    public static LinkedHashMap<String, Integer> wordMap = new LinkedHashMap<String, Integer>();
    public static int cAny = 0;
    public static int vocabSize = 0;
    public static LinkedHashMap<String, Integer> cLabels = new LinkedHashMap<String, Integer>();
    public static LinkedHashMap<String, Integer> cAnyWord = new LinkedHashMap<String, Integer>();
    //split the text and convert it into documents with comma separated instances and removed 1 and 2 characters words
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

    //  // function to print hashtable
    // static void outputHashTable(LinkedHashMap<String, Integer> C){
    //     for (String key : C.keySet()) {
    //        System.out.println(key + " " + C.get(key));
    //     }        
    // }

    public static void main(String[] args) throws IOException {
        // command line parameter
        if(args.length != 1) {
            System.out.println("Invalid command line, exactly one argument required");
            System.exit(1);
        }
        
        String[] labels = {"CCAT", "ECAT", "GCAT", "MCAT"};

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        // to store unique words and used linked to maintain the order
        LinkedHashSet<String> fullVocab = new LinkedHashSet<String>();
        String consoleCurDoc = null;

        // stroe the total no. of instances of all class
        String[] cAnyString = (consoleInput.readLine()).split("\\s");
        cAny = Integer.parseInt(cAnyString[0]);
        
        while((consoleCurDoc = consoleInput.readLine()) != null) {
            String[] splitConsoleCurDoc = consoleCurDoc.split("\\s");
            String tempMessage = splitConsoleCurDoc[0];

            if (consoleCurDoc.contains("A=")) {
                vocabSize++;
            }
            // System.out.println(tempMessage);
            if(consoleCurDoc.contains(",")){
                String[] tempWord = consoleCurDoc.split("\\s");
                String temp = tempWord[0];
                // System.out.println(temp);
                wordMap.put(temp, Integer.parseInt(tempWord[1]));
            }
            
            // store labels count in cLabels
            if (consoleCurDoc.contains("CCAT ") | consoleCurDoc.contains("ECAT ") | consoleCurDoc.contains("GCAT ") | consoleCurDoc.contains("MCAT ")) {
                String[] tempCLabel = consoleCurDoc.split("\\s");
                cLabels.put(tempCLabel[0], Integer.parseInt(tempCLabel[1]));
            }

            if (consoleCurDoc.contains("*")) {
                String[] tempAnyWordLabel = consoleCurDoc.split("\\s");
                cAnyWord.put(tempAnyWordLabel[0], Integer.parseInt(tempAnyWordLabel[1]));
            }
        }
        // System.out.println(vocabSize);
        // vocabSize = fullVocab.size();

        // outputHashTable(wordMap);
        // System.out.println(cAny);
        // outputHashTable(cLabels);
        // outputHashTable(cAnyWord);
        // System.out.println(vocabSize);   


        FileInputStream fstream = new FileInputStream(args[0]);
        BufferedReader fileInput = new BufferedReader(new InputStreamReader(fstream));

        Double maxProbab = Double.NEGATIVE_INFINITY;
        String maxLabel = "";
        String fileCurDoc = null;
        int percentCount = 0;
        int totalDocs = 0;

        while((fileCurDoc = fileInput.readLine()) != null) {
            String[] splitFileCurDoc = fileCurDoc.split("\t");
            String splitLabelCurDoc = splitFileCurDoc[0];
            Vector<String> indivTestWord = tokenizeDoc(splitFileCurDoc[1]);
            maxProbab = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < labels.length; i++) {
                Double prior = 0.0;
                prior = Math.log( ((double)cLabels.get(labels[i]) + 1) / ((double)cAny + 1) );

                Double probab = 0.0;
                for (String word : indivTestWord ) {
                    if (wordMap.containsKey(labels[i] + "^," + word)) {
                        probab += Math.log( ( ((double)wordMap.get(labels[i] + "^," + word) + (1)) / ( (double)cAnyWord.get(labels[i] + "*") + 1 ) ));
                    }else{
                        probab += Math.log( (1) / ( (double)cAnyWord.get(labels[i] + "*") + 1 ) ); 
                    }
                }
                probab += prior;
                // System.out.println(probab);
                if(probab > maxProbab){
                    maxProbab = probab;
                    maxLabel = labels[i];
                }
            }// end of for loop
            System.out.println(maxLabel + "\t" + maxProbab);
            if (splitLabelCurDoc.contains(maxLabel)) {
                percentCount++;
            }
            totalDocs++;
        }// end of while
        Double percentCorrect = ((double)percentCount / totalDocs * 100);
        fileInput.close();
        System.out.println("\nPercent Correct: " + percentCount + "/" + totalDocs + " = "+  percentCorrect + "%");
    }
}



