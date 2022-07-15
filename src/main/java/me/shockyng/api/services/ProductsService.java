package me.shockyng.api.services;

import me.shockyng.api.daos.ProductsDAO;
import me.shockyng.api.data.collections.Product;
import me.shockyng.api.data.dtos.ProductDTO;
import me.shockyng.api.exceptions.ProductDoesNotExistException;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsService {

    private final ModelMapper mapper = new ModelMapper();

    @Inject
    private ProductsDAO dao;

    public ProductDTO createProduct(ProductDTO productDTO) {
        return mapper.map(dao.save(mapper.map(productDTO, Product.class)), ProductDTO.class);
    }

    public ProductDTO getProduct(String id) {
        return mapper.map(dao.getById(id), ProductDTO.class);
    }

    public List<ProductDTO> getAllProducts() {
        return dao.getAll().stream().map(e -> mapper.map(e, ProductDTO.class)).collect(Collectors.toList());
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        ProductDTO foundProduct = getProduct(id);
        foundProduct.setName(productDTO.getName());
        foundProduct.setPrice(productDTO.getPrice());
        return mapper.map(dao.update(mapper.map(foundProduct, Product.class)), ProductDTO.class);
    }

    public void deleteProduct(String id) throws ProductDoesNotExistException {
        dao.deleteById(id);
    }

    public List<ProductDTO> searchProductByName(String productName) {
        return dao.searchProductByName(productName).stream().map(e -> mapper.map(e, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> nativeSearchProductByName(String productName) {
        return dao.nativeSearchProductByName(productName).stream().map(e -> mapper.map(e, ProductDTO.class))
                .collect(Collectors.toList());
    }
}
