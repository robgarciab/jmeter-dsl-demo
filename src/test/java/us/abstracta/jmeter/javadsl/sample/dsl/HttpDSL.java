package us.abstracta.jmeter.javadsl.sample.dsl;

import us.abstracta.jmeter.javadsl.http.HttpHeaders;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpHeaders;

public class HttpDSL {

    public static HttpHeaders getHeaders() {
        return httpHeaders()
                .header("sec-ch-ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"macOS\"")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors");
    }
}
