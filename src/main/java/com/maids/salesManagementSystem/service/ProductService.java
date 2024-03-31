package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Product;
import com.maids.salesManagementSystem.entity.Transaction;
import com.maids.salesManagementSystem.exception.ProductNotFoundException;
import com.maids.salesManagementSystem.model.ProductReport;
import com.maids.salesManagementSystem.repository.ProductRepository;
import com.maids.salesManagementSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LogService logService;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Check for changes in the product data and log updates
        if (!existingProduct.getName().equals(updatedProduct.getName())) {
            logService.logUpdate(existingProduct, "Name", existingProduct.getName(), updatedProduct.getName());
            existingProduct.setName(updatedProduct.getName());
        }
        if (!existingProduct.getDescription().equals(updatedProduct.getDescription())) {
            logService.logUpdate(existingProduct, "Description", existingProduct.getDescription(), updatedProduct.getDescription());
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (!existingProduct.getCategory().equals(updatedProduct.getCategory())) {
            logService.logUpdate(existingProduct, "Category", existingProduct.getCategory(), updatedProduct.getCategory());
            existingProduct.setCategory(updatedProduct.getCategory());
        }
        if (existingProduct.getQuantity() != updatedProduct.getQuantity()) {
            logService.logUpdate(existingProduct, "Quantity", String.valueOf(existingProduct.getQuantity()), String.valueOf(updatedProduct.getQuantity()));
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }
        if (existingProduct.getPrice() != updatedProduct.getPrice()) {
            logService.logUpdate(existingProduct, "Price", String.valueOf(existingProduct.getPrice()), String.valueOf(updatedProduct.getPrice()));
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        // Save the updated product
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    public List<ProductReport> generateProductReport() {
        // Fetch all products from the database
        List<Product> products = productRepository.findAll();

        List<ProductReport> productReports = new ArrayList<>();

        for (Product product : products) {
            // Calculate inventory status
            int totalQuantity = product.getQuantity();
            String inventoryStatus = (totalQuantity > 0) ? "In Stock" : "Out of Stock";

            // Calculate sales performance
            List<Transaction> transactions = transactionRepository.findByProduct(product);
            int totalUnitsSold = 0;
            double totalRevenue = 0.0;
            for (Transaction transaction : transactions) {
                totalUnitsSold += transaction.getQuantity();
                totalRevenue += transaction.getPrice() * transaction.getQuantity();
            }

            // Calculate pricing analysis
            double averagePrice = totalRevenue / totalUnitsSold; // Assuming all products are sold
            double minPrice = product.getPrice();
            double maxPrice = product.getPrice();
            for (Transaction transaction : transactions) {
                double transactionPricePerUnit = transaction.getPrice() / transaction.getQuantity();
                if (transactionPricePerUnit < minPrice) {
                    minPrice = transactionPricePerUnit;
                }
                if (transactionPricePerUnit > maxPrice) {
                    maxPrice = transactionPricePerUnit;
                }
            }
            double priceVariance = maxPrice - minPrice;

            // Populate product report with analysis results
            ProductReport productAnalysis = new ProductReport(product, inventoryStatus, totalUnitsSold, totalRevenue, averagePrice, minPrice, maxPrice, priceVariance);
            productReports.add(productAnalysis);
        }

        return productReports;
    }
}
