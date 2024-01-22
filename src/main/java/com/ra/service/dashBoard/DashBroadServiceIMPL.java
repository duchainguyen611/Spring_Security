package com.ra.service.dashBoard;

import com.ra.model.dto.response.BestSellerProduct;
import com.ra.model.dto.response.TotalPriceByCategory;
import com.ra.repository.CategoryRepository;
import com.ra.repository.OrdersRepository;
import com.ra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashBroadServiceIMPL implements DashBoardService{
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public Double totalPriceByTime(LocalDate startDate, LocalDate endDate) {
        return ordersRepository.totalPriceByTime(startDate,endDate);
    }

    @Override
    public Page<BestSellerProduct> productBestSeller(Pageable pageable) {
        return productRepository.getBestSellerProducts(pageable);
    }

    @Override
    public List<TotalPriceByCategory> totalPriceByCategory() {
        return categoryRepository.getTotalPriceByCategory();
    }
}
