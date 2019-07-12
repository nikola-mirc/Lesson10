package exercises;

import java.util.List;

import examples.FileHelper;

public class Palindrome {

	public List<String> loadWords() {
		return FileHelper.loadFileContentsIntoArrayList("resource/words.txt");
	}

	public boolean isListed(String word) {
		return FileHelper.loadFileContentsIntoArrayList("resource/words.txt").contains(word);
	}

	public boolean isPalindrome(String word) {
		return word.equals(new StringBuffer(word).reverse().toString());
	}

}
