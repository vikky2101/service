package com.demo.controller;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/service/solr")
@Slf4j
public class SolrConfigController {


  @GetMapping("/reload")
  public ResponseEntity<String> reloadCollection(HttpServletRequest request,
      @RequestParam(required = true) String collectionName)
      throws MalformedURLException {
    RestTemplate restTemplate = new RestTemplate();
    String baseUrl =
        "http://localhost:8983/solr/admin/collections?action=RELOAD&name=" + collectionName;
    ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);
    return result;
  }

  @GetMapping("/createAlias")
  public ResponseEntity<String> createAlias() {
    RestTemplate restTemplate = new RestTemplate();
    String url1 = "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=film_service_live&collections=film_service_A";
    String url2 = "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=film_service_shadow&collections=film_service_B";

    String url3 = "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=product_service_live&collections=product_service_A";
    String url4 = "http://localhost:8983/solr/admin/collections?action=CREATEALIAS&name=product_service_shadow&collections=product_service_B";

    ResponseEntity<String> result = restTemplate.getForEntity(url1, String.class);
    result = restTemplate.getForEntity(url2, String.class);
    result = restTemplate.getForEntity(url3, String.class);
    result = restTemplate.getForEntity(url4, String.class);
    return result;
  }

  @GetMapping("/index")
  public ResponseEntity<String> index() {
    RestTemplate restTemplate = new RestTemplate();
    JSONParser parser = new JSONParser();
    try {
      File file = ResourceUtils.getFile("classpath:books.json");
      JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));
      for (Object object : jsonArray) {
        JSONObject jsonObject = (JSONObject) object;
        String url = "http://localhost:8090/service/products/publish";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(jsonObject.toString(), headers);
        log.info("Request Body : {}", ((JSONObject) object).toJSONString());
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
      }
    } catch (Exception e) {
      log.error("Exception {}", e);
    }
    return ResponseEntity.status(200).body("indexing done");
  }

}
