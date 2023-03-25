package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        checkIfProductExist(id);

        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product add(Product product) throws IllegalAccessException {
        validateProduct(product);
            return productRepository.save(product);
    }

    @Override
    public Product update(int id, Product product) throws IllegalAccessException {
        validateProduct(product);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public Product delete(int id) {
    productRepository.deleteById(id);
        return null;
    }

    private void validateProduct(Product product) throws IllegalAccessException {
        checkIfDescriptionValid(product);
        checkIfPriceValid(product);
        checkIfQUantityValid(product);
    }

    private void checkIfProductExist(int id) {
        if (!productRepository.existsById(id)) throw new RuntimeException("ürün bulunamadı");

    }

    private void checkIfPriceValid(Product product) throws IllegalAccessException {
        if (product.getPrice() <= 0) throw new IllegalAccessException("Fiyat sıfırdan az veya eşit olamaz");
    }
    private void checkIfQUantityValid(Product product) throws IllegalAccessException {
        if (product.getPrice()<0) throw  new IllegalAccessException("miktar 0'dan az olamaz");

    }
    private void checkIfDescriptionValid(Product product) throws IllegalAccessException {
        if (product.getDescription().length()<10 ||product.getDescription().length()>50)throw new IllegalAccessException("açıklama 10 karakterden az veya 50 den fazla olamaz");

    }

}
