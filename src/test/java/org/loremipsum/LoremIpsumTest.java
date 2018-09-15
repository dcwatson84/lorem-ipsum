package org.loremipsum;
import java.io.File;
import java.util.Random;

import org.junit.Test;


public class LoremIpsumTest {
	
	/*static String[] SOURCE = new String[]{
		"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
		"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?",
		"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."
	};*/
	
	@Test
	public void createWord(){
		String word = LoremIpsum.createWord();
		System.out.println("Random Word: "+word);
		
		for(int i=0;i<10;i++){
			String fake = LoremIpsum.createWord(i+1);
			System.out.println("Fake Word: "+fake);
		}
		
	}
	
	@Test
	public void testLoremIpsumLength() {
		String pg = LoremIpsum.createParagraph(1000);
		System.out.println(pg.length());
		System.out.println(pg);
	}
	
	@Test
	public void testLoremIpsumLength2() {
		String pg = LoremIpsum.createParagraph(5);
		System.out.println(pg.length());
		System.out.println(pg);
	}

	@Test
	public void testLoremIpsum() {
		//LoremIpsum li = new LoremIpsum();
		
		Random r = new Random();
		
		for(int i=0;i<10;i++){
			//StringBuilder sb = new StringBuilder();
			int len = r.nextInt(10)+50;
			//System.out.println("par:"+len);
			//LoremIpsum.appendPhrase(sb, 5);
			//LoremIpsum.appendSentence(sb,r.nextInt(10)+5 );
			//System.out.println(LoremIpsum.makeString(LoremIpsum.createParagraph(len)));
			System.out.println(LoremIpsum.createParagraph(len));
			System.out.println(LoremIpsum.createParagraph());
			//LoremIpsum.
		}
		
		/*
		for(int i=0;i<10;i++){
			StringBuilder sb = new StringBuilder();
			int len = r.nextInt(8)+4;
			System.out.println("sentence:"+len);
			//LoremIpsum.appendPhrase(sb, 5);
			//LoremIpsum.appendSentence(sb,r.nextInt(10)+5 );
			System.out.println(LoremIpsum.makeString(LoremIpsum.createSentence(len)));
		}
		*/
	}

	
	
	@Test
	public void createTextFile(){
		LoremIpsum.createCsvDocument(10000, 30, new File("target/document.txt"));
		
	}
}
