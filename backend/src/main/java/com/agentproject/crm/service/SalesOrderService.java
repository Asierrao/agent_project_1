package com.agentproject.crm.service;

import com.agentproject.common.exception.BusinessException;
import com.agentproject.crm.entity.SalesOrder;
import com.agentproject.crm.entity.SalesOrderItem;
import com.agentproject.crm.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository) {
        this.salesOrderRepository = salesOrderRepository;
    }

    public List<SalesOrder> findAll() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder findById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("销售订单不存在"));
    }

    @Transactional
    public SalesOrder create(SalesOrder order) {
        order.setOrderNo(generateOrderNo());
        order.setStatus(SalesOrder.OrderStatus.DRAFT);
        order.setOrderDate(LocalDate.now());
        if (order.getItems() != null) {
            BigDecimal total = BigDecimal.ZERO;
            for (SalesOrderItem item : order.getItems()) {
                item.setSalesOrder(order);
                if (item.getSubtotal() == null && item.getUnitPrice() != null) {
                    item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
                total = total.add(item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO);
            }
            order.setTotalAmount(total);
        }
        return salesOrderRepository.save(order);
    }

    @Transactional
    public SalesOrder updateStatus(Long id, SalesOrder.OrderStatus status) {
        SalesOrder existing = findById(id);
        validateStatusTransition(existing.getStatus(), status);
        existing.setStatus(status);
        return salesOrderRepository.save(existing);
    }

    private void validateStatusTransition(SalesOrder.OrderStatus current, SalesOrder.OrderStatus target) {
        if (current == SalesOrder.OrderStatus.DRAFT) {
            if (target != SalesOrder.OrderStatus.SUBMITTED) {
                throw new BusinessException("草稿状态只能提交");
            }
        } else if (current == SalesOrder.OrderStatus.SUBMITTED) {
            if (target != SalesOrder.OrderStatus.APPROVED && target != SalesOrder.OrderStatus.REJECTED) {
                throw new BusinessException("已提交状态只能审批或驳回");
            }
        } else if (current == SalesOrder.OrderStatus.APPROVED) {
            if (target != SalesOrder.OrderStatus.DELIVERED) {
                throw new BusinessException("已审批状态只能发货");
            }
        } else {
            throw new BusinessException("当前状态不允许变更");
        }
    }

    @Transactional
    public void delete(Long id) {
        SalesOrder existing = findById(id);
        if (existing.getStatus() != SalesOrder.OrderStatus.DRAFT) {
            throw new BusinessException("只能删除草稿状态的订单");
        }
        salesOrderRepository.deleteById(id);
    }

    private String generateOrderNo() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = salesOrderRepository.count() + 1;
        return "SO" + datePart + String.format("%04d", count % 10000);
    }
}
