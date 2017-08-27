package cn.likole.BasicInfo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by likole on 8/22/17.
 */
public class BasicInfoSpider implements PageProcessor {

    private Site site = Site.me()
            .setSleepTime(1000)
            .setRetryTimes(3)
            //encode
            .setCharset("gbk")
            .setRetrySleepTime(3000)
            //header
            .addHeader("User_Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36")
            //proxy
//            .setHttpProxy(new HttpHost("127.0.0.1",1080))
            //cookie
            .addHeader("Cookie", "JSESSIONID=");

    @Override
    public void process(Page page) {
        page.putField("name", page.getHtml().xpath("//table[@id=\"report1\"]//tr[2]/td[2]/text()").toString());
        page.putField("id", page.getHtml().xpath("//table[@id=\"report1\"]//tr[2]/td[4]/text()").toString());
        page.putField("gender", page.getHtml().xpath("//table[@id=\"report1\"]//tr[2]/td[6]/text()").toString());
        page.putField("idcard", page.getHtml().xpath("//table[@id=\"report1\"]//tr[2]/td[8]/text()").toString());
        page.putField("nation", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[2]/text()").toString());

        if ("籍贯".equals(page.getResultItems().get("nation"))) {
            page.putField("nation", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[1]/text()").toString());
            page.putField("jg", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[3]/text()").toString());
            page.putField("zz", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[5]/text()").toString());
            page.putField("bj", page.getHtml().xpath("//table[@id=\"report1\"]//tr[4]/td[1]/text()").toString());
            page.putField("zy", page.getHtml().xpath("//table[@id=\"report1\"]//tr[5]/td[1]/text()").toString());
            page.putField("xs", page.getHtml().xpath("//table[@id=\"report1\"]//tr[6]/td[1]/text()").toString());
        } else {
            page.putField("jg", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[4]/text()").toString());
            page.putField("zz", page.getHtml().xpath("//table[@id=\"report1\"]//tr[3]/td[6]/text()").toString());
            page.putField("bj", page.getHtml().xpath("//table[@id=\"report1\"]//tr[4]/td[2]/text()").toString());
            page.putField("zy", page.getHtml().xpath("//table[@id=\"report1\"]//tr[5]/td[2]/text()").toString());
            page.putField("xs", page.getHtml().xpath("//table[@id=\"report1\"]//tr[6]/td[2]/text()").toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
