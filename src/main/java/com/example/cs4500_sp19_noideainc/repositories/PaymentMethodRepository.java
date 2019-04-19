package com.example.cs4500_sp19_noideainc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4500_sp19_noideainc.models.PaymentMethod;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Integer> {
    @Query(value="SELECT paymentMethod FROM PaymentMethod paymentMethod")
    public List<PaymentMethod> findAllPaymentMethods();
    @Query(value="SELECT paymentMethod FROM PaymentMethod paymentMethod WHERE paymentMethod.id=:id")
    public PaymentMethod findPaymentMethodById(@Param("id") Integer id);
}