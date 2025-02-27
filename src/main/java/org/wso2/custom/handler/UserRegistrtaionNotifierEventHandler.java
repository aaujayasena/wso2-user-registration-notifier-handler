package org.wso2.custom.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.core.bean.context.MessageContext;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.event.IdentityEventConstants;
import org.wso2.carbon.identity.event.IdentityEventException;
import org.wso2.carbon.identity.event.event.Event;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;
import java.util.List;
import java.util.Map;

public class UserRegistrtaionNotifierEventHandler extends AbstractEventHandler {

    private static final Log log = LogFactory.getLog(UserRegistrtaionNotifierEventHandler.class);

    @Override
    public String getName(){
        return "UserRegistrationNotifier";
    }

    @Override
    public int getPriority(MessageContext messageContext){
        return 250;
    }

    @Override
    public void handleEvent(Event event) throws IdentityEventException {
        if (IdentityEventConstants.Event.POST_ADD_USER.equals(event.getEventName())) {
            Map<String, Object> eventProperties = event.getEventProperties();
            String username = (String) eventProperties.get(IdentityEventConstants.EventProperty.USER_NAME);
            String tenantDomain = (String) eventProperties.get(IdentityEventConstants.EventProperty.TENANT_DOMAIN);
            int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);

            log.info("New user registered: " + username + " in tenant: " + tenantDomain);

            try {
                // Fetch Admin users' emails
                List<String> adminEmails = UserUtils.getAdminEmails(tenantId);

                // Pass the List<String> directly
                EmailSender.sendEmail(adminEmails, username, tenantDomain);


            } catch (Exception e) {
                log.error("Error while sending admin notification email for new user: " + username, e);
            }
        }

    }
}
