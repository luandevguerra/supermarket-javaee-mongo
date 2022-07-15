package me.shockyng.api.resources;

import me.shockyng.api.data.dtos.ProductDTO;
import me.shockyng.api.exceptions.ProductDoesNotExistException;
import me.shockyng.api.services.ProductsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @Inject
    private ProductsService service;

    @POST
    public ProductDTO createProduct(ProductDTO productDTO) {
        return service.createProduct(productDTO);
    }

    @GET
    @Path("/{id}")
    public ProductDTO getProduct(@PathParam("id") String id) {
        return service.getProduct(id);
    }

    @GET
    public List<ProductDTO> getAllProducts() {
        return service.getAllProducts();
    }

    @PUT
    @Path("/{id}")
    public ProductDTO updateProduct(@PathParam("id") String id, ProductDTO productDTO) {
        return service.updateProduct(id, productDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String id) {
        try {
            service.deleteProduct(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ProductDoesNotExistException e) {
            return Response.ok(e.getMessage()).status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/search")
    public List<ProductDTO> searchProduct(@QueryParam("productName") String productName) {
        return service.searchProductByName(productName);
    }

    @GET
    @Path("/native-search")
    public List<ProductDTO> nativeSearchProduct(@QueryParam("productName") String productName) {
        return service.nativeSearchProductByName(productName);
    }
}
