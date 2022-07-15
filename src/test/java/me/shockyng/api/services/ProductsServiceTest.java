package me.shockyng.api.services;

import me.shockyng.api.daos.ProductsDAO;
import me.shockyng.api.data.collections.Product;
import me.shockyng.api.data.dtos.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductsServiceTest {

    @InjectMocks
    private final ProductsService service = new ProductsService();

    @Mock
    private ProductsDAO dao;

    private final ModelMapper mapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnAnEmptyProductListWhenGetAllProductsIsCalled() {
        // scenario
        Mockito.when(dao.getAll()).thenReturn(new ArrayList<>());

        // action
        List<ProductDTO> products = service.getAllProducts();

        // verification
        assertTrue(products.isEmpty());
    }

    @Test
    void shouldReturnAProductListWithOneProductWhenGetAllProductsIsCalled() {
        // scenario
        Product product = new Product.Builder().id("testId").name("Rice").price(10.0).build();
        List<Product> expectedProductList = Collections.singletonList(product);
        Mockito.when(dao.getAll()).thenReturn(expectedProductList);

        // action
        List<ProductDTO> products = service.getAllProducts();

        // verification
        ProductDTO productDTO = products.get(0);
        assertFalse(products.isEmpty());
        assertEquals(expectedProductList.size(), products.size());
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getName(), product.getName());
        assertEquals(productDTO.getPrice(), product.getPrice());
    }

    @Test
    void shouldReturnAProductWhenCreateProductIsCalled() {
        // scenario
        Product product = new Product.Builder().id("testId").name("Rice").price(10.0).build();
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        Mockito.when(dao.save(any())).thenReturn(product);

        // action
        ProductDTO savedProduct = service.createProduct(productDTO);

        // verification
        assertEquals(productDTO.getId(), savedProduct.getId());
        assertEquals(productDTO.getName(), savedProduct.getName());
        assertEquals(productDTO.getPrice(), savedProduct.getPrice());
    }

    @Test
    void shouldReturnAProductWhenGetProductIsCalled() {
        // scenario
        Product product = new Product.Builder().id("testId").name("Rice").price(10.0).build();
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        Mockito.when(dao.getById(product.getId())).thenReturn(product);

        // action
        ProductDTO foundProduct = service.getProduct(product.getId());

        // verification
        assertEquals(productDTO.getId(), foundProduct.getId());
        assertEquals(productDTO.getName(), foundProduct.getName());
        assertEquals(productDTO.getPrice(), foundProduct.getPrice());
    }

}