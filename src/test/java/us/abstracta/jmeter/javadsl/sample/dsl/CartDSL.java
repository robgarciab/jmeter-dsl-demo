package us.abstracta.jmeter.javadsl.sample.dsl;

import us.abstracta.jmeter.javadsl.core.controllers.DslTransactionController;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;
import static us.abstracta.jmeter.javadsl.JmeterDsl.transaction;

public class CartDSL {
    public static DslTransactionController addProductToCart(String productCode) {
        return transaction("Add To Cart",
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/product/", "https://${BASE_URL_1}/api/v1/product/" + productCode)
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
        );
    }
}
