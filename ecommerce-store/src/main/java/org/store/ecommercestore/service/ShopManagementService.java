package org.store.ecommercestore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.store.ecommercestore.exceptions.CustomException;
import org.store.ecommercestore.mapper.ProductRequest;
import org.store.ecommercestore.mapper.mapperhelper.ProductImage;
import org.store.ecommercestore.model.CategoryEntity;
import org.store.ecommercestore.model.OrderEntity;
import org.store.ecommercestore.model.ProductEntity;
import org.store.ecommercestore.repository.CategoryEntityRepository;
import org.store.ecommercestore.repository.OrderRepository;
import org.store.ecommercestore.repository.ProductEntityRepository;
import org.store.ecommercestore.repository.UserEntityRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


/**
 * Class mainly responsible for ADMIN functionality, editing product, adding etc.
 *
 */

@Service
public class ShopManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ShopManagementService.class);

    @Value("${e-commerce.product.images.dir}")
    private String imagesUploadUrl;

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Autowired
    private CategoryEntityRepository categoryEntityRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private OrderRepository orderRepository;


    /**
     *
     * @param productEntity - refers to productEntity, which is about to be saved, productImage is imageFile and src to productEntity
     * @param productImage - productImage Base64Format image for productEntity src field and overall image by itself
     * @return returns true if process finished successfully, otherwise false
     */
    public boolean addProduct(ProductEntity productEntity, ProductImage productImage){
        if(downloadProductImage(productImage)) {
            productEntity.setPrice(productEntity.getPrice().setScale(2, RoundingMode.HALF_UP));
            productEntity.setImageUrl("assets/products-images/" + productImage.getFileName());
            productEntity.setActive(true);
            productEntityRepository.save(productEntity);
            return true;
        }

        return false;
    }

    /**
     * Deleting category by id
     * @param id - id in long format, specifies which category should be deleted
     */

    public void deleteCategory(Long id){
        categoryEntityRepository.deleteById(id);
    }

    /**
     *
     * @param name - variable which value should be name of the product to be found
     * @return returns found product by its name
     */
    public Page<ProductEntity> findProductsByName(String name, int page, int size){
        return productEntityRepository.findByNameContaining(name, PageRequest.of(page, size));
    }

    /**
     *
     * @param id productEntity id - any id of product that exists in database
     * @return productEntity of specified id
     */
    public ProductEntity getProductById(Long id){
        try {
            return productEntityRepository.findById(id)
                            .orElseThrow(() -> new CustomException("Could not find product for specified ID"));
        } catch (CustomException e) {
            logger.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public void addCategory(CategoryEntity categoryEntity){
        categoryEntityRepository.save(categoryEntity);
    }

    public List<CategoryEntity> getCategories(){
       return categoryEntityRepository.findAll();
    }

    public void deleteProductById(Long id){
        productEntityRepository.deleteById(id);
    }


    /**
     * Responsible for updating ProductEntity, if request productEntity imageUrl equals changed, method downloadProductImage() is called
     * @param editProductRequest - request object containing productEntity and productImage
     * @return returns true if update was successful
     */
    public boolean editProduct(ProductRequest editProductRequest) {
        ProductEntity productEntity = editProductRequest.getProductInfo();
       if(productEntity.getImageUrl().equals("changed")){
           if(downloadProductImage(editProductRequest.getProductImage())) {
               deleteOldImage(editProductRequest.getProductInfo().getId());
               productEntity.setImageUrl("assets/products-images/" + editProductRequest.getProductImage().getFileName());
               productEntityRepository.save(productEntity);
               return true;
           } else {
               return false;
           }
       }

        productEntityRepository.save(editProductRequest.getProductInfo());

        return true;
    }

    /**
     * Method used when updating ProductEntity image, deletes old image file which will be replaced by new one
     * @param productId productEntity Id to be updated
     *
     */

    private void deleteOldImage(Long productId){
            Optional<ProductEntity> product =  productEntityRepository.findById(productId);
            product.ifPresent(productEntity -> {

                try {
                    Files.deleteIfExists(Paths.get(imagesUploadUrl + productEntity.getImageUrl().substring(productEntity.getImageUrl().lastIndexOf("/"))));
                } catch (IOException e) {
                    logger.error(e.getMessage(), e.getCause());
                }
            });
    }

    /**
     * Responsibility - decoding Base64 format image file and uploading it on server. Image file corresponds to ProductEntity image
     *
     * @param multipartFile productEntity image that should be uploaded
     * @return returns true if uploading image has been successful otherwise false and exception
     *
     */

    private boolean downloadProductImage(ProductImage multipartFile)  {

        if(!multipartFile.getImageBase64().isEmpty()) {
            try {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(Base64.getMimeDecoder().decode(multipartFile.getImageBase64().split(",")[1])));
                File outputFile = new File(imagesUploadUrl + multipartFile.getFileName());
                ImageIO.write(image, "jpg", outputFile);
                return true;
            } catch (IOException e) {
                logger.error(e.getMessage(), e.fillInStackTrace());
            }
        }

        return false;
    }

    public Page<CategoryEntity> getCategoriesByName(String name, int page, int size){
        return categoryEntityRepository.findByCategoryNameContaining(name, PageRequest.of(page, size));
    }

    public Page<OrderEntity> getOrdersByUsername(String username, int page, int size){
        return orderRepository.findByOrderHolderUsername(username, PageRequest.of(page, size));
    }


    public Page<CategoryEntity> getPaginationCategories(int page, int size) {
        return categoryEntityRepository.findAll(PageRequest.of(page, size));
    }
}
