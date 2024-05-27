package us.abstracta.jmeter.javadsl.sample;

import org.apache.jmeter.protocol.http.gui.DNSCachePanel;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.blazemeter.BlazeMeterEngine;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

import static us.abstracta.jmeter.javadsl.JmeterDsl.testPlan;
import static us.abstracta.jmeter.javadsl.JmeterDsl.vars;
import static us.abstracta.jmeter.javadsl.JmeterDsl.httpAuth;
import static us.abstracta.jmeter.javadsl.JmeterDsl.httpDefaults;
import static us.abstracta.jmeter.javadsl.JmeterDsl.threadGroup;
import static us.abstracta.jmeter.javadsl.sample.dsl.AuthenticationDSL.login;
import static us.abstracta.jmeter.javadsl.sample.dsl.AuthenticationDSL.logout;
import static us.abstracta.jmeter.javadsl.sample.dsl.CartDSL.addProductToCart;
import static us.abstracta.jmeter.javadsl.sample.dsl.CatalogDSL.browseProducts;
import static us.abstracta.jmeter.javadsl.sample.dsl.CatalogDSL.viewProduct;
import static us.abstracta.jmeter.javadsl.sample.dsl.HttpDSL.getHeaders;
import static us.abstracta.jmeter.javadsl.wrapper.WrapperJmeterDsl.testElement;

public class TestAddProductToCart {

    @Test
    public void test() throws Exception {
        TestPlanStats stats = testPlan()
                .tearDownOnlyAfterMainThreadsDone()
                .children(
                        testElement(new DNSCachePanel())
                                .prop("DNSCacheManager.clearEachIteration", true),
                        vars()
                                .set("BASE_URL_1", "flipkartweb-mern.vercel.app"),
                        getHeaders(),
                        httpDefaults(),
                        httpAuth(),
                        threadGroup(1, 1,
                                login("robgarciab@gmail.com", "4846hkfheJHKJHJK&%&"),
                                browseProducts(1, "", 0, 200000),
                                viewProduct("6203f6755893c7f4f98c358a"),
                                addProductToCart("6203f6755893c7f4f98c358a"),
                                logout()
                        )
                ).runIn(new BlazeMeterEngine(System.getenv("BZ_TOKEN"))
                        .totalUsers(15)
                        .projectId(1437484)
                        .testName("Demo Blaze JMeter DSL")
                        .rampUpFor(Duration.ofMinutes(1))
                        .holdFor(Duration.ofMinutes(4))
                        .threadsPerEngine(50)
                        .testTimeout(Duration.ofMinutes(20)));
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(1));
    }
}