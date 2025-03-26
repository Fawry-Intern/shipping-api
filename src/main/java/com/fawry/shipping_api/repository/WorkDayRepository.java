package com.fawry.shipping_api.repository;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay,Long> {

    @Query("""
    SELECT d FROM DeliveryPerson d 
    JOIN d.workDays w 
    WHERE w.workDay = :dayOfWeek
""")
    List<DeliveryPerson> findByWorkDay(DayOfWeek dayOfWeek);

}
