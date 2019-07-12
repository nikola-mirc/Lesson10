package exercises;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PalindromeTest {

	Palindrome testPalindrome = new Palindrome();
	String word = "aba";

	@Test
	void testLoadWords() {
		assertTrue(testPalindrome.loadWords().contains(word));
	}

	// 2. Test that a word exists in the dictionary

	@Test
	void testWordContainer() {
		assertTrue(testPalindrome.isListed(word));
	}

	// 3. Test that a word is a palindrome

	@Test
	void testIsPalindrome() {
		assertTrue(testPalindrome.isPalindrome(word));
	}
}
