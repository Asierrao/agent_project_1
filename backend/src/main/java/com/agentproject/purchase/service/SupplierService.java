package com.agentproject.purchase.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.purchase.entity.Supplier;
import com.agentproject.purchase.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new BusinessException("供应商不存在"));
    }

    public Supplier create(Supplier supplier) {
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new BusinessException("供应商名称已存在");
        }
        return supplierRepository.save(supplier);
    }

    public Supplier update(Long id, Supplier supplier) {
        Supplier existing = findById(id);
        existing.setName(supplier.getName());
        existing.setContact(supplier.getContact());
        existing.setPhone(supplier.getPhone());
        existing.setEmail(supplier.getEmail());
        existing.setAddress(supplier.getAddress());
        existing.setDescription(supplier.getDescription());
        return supplierRepository.save(existing);
    }

    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new BusinessException("供应商不存在");
        }
        supplierRepository.deleteById(id);
    }
}
