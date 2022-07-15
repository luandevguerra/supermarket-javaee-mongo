package me.shockyng.api.daos;


import me.shockyng.api.data.collections.Product;

public class ProductsDAO extends BaseDAO<Product, String> {

    @Override
    protected Class<Product> clazz() {
        return Product.class;
    }

}
