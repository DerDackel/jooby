package org.jooby;

import org.jooby.test.ServerFeature;
import org.junit.Test;

public class AssetResolveNextFeature extends ServerFeature {

  {
    assets("/assets/**", "/")
        .onMissing(0);

    assets("/assets/js/*-*-*.js", "/META-INF/resources/webjars/{0}/{1}-{2}/{0}.js");
    assets("/assets/js/*-*.js", "/META-INF/resources/webjars/{0}/{1}/{0}.js");
  }

  @Test
  public void firstLocation() throws Exception {
    request()
        .get("/assets/js/file.js")
        .expect(200);
  }

  @Test
  public void secondLocation() throws Exception {
    request()
        .get("/assets/js/jquery-3.3.1-1.js")
        .expect(200);
  }

  @Test
  public void notFound() throws Exception {
    request()
        .get("/assets/js/missing.js")
        .expect(404);
  }

}
