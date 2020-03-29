package org.loremipsum;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.WordUtils;

public class LoremIpsum {

	private static String[] SOURCE = new String[]{
		"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
		"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
		"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."
	};
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private static Boolean INIT_WORDS = false;
	protected static Set<String> WORD_SET;
	private static String[] WORD_ARRAY;
	private static final String WORD_SEP = " ";
	private static final String PHRASE_SEP = ",";
	private static final String[] SEN_TERMS = new String[]{".",".",".",".",".",".",".",".","!","?"}; //Hack for having different punctuation
	private static final String LINE_TERM = "\n";
	
	static int DEF_PG_MAX_WORDS = 150;
	static int DEF_PG_MIN_WORDS = 50;
	
	static int DEF_SEN_MAX_WORDS = 12;
	static int DEF_SEN_MIN_WORDS = 4;

	protected static Set<String> splitWords(String... strings){
		Set<String> wordSet = new HashSet<String>();
		for(String s : strings){
			String[] words = s.toLowerCase().replaceAll("[,\\.?]", "").split("((\\s){1}?)");
			wordSet.addAll(Arrays.asList(words));
		}
		return wordSet;
	}
	
	public static void createCsvDocument(int lines, int wordsPerLine,OutputStream os) throws IOException {
		String doc = joinLines(makeLinesParts(lines, wordsPerLine, ",", null, null));
		os.write(doc.getBytes());
		os.flush();
	}
	
	public static void createCsvDocument(int lines, int wordsPerLine,File file)  {
		OutputStream fos = null;
		try {
			fos = new BufferedOutputStream(new FileOutputStream(file));
			createCsvDocument(lines, wordsPerLine, fos);
		} 
		catch (IOException e) {}
		finally {
			IOUtils.closeQuietly(fos);
		}
		
	}
	
	public static String createParagraph(int words) {
		return joinLine(createParagraphParts(words,WORD_SEP,PHRASE_SEP,SEN_TERMS));
	}
	
	public static String createParagraph() {
		return createParagraph(RANDOM.nextInt(DEF_PG_MAX_WORDS-DEF_PG_MIN_WORDS)+DEF_PG_MIN_WORDS);
	}
	
	public static String createSentence(int words) {
		return joinLine(createSentenceParts(words,true,true,WORD_SEP,PHRASE_SEP,SEN_TERMS));
	}
	
	public static String createSentence() {
		return createSentence(RANDOM.nextInt(DEF_SEN_MAX_WORDS-DEF_SEN_MIN_WORDS) + DEF_SEN_MIN_WORDS);
	}
	
	public static String createPhrase(int words) {
		return joinLine(createPhraseParts(words,WORD_SEP));
	}

	private static List<String> createParagraphParts(int parlen,String wordSep,String phraseSep,String[] senTerm) {
		List<String> parts = new ArrayList<String>();
		for (int i = 0; parlen > 0; i++) {
			int senlen = RANDOM.nextInt(DEF_SEN_MAX_WORDS-DEF_SEN_MIN_WORDS) + DEF_SEN_MIN_WORDS;
			if (senlen > parlen)
				senlen = parlen;
			parlen = parlen - senlen;
			List<String> sen = createSentenceParts(senlen,true,true,wordSep,phraseSep,senTerm);
			if (i != 0)
				parts.add(wordSep);
			parts.addAll(sen);
		}
		return parts;
	}


	private static List<String> createSentenceParts(int words,boolean punctuation,boolean capitalize,String wordSep,String phraseSep,String[] senTerm) {
		List<String> parts = new ArrayList<String>();
		int phrases = RANDOM.nextInt(3) + 1; //max of 3 phrases
		for (int p = 1; p <= phrases && words > 0; p++) {
			int phraselen;
			if (p == phrases)
				phraselen = words;
			else
				phraselen = RANDOM.nextInt((words / 2) + 1) + 2;
			if (words < phraselen) phraselen = words;
			words = words - phraselen;
			if (p != 1) {
				if(punctuation)parts.add(phraseSep);
				parts.add(wordSep);
			}
			List<String> phraseParts = createPhraseParts(phraselen,wordSep);
			if (capitalize && p == 1)
				phraseParts.set(0, WordUtils.capitalize(phraseParts.get(0)));
			parts.addAll(phraseParts);

		}
		if(punctuation)parts.add(senTerm[RANDOM.nextInt(senTerm.length)]);
		return parts;
	}

	private static List<String> createPhraseParts(int length,String wordSep) {
		List<String> parts = new ArrayList<String>();
		for (int j = 0; j < length; j++) {
			String word = getWord();
			if (j != 0)
				parts.add(wordSep);
			parts.add(word);
		}
		return parts;
	}
	
	public static String getWord(){
		return getWord(null,null);
	}
	
	public static String getWord(Integer len){
		return getWord(len,len);
	}
	

	/**
	 * Gets a word from the list of words that is >= min && <= max
	 * @param min
	 * @param max
	 * @return
	 */
	public static String getWord(Integer min, Integer max){
		if(!INIT_WORDS) initWords();
		int start = RANDOM.nextInt(WORD_ARRAY.length);
		int index = start;
		String word = null;
		for(;;) {
			word = WORD_ARRAY[index++];
			if((min == null || word.length() >= min) && (max == null || word.length() <= max) ) {
				return word;
			}
			//wrap around
			if(index == WORD_ARRAY.length) index = 0;
			//if were back at the beginning
			if(index == start) throw new IllegalStateException("Could not find word with size "+min+"-"+max);
		}
		
	}
	
	//TODO do we even need a method to "create" new words?
	/*
	 * public static String getOrCreateWord(Integer min, Integer max){
	 * if(!INIT_WORDS) initWords();
	 * 
	 * 
	 * String word = getWord(min, max);
	 * 
	 * if(word != null) return word; else { //need to create word String word = "";
	 * int start = RANDOM.nextInt(WORD_ARRAY.length); int index = start; for(;;) {
	 * if(word.length()<max) {
	 * 
	 * } } }
	 * 
	 * if(min == null) return WORD_ARRAY[RANDOM.nextInt(WORD_ARRAY.length)]; else {
	 * String word = ""; while(word.length()<length){
	 * word+=WORD_ARRAY[RANDOM.nextInt(WORD_ARRAY.length)]; }
	 * if(word.length()>length)word = word.substring(0, length); return word; } }
	 */
	
	private static void initWords(){
		INIT_WORDS = true;
		WORD_SET = LoremIpsum.splitWords(SOURCE);
		WORD_ARRAY = WORD_SET.toArray(new String[WORD_SET.size()]);
	}

	private static String joinLines(List<List<String>> partParts) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<List<String>> it = partParts.iterator();it.hasNext();) {
			List<String> parts = it.next();
			sb.append(joinLine(parts));
			if(it.hasNext())sb.append(LINE_TERM);
			
		}
		return sb.toString();
	}
	
	private static String joinLine(List<String> parts) {
		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			sb.append(part);
		}
		return sb.toString();
	}
	
	private static List<List<String>> makeLinesParts(Integer lines,Integer wordsPerLine,String wordSep,String phraseSep,String[] senTerm) {
		List<List<String>> pageParts = new ArrayList<List<String>>();
		for(int l=0;l<lines;l++){
			List<String> lineParts = createSentenceParts(wordsPerLine,false,false, wordSep, phraseSep, senTerm);
			pageParts.add(lineParts);
		}
		return pageParts;
	}
	
}
