package com.agentproject.inventory.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.inventory.entity.*;
import com.agentproject.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          WarehouseRepository warehouseRepository,
                          StockRepository stockRepository,
                          StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockRepository = stockRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    // === Category ===
    public List<Category> getCategoryTree() {
        return categoryRepository.findByParentIsNullOrderBySortOrder();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // === Product ===
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("商品不存在"));
    }

    public Product createProduct(Product product) {
        if (productRepository.existsByProductNo(product.getProductNo())) {
            throw new BusinessException("商品编号已存在");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existing = findProductById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setUnit(product.getUnit());
        existing.setPurchasePrice(product.getPurchasePrice());
        existing.setSalePrice(product.getSalePrice());
        existing.setCategoryId(product.getCategoryId());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // === Warehouse ===
    public List<Warehouse> findAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        if (warehouseRepository.existsByName(warehouse.getName())) {
            throw new BusinessException("仓库名称已存在");
        }
        return warehouseRepository.save(warehouse);
    }

    // === Stock ===
    public Stock getStock(Long productId, Long warehouseId) {
        return stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElse(new Stock());
    }

    public List<Stock> getStockByProduct(Long productId) {
        return List.of(getStock(productId, null));
    }

    @Transactional
    public StockMovement moveStock(Long productId, Long warehouseId,
                                    StockMovement.MovementType type, int quantity, String remark, String username) {
        Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseGet(() -> {
                    Stock s = new Stock();
                    s.setProductId(productId);
                    s.setWarehouseId(warehouseId);
                    s.setQuantity(0);
                    return s;
                });

        if (type == StockMovement.MovementType.IN) {
            stock.setQuantity(stock.getQuantity() + quantity);
        } else {
            if (stock.getQuantity() < quantity) {
                throw new BusinessException("库存不足");
            }
            stock.setQuantity(stock.getQuantity() - quantity);
        }
        stockRepository.save(stock);

        StockMovement movement = new StockMovement();
        movement.setProductId(productId);
        movement.setWarehouseId(warehouseId);
        movement.setType(type);
        movement.setQuantity(quantity);
        movement.setRemark(remark);
        movement.setCreatedBy(username);
        return stockMovementRepository.save(movement);
    }
}
