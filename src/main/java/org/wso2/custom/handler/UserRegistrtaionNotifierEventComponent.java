package org.wso2.custom.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.wso2.carbon.identity.event.handler.AbstractEventHandler;

/**
 * OSGi Component for registering the custom event handler in WSO2 Identity Server.
 */
@Component(
        name = "org.wso2.custom.handler.UserRegistrtaionNotifierEventComponent",
        immediate = true
)

public class UserRegistrtaionNotifierEventComponent {

    private static final Log log = LogFactory.getLog(UserRegistrtaionNotifierEventComponent.class);

    @Activate
    protected void activate(ComponentContext context) {
        try {
            BundleContext bundleContext = context.getBundleContext();
            bundleContext.registerService(
                    AbstractEventHandler.class.getName(),
                    new UserRegistrtaionNotifierEventHandler(),
                    null
            );
            log.info("Custom User Registration notifier Event Handler Activated Successfully.");
        } catch (Exception e) {
            log.error("Error while activating Custom User Registration notifier Event Handler component.", e);
        }
    }


}
