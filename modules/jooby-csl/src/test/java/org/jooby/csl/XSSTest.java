package csl;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import org.jooby.Env;
import org.jooby.csl.XSS;
import org.jooby.test.MockUnit;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class XSSTest {

  @Test
  public void xssShouldRun() throws Exception {
    XSS subject = new XSS();

    new MockUnit(Env.class, Config.class, Binder.class)
        .expect(unit -> {
          Env env = unit.get(Env.class);
          expect(env.xss(eq("html"), anyObject())).andReturn(env);
          expect(env.xss(eq("htmlText"), anyObject())).andReturn(env);
          expect(env.xss(eq("js"), anyObject())).andReturn(env);
          expect(env.xss(eq("jsRegex"), anyObject())).andReturn(env);
          expect(env.xss(eq("css"), anyObject())).andReturn(env);
          expect(env.xss(eq("uri"), anyObject())).andReturn(env);
        })
        .run(unit -> {
          Env env = unit.get(Env.class);
          Config config = unit.get(Config.class);
          Binder binder = unit.get(Binder.class);
          subject.configure(env, config, binder);
        });

  }
}
