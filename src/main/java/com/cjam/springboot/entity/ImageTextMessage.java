package com.cjam.springboot.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

/**
 * Created by jam on 2017/1/2.
 <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>12345678</CreateTime>
 <MsgType><![CDATA[news]]></MsgType>
 <ArticleCount>2</ArticleCount>
 <Articles>
 <item>
 <Title><![CDATA[title1]]></Title>
 <Description><![CDATA[description1]]></Description>
 <PicUrl><![CDATA[picurl]]></PicUrl>
 <Url><![CDATA[url]]></Url>
 </item>
 <item>
 <Title><![CDATA[title]]></Title>
 <Description><![CDATA[description]]></Description>
 <PicUrl><![CDATA[picurl]]></PicUrl>
 <Url><![CDATA[url]]></Url>
 </item>
 </Articles>
 </xml>
 */
public class ImageTextMessage extends BaseMessage{

    // ArticleCount
    private Integer ArticleCount;

    private ArrayList<item> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public ArrayList<item> getArticles() {
        return Articles;
    }

    public void setArticles(ArrayList<item> articles) {
        Articles = articles;
    }

    public static ImageTextMessage newObj(){
        ImageTextMessage imageTextMessage = new ImageTextMessage();
        ArrayList<item> items = new ArrayList<item>();
        imageTextMessage.setArticles(items);
        return imageTextMessage;
    }
}
