package com.cjam.springboot.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.cjam.springboot.entity.ImageTextMessage;
import com.cjam.springboot.entity.WeixinUserInfo;
import com.cjam.springboot.entity.item;
import com.cjam.springboot.helper.UserHelper;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjam.springboot.entity.TextMessage;
import com.cjam.springboot.util.MessageUtil;

/**
 * Created by jam on 2017/1/1.
 * 接收，处理用户发过来的各类消息
 */
@Service
public class CoreService {

    private final static Logger logger = LoggerFactory.getLogger(CoreService.class);

    public String processRequest(HttpServletRequest request) {

        Map<String, String> xmlDict = null;
        try {
            xmlDict = MessageUtil.parseXml(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        logger.info("request xml:{}",JSON.toJSON(xmlDict));
        // 发送方帐号
        String fromUserName = xmlDict.get(MessageUtil.FromUserName);
        // 开发者微信号
        String toUserName = xmlDict.get(MessageUtil.ToUserName);
        // 消息类型
        String msgType = xmlDict.get(MessageUtil.MsgType);

        // 回复文本消息
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

        String respContent = null;

        // 文本消息
        if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            String content = xmlDict.get("Content");
            UserHelper userHelper = new UserHelper();
            WeixinUserInfo userInfo = userHelper.getUserInfo(fromUserName);
            respContent = "您发送的是文本消息！" + JSON.toJSONString(userInfo);
            logger.info("{}|{}|{}|{}",fromUserName,toUserName,msgType,content);
            logger.info("respContent|{}",respContent);
        }
        // 图片消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
            String picUrl = xmlDict.get("PicUrl");
            respContent = "您发送的是图片消息！" + picUrl;
            logger.info("{}|{}|{}|{}",fromUserName,toUserName,msgType,picUrl);
        }
        // 语音消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
            respContent = "您发送的是语音消息！";
        }
        // 视频消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
            respContent = "您发送的是视频消息！";
        }
        // 视频消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
            respContent = "您发送的是小视频消息！";
        }
        // 地理位置消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
            respContent = "您发送的是地理位置消息！";
        }
        // 链接消息
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
            respContent = "您发送的是链接消息！";
        }
        // 事件推送
        else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
            // 事件类型
            String eventType = xmlDict.get("Event");
            // 关注
            if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                respContent = "谢谢您的关注！";
            }
            // 取消关注
            else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
            }
            // 扫描带参数二维码
            else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                // TODO 处理扫描带参数二维码事件
            }
            // 上报地理位置
            else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                // TODO 处理上报地理位置事件
            }
            // 自定义菜单
            else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                // TODO 处理菜单点击事件
                String eventKey = xmlDict.get("EventKey");
                logger.info("eventKey:{}",eventKey);
                respContent = eventKey;

                // 回复文本消息
                ImageTextMessage imageTextMessage = ImageTextMessage.newObj();
                imageTextMessage.setToUserName(fromUserName);
                imageTextMessage.setFromUserName(toUserName);
                imageTextMessage.setCreateTime(new Date().getTime());
                imageTextMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                item item1 = new item();
                item1.setTitle("荔枝电台");
                item1.setDescription("荔枝电台,有胆你就来！");
                item1.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1483428914&di=f31eaf7b2abc3a2e9359d3bdb376a9cf&src=http://www.ushorizontravel.com/Plugin/ueditor/net/upload1/2014-11-23/e191f21a-cc8c-4606-821f-2e9f931c3465.png");
                item1.setUrl("https://weui.io");
                imageTextMessage.setArticleCount(1);
                imageTextMessage.getArticles().add(item1);
                if (eventKey.equalsIgnoreCase("12")){
                    imageTextMessage.getArticles().add(item1);
                    imageTextMessage.setArticleCount(2);
                } else if (eventKey.equalsIgnoreCase("13")){
                    imageTextMessage.getArticles().add(item1);
                    imageTextMessage.getArticles().add(item1);
                    imageTextMessage.setArticleCount(3);
                }
                String respXml = MessageUtil.messageToXml(imageTextMessage);
                return respXml;
            }
        }
        // 设置文本消息的内容
        textMessage.setContent(respContent);
        // 将文本消息对象转换成xml
        String respXml = MessageUtil.messageToXml(textMessage);
        return respXml;
    }
}
