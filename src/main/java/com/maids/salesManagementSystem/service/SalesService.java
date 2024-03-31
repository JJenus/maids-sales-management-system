package com.maids.salesManagementSystem.service;

import com.maids.salesManagementSystem.entity.Client;
import com.maids.salesManagementSystem.entity.Product;
import com.maids.salesManagementSystem.entity.Sale;
import com.maids.salesManagementSystem.entity.Transaction;
import com.maids.salesManagementSystem.exception.ProductNotFoundException;
import com.maids.salesManagementSystem.exception.SaleNotFoundException;
import com.maids.salesManagementSystem.model.*;
import com.maids.salesManagementSystem.repository.ClientRepository;
import com.maids.salesManagementSystem.repository.ProductRepository;
import com.maids.salesManagementSystem.repository.SalesRepository;
import com.maids.salesManagementSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesService {
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LogService logService;


    public List<Sale> getAllSales() {
        return salesRepository.findAll();
    }

    public Sale createSale(SaleDTO saleDTO) {
        Sale sale = getSaleEntity(saleDTO);

        return salesRepository.save(sale);
    }

    public Sale updateSale(Long id, SaleDTO updatedSaleDTO) {
        Sale existingSale = salesRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found with id: " + id));

        Sale updatedSale = getSaleEntity(updatedSaleDTO);

        // Check for changes in the transactions and total
        if (areSalesUpdated(existingSale, updatedSale) || existingSale.getTotal() != updatedSale.getTotal()) {
            logService.logUpdate(existingSale, "Sales", existingSale.getTransactions().toString(), updatedSale.getTransactions().toString());
            existingSale.setTransactions(updatedSale.getTransactions());
            existingSale.setTotal(updatedSale.getTotal());
        }

        // Save the updated sale
        return updatedSale;
    }

    public boolean areSalesUpdated(Sale existingSale, Sale updatedSale) {
        List<Transaction> existingTransactions = existingSale.getTransactions();
        List<Transaction> updatedTransactions = updatedSale.getTransactions();

        // Calculate total quantity and price for existing sale
        int existingTotalQuantity = 0;
        double existingTotalPrice = 0.0;
        for (Transaction transaction : existingTransactions) {
            existingTotalQuantity += transaction.getQuantity();
            existingTotalPrice += transaction.getPrice();
        }

        // Calculate total quantity and price for updated sale
        int updatedTotalQuantity = 0;
        double updatedTotalPrice = 0.0;
        for (Transaction transaction : updatedTransactions) {
            updatedTotalQuantity += transaction.getQuantity();
            updatedTotalPrice += transaction.getPrice();
        }

        // Compare total quantity and price
        return existingTotalQuantity != updatedTotalQuantity || Double.compare(existingTotalPrice, updatedTotalPrice) != 0;
    }

    public Sale getSaleEntity(SaleDTO saleDTO){
        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setClient(saleDTO.getClient());
        sale.setSeller(saleDTO.getSeller());
        sale.setCreationDate(saleDTO.getCreationDate());

        List<Transaction> transactions = new ArrayList<>();

        Sale saleWithID;

        if (saleDTO.getId() == null){
            saleWithID = salesRepository.save(sale);
        } else {
            saleWithID = sale; // on update
        }

        double total = 0;

        for (Transaction transaction : saleDTO.getTransactions()) {
            // Fetch the product from the database
            Product product = productRepository.findById(transaction.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // Set product details in the transaction entity
            transaction.setProduct(product);

            // Calculate price for the transaction using the actual product price from the database
            double price = product.getPrice() * transaction.getQuantity();
            transaction.setPrice(price);
            transaction.setSale(saleWithID);

            Transaction transactionWithID;

            if (transaction.getId() == null){
                transactionWithID = transactionRepository.save(transaction);
            } else {
                transactionWithID = transaction; // on update
            }

            // Add the transaction to the sale
            transactions.add(transactionWithID);

            // Update total for the sale
            total += price;
        }

        saleWithID.setTotal(total);
        saleWithID.setTransactions(transactions);


        return salesRepository.save(saleWithID);
    }

    public SalesReport generateSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        // Fetch all sales transactions within the specified date range
        List<Sale> sales = salesRepository.findByCreationDateBetween(startDate, endDate);

        // Calculate total number of sales and total revenue
        int totalSales = sales.size();
        double totalRevenue = sales.stream().mapToDouble(Sale::getTotal).sum();

        // Determine top-selling products based on quantity sold
        Map<Product, Integer> productSalesMap = new HashMap<>();
        for (Sale sale : sales) {
            for (Transaction transaction : sale.getTransactions()) {
                Product product = transaction.getProduct();
                int quantitySold = transaction.getQuantity();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0) + quantitySold);
            }
        }
        List<SalesStatistic> topSellingProducts = productSalesMap.entrySet().stream()
                .map(entry -> new SalesStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(SalesStatistic::getQuantitySold).reversed())
                .collect(Collectors.toList());

        // Identify top-performing sellers based on sales revenue
        Map<Client, Double> sellerRevenueMap = new HashMap<>();
        for (Sale sale : sales) {
            Client seller = sale.getSeller();
            double saleRevenue = sale.getTotal();
            sellerRevenueMap.put(seller, sellerRevenueMap.getOrDefault(seller, 0.0) + saleRevenue);
        }
        List<PerformanceStatistic> topPerformingSellers = sellerRevenueMap.entrySet().stream()
                .map(entry -> new PerformanceStatistic(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingDouble(PerformanceStatistic::getTotalRevenue).reversed())
                .collect(Collectors.toList());

        // Construct the sales report
        SalesReport salesReport = new SalesReport();
        salesReport.setStartDate(startDate);
        salesReport.setEndDate(endDate);
        salesReport.setTotalNumberOfSales(totalSales);
        salesReport.setTotalRevenue(totalRevenue);
        salesReport.setTopSellingProducts(topSellingProducts);
        salesReport.setTopPerformingSellers(topPerformingSellers);

        return salesReport;
    }

}
