package htmlparser;

import net.androidla.html.AbStractHTMLContent;
import net.androidla.html.HTMLContentHelper;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.ParserFeedback;

public class TestJsoup {
	public static void main(String[] args) {
		AbStractHTMLContent htmlHelper = new HTMLContentHelper();
		String str = htmlHelper.getHTMLContent("http://edu.csdn.net/heima/");
		try {
			Parser parser = createParser(str);
			System.out.println(parser.getEncoding());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        return new Parser(mLexer, (ParserFeedback) new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
}
