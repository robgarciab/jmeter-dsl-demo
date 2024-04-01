package us.abstracta.jmeter.javadsl.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;
import static us.abstracta.jmeter.javadsl.wrapper.WrapperJmeterDsl.*;

import java.time.Duration;

import org.apache.jmeter.protocol.http.gui.DNSCachePanel;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.blazemeter.BlazeMeterEngine;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

public class PerformanceTest {

    @Test
    public void test() throws Exception {
        TestPlanStats stats = testPlan()
                .tearDownOnlyAfterMainThreadsDone()
                .children(
                        testElement(new DNSCachePanel())
                                .prop("DNSCacheManager.clearEachIteration", true),
                        vars()
                                .set("BASE_URL_1", "demoblaze.com"),
                        httpHeaders()
                                .header("sec-ch-ua", "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"")
                                .header("sec-ch-ua-mobile", "?0")
                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                                .header("Upgrade-Insecure-Requests", "1")
                                .header("sec-ch-ua-platform", "\"Windows\"")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                                .header("Sec-Fetch-Dest", "document")
                                .header("Sec-Fetch-User", "?1")
                                .header("Sec-Fetch-Mode", "navigate"),
                        httpDefaults(),
                        httpAuth(),
                        threadGroup(1, 1,
                                transaction("Test",
                                        httpSampler("https://demoblaze.com/", "https://${BASE_URL_1}")
                                                .header("Sec-Fetch-Site", "none")
                                                .children(
                                                        constantTimer(Duration.ofMillis(750))
                                                ),
                                        httpSampler("https://demoblaze.com/prod.html?idp_=1", "https://${BASE_URL_1}/prod.html")
                                                .header("Sec-Fetch-Site", "same-origin")
                                                .rawParam("idp_", "1")
                                                .children(
                                                        constantTimer(Duration.ofMillis(750))
                                                ),
                                        httpSampler("https://demoblaze.com/cart.html", "https://${BASE_URL_1}/cart.html")
                                                .header("Sec-Fetch-Site", "same-origin")
                                                .children(
                                                        constantTimer(Duration.ofMillis(750))
                                                )
                                )
                        )
                ).runIn(new BlazeMeterEngine(System.getenv("BZ_TOKEN"))
                        .totalUsers(210)
                        .projectId(1437484)
                        .testName("Demo Blaze JMeter DSL")
                        .holdFor(Duration.ofMinutes(4))
                        .threadsPerEngine(100)
                        .testTimeout(Duration.ofMinutes(20)));
        assertThat(stats.overall().sampleTimePercentile99()).isLessThan(Duration.ofSeconds(1));
    }

}