import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class Jsouprun {
	
	public static String pegarDocType(Document doc) {
		
		List<Node>nods = doc.childNodes();
        for (Node node : nods) {
           if (node instanceof DocumentType) {
               DocumentType documentType = (DocumentType)node;
                 return documentType.toString(); 
                 
           }
       }
        return "";
		
	}
	
	public static String getHtmlVersion(String src) 
	{
			String html = regexHelper(src, "(?<=<!)(.*?)(?=>)"); 
				if (html.equalsIgnoreCase("doctype html"))
					return "HTML 5";
				else {
					String html2 = regexHelper(src, "\\w{4,5}\\s\\d\\..=?\\W");
					if (!html2.isEmpty()) {
						return html2;
					} else
						return "No match";
		}
	}

	public static String regexHelper(String src, String ptrn) {
		Pattern pattern = Pattern.compile(ptrn);
		Matcher matcher = pattern.matcher(src);
		if (matcher.find()) {
			return matcher.group();
		}else
			return "";
	}
		
	public static void main(String[] args) throws IOException 
	{
		Scanner s = new Scanner(System.in);
		
		System.out.println("Enter URL: ");
		String url = s.next();
				
		Document doc = Jsoup.connect(url)
				.timeout(9000).get();
		Element title = doc.select("h1").first();
		
		String docType = pegarDocType(doc);
		String htmlVersion = getHtmlVersion(docType);
		
		Elements links = doc.select("a");
		
		int linkExterno = 0; 
		int linkInterno = 0;
				
		for(Element e : links)
		{
			String href = e.attr("href");
			if (href.startsWith("http")){
				linkExterno++;
			
			} else {
				linkInterno++;
			}
		}
		System.out.println("Versão HTML: "+ htmlVersion);
		System.out.println("Título: "+title.text());
		System.out.println("Link Externo: "+linkExterno);
		System.out.println("Link Interno: "+linkInterno);
		
	}
	
}
