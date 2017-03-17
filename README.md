
今天花了一点时间抓取了网易云音乐的热门民谣歌单，共1500热门民谣歌单，后续有时间会爬取其他分类。
    下面记录一下java爬取过程。


----------

## 爬虫过程 ##



  1.首先抓取各个歌单的url与标题
```
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
```


----------


  2.根据抓取的url进一步用jsoup解析收听量


```
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
```


----------


## 爬取结果 ##
 
![这里写图片描述](http://img.blog.csdn.net/20161212155208948?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzc3NTk1Mg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
  


----------


 民谣歌单收听量前10：


1.  [如果你想听民谣，可以从这些歌曲开始。](http://music.163.com/playlist?id=129627424)    收听量：11548417

2.   [民谣是最安静的角落](http://music.163.com/#/playlist?id=110759778)          收听量：10727168

3. [孤独旅人配民谣。](http://music.163.com/#/playlist?id=378324005)    收听量：9946952

4. [你若听过他的歌，此生便有了挂念](http://music.163.com/playlist?id=506453942)    收听量：7551374

5. [♬女生嘛，污一点才可爱](http://music.163.com/playlist?id=327180503)    收听量：6260712

6. [阅尽沧桑，洗却铅华:聆听那些沧桑之声](http://music.163.com/#/playlist?id=136549038)    收听量：5793889

7. [民谣，成长中的情绪共谋者](http://music.163.com/playlist?id=131368017)    收听量：5368672

8. [华语女声‖那些入耳入心的代表曲](http://music.163.com/playlist?id=501419209)    收听量：4535668

9. [啤酒邂逅音乐之华语摇滚](http://music.163.com/playlist?id=138911210)    收听量：4449337

10. [中国民谣精选集](http://music.163.com/playlist?id=49927225)    收听量：4423420



