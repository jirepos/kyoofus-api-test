package com.jirepo.demo.opengraph;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenGraphUtil {

    public static OpenGraphData getOpenGraph(String sourceUrl) {

        Document document;
        if (!(StringUtils.startsWithIgnoreCase(sourceUrl, "http://")
                || StringUtils.startsWithIgnoreCase(sourceUrl, "https://"))) {
            sourceUrl = "http://" + sourceUrl;
        }
        OpenGraphData data = new OpenGraphData();
        try {
            // 사이트 연결하여 문서 가져오기
            URI uri = URI.create(sourceUrl);
            data.setUrl(sourceUrl);
            data.setDomain(getOgDomain(sourceUrl));
            document = Jsoup.connect(uri.toString()).followRedirects(true).timeout(3000).get();
            if (document.location().startsWith("https://link.naver.com/bridge")) {
                String redirectNaverUrl = "";
                List<NameValuePair> params = URLEncodedUtils.parse(new URI(document.location()),
                        Charset.forName("UTF-8"));
                for (NameValuePair param : params) {
                    if (param.getName().equals("url"))
                        redirectNaverUrl = param.getValue();
                    break;
                }
                document = Jsoup.connect(redirectNaverUrl).followRedirects(true).timeout(3000).get();
            }
            // for (Element refresh : document.select("html head meta[http-equiv=refresh]"))
            // {
            // Matcher m =
            // Pattern.compile("(?si)\\d+;\\s*url=(.+)|\\d+").matcher(refresh.attr("content"));
            //
            // // find the first one that is valid
            // if (m.matches()) {
            // if (m.group(1) != null) {
            // document = Jsoup.connect(uri.resolve(m.group(1)).toString()).get();
            // }
            //
            // break;
            // }
            // }
        } catch (Exception e) {
            e.printStackTrace();
            data.setTitle("");
            data.setDescription("");
            sourceUrl = getBase(sourceUrl);
            try {
                Document documentBase = Jsoup.connect(sourceUrl).followRedirects(true).timeout(3000).get();
                data.setImage(getOgImage(sourceUrl, documentBase));
            } catch (Exception e1) {
                return null;
            }
            return data;
        }

        // 문서 파싱하여 데이터 반환 
        data.setTitle(getOgTitle(document));
        data.setDescription(getOgDescription(document));
        String ogImage = getOgImage(sourceUrl, document);
        if (StringUtils.isNotEmpty(ogImage) && ogImage.length() <= 2000) {
            data.setImage(ogImage);
        }
        return data;
    }//:


    private static String getOgDomain(String url) {
        // URI = https://www.geeksforgeeks.org
        // Host = www.geeksforgeeks.org
        try {
            return new URI(url).getHost();
        } catch (Exception e) {
            return "";
        }
    }

    private static String getOgTitle(Document doc) {
        String content;
        // <meta property="og:title" content="*" />
        Elements elements = doc.getElementsByAttributeValue("property", "og:title");
        content = getElementAttribute(elements, "content");
        if (StringUtils.isEmpty(content)) {
            // <meta name="og:title" content="*" />
            elements = doc.getElementsByAttributeValue("name", "og:title");
            content = getElementAttribute(elements, "content");
        }
        if (StringUtils.isEmpty(content)) {
            // <meta property="twitter:title" content="*" />
            elements = doc.getElementsByAttributeValue("property", "twitter:title");
            content = getElementAttribute(elements, "content");
        }
        if (StringUtils.isEmpty(content)) {
            // <meta name="twitter:title" content="*" />
            elements = doc.getElementsByAttributeValue("name", "twitter:title");
            content = getElementAttribute(elements, "content");
        }
        if (StringUtils.isEmpty(content)) {
            // <meta name="title" content="*">
            elements = doc.getElementsByAttributeValue("name", "title");
            content = getElementAttribute(elements, "content");
        }
        if (StringUtils.isEmpty(content)) {
            // <title>*</title>
            content = doc.title();
        }

        if (StringUtils.isEmpty(content)) {
            content = "";
        } else {
            content = StringUtils.left(content, 200);
        }

        return content;
    }

    private static String getOgDescription(Document doc) {
        String content;

        // <meta property="og:description" content="*" />
        Elements elements = doc.getElementsByAttributeValue("property", "og:description");
        content = getElementAttribute(elements, "content");

        if (StringUtils.isEmpty(content)) {
            // <meta name="og:description" content="*" />
            elements = doc.getElementsByAttributeValue("name", "og:description");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta property="twitter:description" content="*" />
            elements = doc.getElementsByAttributeValue("property", "twitter:description");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta name="twitter:description" content="*" />
            elements = doc.getElementsByAttributeValue("name", "twitter:description");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta name="description" content="*">
            elements = doc.getElementsByAttributeValue("name", "description");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <p>*</p>
            content = getElementText(doc.getElementsByTag("p"));
        }

        if (StringUtils.isEmpty(content)) {
            // <div>*</div>
            content = getElementText(doc.getElementsByTag("div"));
        }

        if (StringUtils.isEmpty(content)) {
            content = "";
        } else {
            content = StringUtils.left(content, 600);
        }

        return content;
    }

    private static String getOgImage(String url, Document document) {

        String content;

        // <meta itemprop="image" content="...">
        Elements elements = document.getElementsByAttributeValue("itemprop", "image");
        content = getElementAttribute(elements, "content");

        if (StringUtils.isEmpty(content)) {
            // <meta property="og:image" content="*" />
            elements = document.getElementsByAttributeValue("property", "og:image");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta name="og:image" content="*" />
            elements = document.getElementsByAttributeValue("name", "og:image");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta property="twitter:image" content="*" />
            elements = document.getElementsByAttributeValue("property", "twitter:image");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // <meta name="twitter:image" content="*" />
            elements = document.getElementsByAttributeValue("name", "twitter:image");
            content = getElementAttribute(elements, "content");
        }

        if (StringUtils.isEmpty(content)) {
            // 2nd -> img in div
            for (Element e1 : document.getElementsByTag("div")) {
                if (e1.children().size() > 0) {
                    e1 = e1.child(0);
                    if (e1.tagName().equals("img")) {
                        if (e1.hasAttr("width")) {
                            content = e1.attr("src");

                            if (StringUtils.isNotEmpty(content)) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (StringUtils.isEmpty(content)) {
            // 2nd -> img in p
            for (Element e1 : document.getElementsByTag("p")) {
                content = getElementAttribute(e1.getElementsByTag("img"), "src");

                if (StringUtils.isNotEmpty(content)) {
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(content)) {
            // 2nd -> img in dd
            for (Element e1 : document.getElementsByTag("dd")) {
                content = getElementAttribute(e1.getElementsByTag("img"), "src");

                if (StringUtils.isNotEmpty(content)) {
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(content)) {
            // 3rd -> img in html
            content = getElementAttribute(document.getElementsByTag("img"), "src");
        }

        String imageUrl = "";
        if (StringUtils.isNotEmpty(content)) {
            imageUrl = getAbsoluteUrl(url, content);
        }

        try {
            if (StringUtils.isNotEmpty(imageUrl)) {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(imageUrl).openConnection();
                urlConnection.setConnectTimeout(1000);
                urlConnection.setReadTimeout(1000);
                urlConnection.connect();

                if (urlConnection.getResponseCode() != HttpStatus.SC_OK)
                    imageUrl = null;
            }

        } catch (Exception e) {
            imageUrl = "";
        }

        // etc empty
        return imageUrl;
    }

    private static String getBase(String url) {
        // getUrlQuery(url);

        String host;
        try {
            URI uri = new URI(url);
            host = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() == -1 ? "" : ":" + uri.getPort());
        } catch (Exception e) {
            host = "";
        }

        return host;
    }


    private static String getAbsoluteUrl(String sourceUrl, String dataUrl) {
        String baseProtocol = "";
        String baseUrl = "";
        String rawPath = "";

        try {
            URI uri = new URI(sourceUrl);
            baseProtocol = uri.getScheme();
            baseUrl = uri.getScheme() + "://" + uri.getHost() + (uri.getPort() == -1 ? "" : ":" + uri.getPort());
            rawPath = uri.getRawPath();
        } catch (Exception e) {
        }

        String parsedUrl = dataUrl;

        if (StringUtils.startsWith(dataUrl, "//")) {
            parsedUrl = baseProtocol + ":" + dataUrl;
        } else if (StringUtils.startsWith(dataUrl, "/")) {
            parsedUrl = baseUrl + dataUrl;
        } else if (!(StringUtils.startsWithIgnoreCase(dataUrl, "http://")
                || StringUtils.startsWithIgnoreCase(dataUrl, "https://"))) {
            parsedUrl = baseUrl + rawPath + dataUrl;
        }

        return parsedUrl;
    }

    private static String getElementText(Elements elements) {
        String content = "";

        if (elements != null) {
            for (Element element : elements) {
                if (element.hasText()) {
                    String tempContent = element.text();

                    if (StringUtils.isNotEmpty(tempContent)) {
                        content = tempContent;
                        break;
                    }
                }
            }
        }

        return content;
    }

    private static String getElementAttribute(Elements elements, String attribute) {
        String content = "";

        if (elements != null) {
            for (Element element : elements) {
                if (element.hasAttr(attribute)) {
                    String tempContent = element.attr(attribute);

                    if (StringUtils.isNotEmpty(tempContent)) {
                        content = tempContent;
                        break;
                    }
                }
            }
        }

        return content;
    }

}
