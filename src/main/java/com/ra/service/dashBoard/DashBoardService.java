package com.ra.service.dashBoard;

import com.ra.model.dto.response.BestSellerProduct;
import com.ra.model.dto.response.TotalPriceByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DashBoardService {
    Double totalPriceByTime(LocalDate startDate, LocalDate endDate);
    Page<BestSellerProduct> productBestSeller(Pageable pageable);
    List<TotalPriceByCategory> totalPriceByCategory();
}
