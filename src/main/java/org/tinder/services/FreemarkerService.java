package org.tinder.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.HashMap;

public class FreemarkerService {

  private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

  public FreemarkerService(String root) throws IOException, URISyntaxException {
    System.out.println(getPath(root));
    cfg.setDirectoryForTemplateLoading(
      new File(getPath(root))
    );
  }

  public void render(String template, HashMap<String, Object> data, Writer w) {
    try {
      cfg.getTemplate(template)
        .process(data, w);
    } catch (TemplateException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getPath(String name) throws URISyntaxException {
    System.out.println("path" + FreemarkerService.class
            .getClassLoader()
            .getResource(name)
            .toURI()
            .getPath());
    return FreemarkerService.class
            .getClassLoader()
            .getResource(name)
            .toURI()
            .getPath();
  }
}
