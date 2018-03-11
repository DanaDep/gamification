package com.dep.gamification.util;

import com.dep.gamification.models.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Component
public class MailHelper {
    private static final Logger logger = Logger.getLogger(MailHelper.class);

    @Autowired
    private Configuration freemarkerConfig;

    public String createEarnedPointsRequestEmailTemplate(User supervisor, User requester, String requestId) throws Exception {
        logger.info("LOG: Starting to create email content for earned points request...");
        Map<String, String> model = new HashMap<>();
        model.put("supervisor", supervisor.getFirstName() + " " + supervisor.getLastName());
        model.put("requester", requester.getFirstName() + " " + requester.getLastName());
        model.put("link", PathHelper.getHost() + ":" + PathHelper.getPort() + PathHelper.getEarnedPointsRequestPath() + "/" + requestId + PathHelper.getResponsePath());

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("earnedPointsRequestEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for earned points request...");

        return emailContent;
    }

    public String createEarnedPointsAcceptedResponseEmailTemplate(User beneficiary) throws Exception{
        logger.info("LOG: Starting to create email content for earned points request...");
        Map<String, String> model = new HashMap<>();
        model.put("beneficiary", beneficiary.getFirstName() + " " + beneficiary.getLastName());
        model.put("gamificationHomePage", "www.gamification.com");

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("earnedPointsAcceptedResponseEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for earned points request...");

        return emailContent;
    }

    public String createEarnedPointsRejectedResponseEmailTemplate(User requester, String comment) throws Exception{
        logger.info("LOG: Starting to create email content for earned points request...");
        Map<String, String> model = new HashMap<>();
        model.put("requester", requester.getFirstName() + " " + requester.getLastName());
        model.put("supervisorResponse", comment);

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("earnedPointsRejectedResponseEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for earned points request...");

        return emailContent;
    }

    public String createSpentPointsRequestEmailTemplate(User supervisor, User requester, String requestId) throws Exception {
        logger.info("LOG: Starting to create email content for spent points request...");

        Map<String, String> model = new HashMap<>();
        model.put("supervisor", supervisor.getFirstName() + " " + supervisor.getLastName());
        model.put("requester", requester.getFirstName() + " " + requester.getLastName());
        model.put("link", PathHelper.getHost() + ":" + PathHelper.getPort() + PathHelper.getEarnedPointsRequestPath() + "/" + requestId + PathHelper.getResponsePath());

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("spentPointsRequestEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for spent points request...");

        return emailContent;
    }

    public String createSpentPointsAcceptedResponseEmailTemplate(User requester) throws Exception{
        logger.info("LOG: Starting to create email content for spent points request...");

        Map<String, String> model = new HashMap<>();
        model.put("requester", requester.getFirstName() + " " + requester.getLastName());
        model.put("gamificationHomePage", "www.gamification.com");

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("spentPointsAcceptedResponseEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for spent points request...");

        return emailContent;
    }

    public String createSpentPointsRejectedResponseEmailTemplate(User requester, String comment) throws Exception{
        logger.info("LOG: Starting to create email content for spent points request...");
        Map<String, String> model = new HashMap<>();
        model.put("requester", requester.getFirstName() + " " + requester.getLastName());
        model.put("supervisorResponse", comment);

        // set loading location to src/resources/templates
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Template template = freemarkerConfig.getTemplate("spentPointsRejectedResponseEmailTemplate.ftl");
        String emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        logger.info("LOG: Ending to create email content for earned points request...");

        return emailContent;
    }

}
