package com.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mg.vo.Order;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JmsTemplateMail {
	
	 private static final String ENCODING = "utf-8";

	    //private static Logger logger = LoggerFactory.getLogger(TemplateMail.class);   

	    private JavaMailSender mailSender;

	    private Template template;

	    /**

	     * Spring的MailSender.

	     */

	    public void setMailSender(JavaMailSender mailSender) {

	       this.mailSender = mailSender;

	    }

	    /**

	     * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.

	     */

	    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {

	       //根据freemarkerConfiguration的templateLoaderPath载入文件.

	       template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", ENCODING);

	    }

	    /**

	     * 发送MIME格式的订单消息邮件.

	     */

	    public void sendNotificationMail(Order order) {

	       MimeMessage msg = mailSender.createMimeMessage();

	       try {

	           MimeMessageHelper helper = new MimeMessageHelper(msg, true, ENCODING);

	           //Member member = order.getMember();

//	         helper.setTo(order.getMember().getEmail());

	           helper.setTo("851590981@qq.com");

	           helper.setFrom("jfd851590981@163.com");

	           String subject = "jms发送html模版邮件测试"; 

	           helper.setSubject(subject);

	           buildContent(helper,order);

//	         buildAttachment(helper);

	           mailSender.send(msg);

	       System.out.println("jms邮件发送成功,^_^");

	       } catch (MessagingException e) {  

	       } catch (Exception e) {

	       }

	    }

	    /**

	     * 使用Freemarker生成html格式内容.

	     */

	    @SuppressWarnings("unchecked")

	    private void buildContent(MimeMessageHelper helper,Order order) throws MessagingException {

	       try {

	           Map context = new HashMap();

	           context.put("memberName", "XXX");

	           context.put("orderNum", "011258");

//	         context.put("memberName", "java.master");

//	         context.put("orderNumber", 223344);

	           //context.put("vo", vo);

	           context.put("ordervo", order);

	           String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);

	           helper.setText(content, true);

	       } catch (IOException e) {

	           throw new MessagingException("FreeMarker模板不存在", e);

	       } catch (TemplateException e) {

	           throw new MessagingException("FreeMarker处理失败", e);

	       }

	    }

	    /**

	     * 添加附件.

	     */

	    private void buildAttachment(MimeMessageHelper helper) throws MessagingException {

	       try {

	           //使用Spring的Resource Loader获取打包在classpath中的附件.

	           ClassPathResource attachment = new ClassPathResource("/mail/mailAttachment.txt");

	           helper.addAttachment("mailAttachment.txt", attachment.getFile());

	       } catch (IOException e) {

	           throw new MessagingException("附件文件不存在", e);

	       }

	    }

	
}
