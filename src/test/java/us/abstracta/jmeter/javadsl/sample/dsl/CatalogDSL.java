package us.abstracta.jmeter.javadsl.sample.dsl;

import us.abstracta.jmeter.javadsl.core.controllers.DslTransactionController;

import static us.abstracta.jmeter.javadsl.JmeterDsl.httpSampler;
import static us.abstracta.jmeter.javadsl.JmeterDsl.transaction;

public class CatalogDSL {
    public static DslTransactionController browseProducts(int page, String keyword, double priceGte, double priceLte) {
        return transaction("Browse Products",
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/products", "https://${BASE_URL_1}api/v1/products")
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
                        .param("ratings[gte]", "0")
                        .rawParam("page", page + "")
                        .rawParam("keyword", keyword)
                        .param("price[gte]", priceGte + "")
                        .param("price[lte]", priceLte + "")
        );
    }

    public static DslTransactionController viewProduct(String productCode) {
        return transaction("Select First Product",
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/product", "https://${BASE_URL_1}/api/v1/product/" + productCode)
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin"),
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/products", "https://${BASE_URL_1}/api/v1/products")
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
                        .rawParam("category", "undefined"),
                httpSampler("https://flipkartweb-mern.vercel.app/api/v1/products", "https://${BASE_URL_1}/api/v1/products")
                        .header("Accept", "application/json, text/plain, */*")
                        .header("Sec-Fetch-Site", "same-origin")
                        .rawParam("category", "Electronics")
        );
    }
}
