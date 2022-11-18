package com.sonal.demo.productssvc.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonal.demosvc.vo.CreateProductRequestVO;
import com.sonal.demosvc.vo.CreateProductResponseVO;
import com.sonal.demosvc.vo.UpdateProductRequestVO;
import com.sonal.demosvc.vo.UpdateProductResponseVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/products")
@Api(tags = "products")
public class ProductsController {

    @ApiOperation(value = "This web method is used to get all products.")
    @GetMapping
    public ResponseEntity<List<String>> getProducts() {
        return ResponseEntity.ok(Arrays.asList("Bats", "Balls", "Stumps"));
    }
    
    @ApiOperation(value = "This web method is used to product by porductId")
    @GetMapping("/{productId}")
    public ResponseEntity<String> getProducts(@PathVariable (value = "productId") String productId) {
        return ResponseEntity.ok("Product-" + productId);
    }
    
    @ApiOperation(value = "This web method is used to cerate a product.")
    @PostMapping("/{productId}")
    public ResponseEntity<CreateProductResponseVO> createProduct(@PathVariable(value = "productId") String productId, @RequestBody CreateProductRequestVO productRequestVO) {
        return ResponseEntity.ok(CreateProductResponseVO.builder()
        												.result(productRequestVO.getProductName() + " Created.")
        												.build());
    }
    
    @ApiOperation(value = "This web method is used to update a products")
    @PutMapping("/{productId}")
    public ResponseEntity<UpdateProductResponseVO> updateProduct(@PathVariable(value = "productId") String productId, @RequestBody UpdateProductRequestVO productRequestVO) {
        return ResponseEntity.ok(UpdateProductResponseVO.builder()
        												.result(productRequestVO.getProductName() + " Updated.")
        												.build());
    }
    
    @ApiOperation(value = "This web method is used to delete product by ProductId.")
    @DeleteMapping("/{productId}")
    public ResponseEntity<UpdateProductResponseVO> deleteProduct(@PathVariable(value = "productId") String productId) {
        return ResponseEntity.ok(UpdateProductResponseVO.builder()
        												.result(productId + " Deleted.")
        												.build());
    }

}
