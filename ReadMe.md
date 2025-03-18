Project # 3: Project Name: Experiments with hashing
Author: Tristin Watkins
Class: CS321 Section # 2
Semester: 2


Overview
This program explores different hashing techniques by performing experiments with linear probing and double hashing.  The size of the hash table is determined by finding a twin prime number within a given range. The program tracks how many elements are inserted, how many are duplicates, and calculates the average number of probes for both linear probing and double hashing.

Reflection
This project provided a deep dive into hashing and collision resolution techniques.I struglled a lot on the project, It does not really make sense to me and the whole thing was confusing, I seeked help with the TA's and even with the help still dont fully understnad the project and why its not working. I got help from the TA's but we could not figure out why my output was not the same as the example and was off, the number of elements and duplicates is off, the other numbers are good and tracking well but for some reason the others are not, which is weird, So basically 1% of my code is not working and I have spent over 20 hrs working on this project and still couldnt get it to work. IDK why, I also struggled with the AWS part, not that the code would have worked anyway, but I watched the video and it was not working on my laptop for some reason, I use VScode, Do i need to change anything if that is the case, Like the video was confussing and maybe there should be different videos for the different uses of the code wrriting, But anyway this whole project is a bummer and a failure in my eyes, I hope I dont get a lot of my project points off, I know its not passing and failing the test, but its not passing due to 1% of the code not working, SO IDK what to say. I have a 95% in this class, I really hope I dont go to far down. Sorry for the long talk, I just am upset with myself that me and the TA's couldnt get this to work.



Compiling and Using
Compiling
Open your terminal or command prompt.
Navigate to the directory where the Java files are located.
Run the following command to compile the Java files:
bash
Copy code
javac *.java
This will compile all the .java files in the directory.
Running the Program
To run the program, use the following command:

bash
Copy code
java HashtableExperiment <tableSize> <loadFactor> <twinPrimeMin>
Parameters:
tableSize: The desired number of elements for the hash table. This value will be adjusted based on the twin prime.
loadFactor: The desired load factor for the hash table (a float between 0 and 1). This value will control the size of the hash table and how often resizing occurs.
twinPrimeMin: The minimum range for the twin prime generator, which will determine the hash table size.
For example, to run the program with a table size based on a twin prime number, a load factor of 0.5, and a minimum value for the twin prime of 0, you can run:

bash
Copy code
java HashtableExperiment 3 0.5 0
Input
The program reads a list of words to be inserted into the hash table. The input words are processed, and each word's insertion into the hash table is logged.

Results
During the experiments, we observed the following:

Linear Probing: After inserting all elements, the number of elements inserted and the number of probes required were calculated. In this method, collisions were handled by checking the next available index linearly.
Double Hashing: This method involved a second hash function to determine the step size for probing, which led to fewer probes than linear probing. It provided better performance in terms of average number of probes.
For a load factor of 0.5, a table size of 95791, and 1305930 elements, the results were as follows:

Linear Probing:
Hash table size: 47896
Total elements inserted: 1305930
Duplicate entries: 1258034
Average number of probes: 1.60
Double Hashing:
Hash table size: 47896
Total elements inserted: 1305930
Duplicate entries: 1258034
Average number of probes: 1.39
Sources Used
Lecture notes and course materials.
StackOverflow discussions on hash table implementations and collision handling:
StackOverflow Link
Java API documentation for java.util.HashMap.



Notes
This README.md template is using Markdown. Here is some help on using Markdown: markdown cheatsheet

Markdown can be edited and viewed natively in most IDEs such as Eclipse and VS Code. Just toggle between the Markdown source and preview tabs.

To preview your README.md output online, you can copy your file contents to a Markdown editor/previewer such as https://stackedit.io/editor.