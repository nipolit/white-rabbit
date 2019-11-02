# Usage info
## Build
To build an executable jar file run:
```
mvn clean install
```
## Run
A sample command to run the application:
```
java -Xmx4g -jar white-rabbit.jar -p "poultry outwits ants" -l 4 665e5bcb0c20062fe8abaaf4628bb154 23170acc097c24edb98fc5488ab033fe e4820b45d2277f3844eac66c903e84be
```
For more details on usage you can run:
```
java -jar white-rabbit.jar --help
```
#### Note
A meet-in-the-middle algorithm used by the application stores a lot of objects in memory. So you may need to grant a permission for claiming it to the JVM. 
E.g. add a parameter `-Xmx4g` to set heap size to 4GB.
# About the application
## Key features
The main feature of the solution is the usage of meet-in-the-middle algorithm for searching anagrams of a phrase. First, it precomputes all word combinations not longer than half of the given phrase. Then, it goes through precomputed combinations and checks if the current combination can be extended with another precomputed combination to form an anagram. To make this search efficient combinations are stored in a hash map mapped by a count of characters they consist of. So, the solution takes a combination, checks how many of each characters is it lacking and searches a complementary combination with a constant time cost.
Such approach is `N^(L/2)` is more efficient in finding all anagrams, than a brute-force algorithm. (`N` is a number of words in the dictionary and `L` is a number of letters in the phrase)

Another important feature of the solution is code quality.
* All classes in the application are highly focused on a single responsibility.
* All of the classes are immutable except for the builder classes and the class `Dictionary`, which allows to add words into it.
* The methods are made small, simple, given meaningful names and have very little to no arguments. You'd barely find a single nested loop or condition anywhere in the program.
* I used TDD throughout the whole development process, so it helped to lower coupling between objects and increase their cohesion. Also, it would help understand, what is each element intended to do. Nearly every method is covered by unit tests. The only exclusions are one simple getter, generated `hashCode()` method and the class `App`, which is used only to run the application.

## Implementation details
The solution doesn't operate with actual words, but instead it transforms words into a vector-like data structure which stores a count of each character of the word. It's represented by a class `CharCount`. Also, it doesn't operate directly with sentences - instead it works with ordered combinations of `CharCounts`. This approach significantly reduces the amount of data the algorithm has to go through to find all anagrams of a phrase.
* There are 99175 words present in the provided `wordlist`. 2691 of them can be formed by taking a subset from the letters present in the original phrase "poultry outwits ants" and rearranging them. These words correspond to 1376 distinct `CharCounts`.
* Using ordered combinations instead of phrases allows to consider each combination only once instead of up to `n!` times, where n is the number of words a combination consists of.
* When considering different continuations of a combination it's safe to discard all continuations, that break the combination order.

## Application flow:
1) The application is given a phrase, anagram word limit value and MD5 sums to find.
2) All combinations of `CharCounts`, that satisfy the following conditions, are generated and stored: 
   * A combination is not longer than half of a given phrase.
   * All letters used in a combination are present in a given phrase.
   * A combination has less words than a word limit.
3) The application checks if there is a single-word anagram of a given phrase.
4) The application finds all two-word anagrams of a given phrase.
5) If a phrase has even length the application goes through all combinations of half the given phrase length and checks whether there are complementary combinations present, so anagrams can be formed.
6) The application goes through all combinations shorter than half of a given phrase. It tries to extend each combination, so the result is longer, than half of the given phrase. Then it tries to form an anagram from the result and any complementary combinations. 
**At this point all anagrams are already found in a form of `CharCount` combinations.**
7) The application goes through every possible `CharCount` order in each `CharCount` combination representing an anagram of the given phrase.
8) It replaces each `CharCount` with every suitable word from a dictionary. The words are joined in a string.
9) It computes MD5 sum of each anagram and compares it to the given MD5 sums.

## Performance notes:
The following chart demonstrates the application performance on i5-4690 with 6GB heap size.
These data is gathered by running the command:
```
time java -Xmx6g -jar target/white-rabbit.jar -p "poultry outwits ants" -l {word-limit} 665e5bcb0c20062fe8abaaf4628bb154 23170acc097c24edb98fc5488ab033fe e4820b45d2277f3844eac66c903e84be
```
The application manages to find all anagrams pretty quickly, and the application flow steps from 6 to 8 can be improved.

| Word limit | Time to find every anagram | Time to resolve and check MD5 sum of every anagram | 
| :--------: | :------------------------: | :------------------------------------------------: |
|3|0m1,211s|1,5s|
|4|0m18,578s|36s|
|5|2m15,809s|48m|
|6|7m5,109s|34h|
|7|11m9,413s||
|8|14m14,718s||
|9|14m41,917s||
|10|14m17,534s||
|11|14m56,470s||
|12|14m52,256s||
|20 (no effective limit)|13m24,242s||
