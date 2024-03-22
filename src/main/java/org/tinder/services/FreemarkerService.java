package org.tinder.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

public class FreemarkerService {

  private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

  public FreemarkerService(String root){
    cfg.setClassForTemplateLoading(this.getClass(), "/" + root);
  }

  public void render(String template, HashMap<String, Object> data, Writer w) {
    try {
      cfg.getTemplate(template)
        .process(data, w);
    } catch (TemplateException | IOException e) {
      throw new RuntimeException(e);
    }
  }

}
