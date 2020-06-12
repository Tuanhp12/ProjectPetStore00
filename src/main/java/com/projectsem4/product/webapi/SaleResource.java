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
@CrossOrigin
public class SaleResource {
    private final SaleService saleService;

    public SaleResource(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/")
    public Sale createSale(@Valid @RequestBody Sale sale){
        return saleService.saveOrUpDate(sale);
    }

    @GetMapping("/")
    public List<Sale> getAllSales(){
        return saleService.findAll();
    }

    @DeleteMapping("/{saleId}")
    public void deleteProduct(@PathVariable (value = "saleId") Long saleId){
        saleService.delete(saleId);
    }
}

//@PutMapping("/product/{productId}/sales/{saleId}")
//    public Sale updateProduct(@PathVariable (value = "productId") Long productId,
//                                 @PathVariable (value = "saleId") Long saleId,
//                                 @Valid @RequestBody Sale saleRequest){
//        return saleService.updateSale(productId, productId, saleRequest);
//    }
