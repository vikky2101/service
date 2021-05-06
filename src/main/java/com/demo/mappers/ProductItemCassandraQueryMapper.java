package com.demo.mappers;

import com.demo.models.Payload;
import com.demo.models.QueryMapper;
import com.demo.cassandra.model.ProductDataModel;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ProductItemCassandraQueryMapper implements QueryMapper {

  public ProductDataModel map(Payload payload) {
    ProductDataModel productDataModel = new ProductDataModel();
    Map<String, Object> params = payload.getParams();
    if (params.containsKey("id")) {
      productDataModel.setId(Long.parseLong(params.get("id").toString()));
    }

    if (params.containsKey("name")) {
      productDataModel.setName((String) params.get("name"));
    }

    if (params.containsKey("cat")) {
      productDataModel.setCat((List<String>) params.get("cat"));
    }
    if (params.containsKey("author")) {
      productDataModel.setAuthor((String) params.get("author"));
    }
    if (params.containsKey("series_t")) {
      productDataModel.setSeries_t((String) params.get("series_t"));
    }
    if (params.containsKey("sequence_i")) {
      productDataModel.setSequence_i((Integer) (params.get("sequence_i")));
    }
    if (params.containsKey("inStock")) {
      productDataModel.setInStock((Boolean) params.get("inStock"));
    }
    if (params.containsKey("price")) {
      productDataModel.setPrice((Double) params.get("price"));
    }
    if (params.containsKey("pages_i")) {
      productDataModel.setPages_i((Integer) params.get("pages_i"));
    }
    return productDataModel;

  }

}
