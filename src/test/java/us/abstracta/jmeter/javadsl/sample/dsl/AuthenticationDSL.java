package us.abstracta.jmeter.javadsl.sample.dsl;

import us.abstracta.jmeter.javadsl.core.controllers.DslTransactionController;
import org.apache.http.entity.ContentType;
import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;
import static us.abstracta.jmeter.javadsl.JmeterDsl.transaction;

public class AuthenticationDSL {
    public static DslTransactionController login(String email, String password) {
        return transaction("Login",
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/login", "https://${BASE_URL_1}/api/v1/login")
                        .post(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password), ContentType.APPLICATION_JSON)
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
        );
    }

    public static DslTransactionController logout() {
        return transaction("Logout",
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/logout", "https://${BASE_URL_1}/api/v1/logout")
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
        );
    }
}
