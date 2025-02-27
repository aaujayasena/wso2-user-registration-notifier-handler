package org.wso2.custom.handler;

import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static List<String> getAdminEmails(int tenantId) throws Exception {
        List<String> adminEmails = new ArrayList<>();
        RealmService realmService = IdentityTenantUtil.getRealmService();

        if (realmService != null) {
            UserRealm userRealm = realmService.getTenantUserRealm(tenantId);
            if (userRealm != null) {
                UserStoreManager userStoreManager = userRealm.getUserStoreManager();

                if (userStoreManager != null) {
                    // Get all users with the "admin" role
                    String[] adminUsers = userStoreManager.getUserListOfRole("admin");

                    for (String adminUser : adminUsers) {
                        // Fetch email claim of the admin user
                        String email = userStoreManager.getUserClaimValue(adminUser, "http://wso2.org/claims/emailaddress", null);
                        if (email != null && !email.isEmpty()) {
                            adminEmails.add(email);
                        }
                    }
                }
            }
        }
        return adminEmails;
    }
}
