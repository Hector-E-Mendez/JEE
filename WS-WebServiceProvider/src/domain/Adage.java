package domain;

public class Adage {

	private String words;
	private int wordCount;
	private int id;
	
	public Adage(){}

	public String toString(){
		return String.format("%2d: ", id) + words + " -- " + wordCount + "words";
	}
	
	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
		this.wordCount = words.trim().split("\\s+").length;
	}

	public int getWordCount() {
		return wordCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
