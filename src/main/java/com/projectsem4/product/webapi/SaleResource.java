package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Product;
import com.projectsem4.product.entity.Sale;
import com.projectsem4.product.service.SaleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/sale/v1")
public class SaleResource {
    private final SaleService saleService;

    public SaleResource(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/product/{productId}/sale")
    public Optional<Sale> sale(@PathVariable(value = "productId")Long productId,
                               @Valid @RequestBody Sale sale){
        return saleService.save(productId, sale);
    }

    @GetMapping("/product/{productId}/sale")
    public List<Sale> getAllSales(@PathVariable Long productId){
        return saleService.findAll();
    }

    @PutMapping("/product/{productId}/sales/{saleId}")
    public Sale updateProduct(@PathVariable (value = "productId") Long productId,
                                 @PathVariable (value = "saleId") Long saleId,
                                 @Valid @RequestBody Sale saleRequest){
        return saleService.updateSale(productId, productId, saleRequest);
    }

    @DeleteMapping("/product/{productId}/sales/{saleId}")
    public void deleteProduct(@PathVariable (value = "productId") Long productId,
                              @PathVariable (value = "saleId") Long saleId){
        saleService.delete(productId, saleId);
    }
}
