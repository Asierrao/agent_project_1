package com.agentproject.purchase.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.purchase.entity.PurchaseOrder;
import com.agentproject.purchase.entity.PurchaseOrderItem;
import com.agentproject.purchase.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("采购订单不存在"));
    }

    @Transactional
    public PurchaseOrder create(PurchaseOrder order) {
        order.setOrderNo(generateOrderNo());
        order.setStatus(PurchaseOrder.OrderStatus.DRAFT);
        order.setOrderDate(LocalDate.now());
        if (order.getItems() != null) {
            BigDecimal total = BigDecimal.ZERO;
            for (PurchaseOrderItem item : order.getItems()) {
                item.setPurchaseOrder(order);
                if (item.getSubtotal() == null && item.getUnitPrice() != null) {
                    item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
                total = total.add(item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO);
            }
            order.setTotalAmount(total);
        }
        return purchaseOrderRepository.save(order);
    }

    @Transactional
    public PurchaseOrder updateStatus(Long id, PurchaseOrder.OrderStatus status) {
        PurchaseOrder existing = findById(id);
        validateStatusTransition(existing.getStatus(), status);
        existing.setStatus(status);
        return purchaseOrderRepository.save(existing);
    }

    private void validateStatusTransition(PurchaseOrder.OrderStatus current, PurchaseOrder.OrderStatus target) {
        if (current == PurchaseOrder.OrderStatus.DRAFT) {
            if (target != PurchaseOrder.OrderStatus.SUBMITTED) {
                throw new BusinessException("草稿状态只能提交");
            }
        } else if (current == PurchaseOrder.OrderStatus.SUBMITTED) {
            if (target != PurchaseOrder.OrderStatus.APPROVED && target != PurchaseOrder.OrderStatus.REJECTED) {
                throw new BusinessException("已提交状态只能审批或驳回");
            }
        } else if (current == PurchaseOrder.OrderStatus.APPROVED) {
            if (target != PurchaseOrder.OrderStatus.RECEIVED) {
                throw new BusinessException("已审批状态只能收货");
            }
        } else {
            throw new BusinessException("当前状态不允许变更");
        }
    }

    @Transactional
    public void delete(Long id) {
        PurchaseOrder existing = findById(id);
        if (existing.getStatus() != PurchaseOrder.OrderStatus.DRAFT) {
            throw new BusinessException("只能删除草稿状态的订单");
        }
        purchaseOrderRepository.deleteById(id);
    }

    private String generateOrderNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = purchaseOrderRepository.count() + 1;
        return "PO" + datePart + String.format("%04d", count % 10000);
    }
}
