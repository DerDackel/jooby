package org.jooby;

import org.jooby.test.ServerFeature;
import org.junit.Test;

public class AssetLocationFeature extends ServerFeature {

  {
    assets("/", "welcome.html");

    assets("/js/lib/*-*-*.js", "/META-INF/resources/webjars/{0}/{1}-{2}/{0}.js");
    assets("/js/lib/*-*.js", "/META-INF/resources/webjars/{0}/{1}/{0}.js");

    assets("/js/**", "/META-INF/resources/webjars/{0}");

  }

  @Test
  public void welcome() throws Exception {
    request()
        .get("/")
        .expect(200)
        .header("Content-Type", "text/html;charset=UTF-8")
        .header("Last-Modified", lastModified -> {
          request()
              .get("/")
              .header("If-Modified-Since", lastModified)
              .expect(304)
              .empty();
        });
  }

  @Test
  public void webjars() throws Exception {
    request()
        .get("/js/jquery/3.3.1-1/jquery.js")
        .expect(200)
        .header("Content-Type", "application/javascript;charset=UTF-8")
        .header("Content-Length", "271751");

    request()
        .get("/js/jquery/3.3.1-1/jquery.min.js")
        .expect(200)
        .header("Content-Type", "application/javascript;charset=UTF-8")
        .header("Content-Length", "86927");
  }

  @Test
  public void webjarsMapping() throws Exception {
    request()
        .get("/js/lib/jquery-3.3.1-1.js")
        .expect(200)
        .header("Content-Type", "application/javascript;charset=UTF-8")
        .header("Content-Length", "271751");

  }

}
