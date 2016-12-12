

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.util.logging.resources.logging;

import com.sun.org.apache.xpath.internal.operations.Div;

public class PaUrl {
    
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
    	String charset = "utf-8";
        int  page=0;
       for(int i=0;i<8;i++){
        	//String url_str = "http://music.163.com/#/discover/playlist/?order=hot&cat=%E6%B0%91%E8%B0%A3&limit=35&offset="+page;
        	String url_str =    "http://music.163.com/discover/playlist/?order=hot&cat=民谣&limit=35&offset="+page;
        	DoPachong(url_str,charset);
        	page = page +35;
    	}
      //  DoPachong(url_str);
     //   String filepath = "/home/tianhao/study/网易云音乐爬虫/民谣";
        
    }
    public static void DoPachong( String url_str, String charset) throws ClientProtocolException, IOException{
    		HttpClient hc = new DefaultHttpClient();
    		HttpGet hg = new HttpGet(url_str);
    		HttpResponse response = hc.execute(hg);
    		HttpEntity entity = response.getEntity();
            
        
    		InputStream htm_in = null;
        
    		if(entity != null){
    			htm_in = entity.getContent();
    			String htm_str = InputStream2String(htm_in,charset);
    			Document  doc =  Jsoup.parse(htm_str);
    			Elements links= doc.select("div[class=g-bd]").select("div[class=g-wrap p-pl f-pr]").select("ul[class=m-cvrlst f-cb]").select("div[class=u-cover u-cover-1");
    			for (Element link : links) {
    					Elements lin = link.select("a");  
    					String re_url = lin.attr("href");
    					String re_title = lin.attr("title");
    					re_url = "http://music.163.com"+re_url;
    					System.out.print(re_title+"       ");
    					System.out.print(re_url+"       ");
    					SecondPaChong(re_url,charset);
    			}
    		}
    }
    public static void SecondPaChong( String url_str, String charset) throws ClientProtocolException, IOException{
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url_str);
		HttpResponse response = hc.execute(hg);
		HttpEntity entity = response.getEntity();
        
		InputStream htm_in = null;
    
		if(entity != null){
			htm_in = entity.getContent();
			String htm_str = InputStream2String(htm_in,charset);
			Document  doc =  Jsoup.parse(htm_str);
			String links= doc.select("div[class=u-title u-title-1 f-cb]").select("div[class=more s-fc3]").select("strong").text();
			System.out.println(links);
		
		}
}
    public static void saveHtml(String filepath, String str){
        
        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "utf-8");
            outs.write("http://www.dailystrength.org"+str+"\r\n");
            outs.close();
        } catch (IOException e) {
            System.out.println("Error at save html...");
            System.out.println(str);
            e.printStackTrace();
        }
    }

    public static String InputStream2String(InputStream in_st,String charset) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
        	res.append(line);
        }
        return res.toString();
    }
}