# Memory Restricted Naive Bayes
Implementation of Naive Bayes Algorithm for Text Classification using Java

Using the Reuters Corpus Data, which is a set of news stories split into a hierarchy of categories. Categorizing into only 4 categories.
CCAT, MCAT, GCAT, ECAT

- CCAT: Corporate/Industrial
- ECAT: Economics
- GCAT: Government/Social
- MCAT: Markets

#### How to Run
  **$** **cat RCV1.small_train.txt | java –Xmx128m NBTrain | sort –k1,1 | java –Xmx128m MergeCounts | java NBTest RCV1.small_test.txt**
