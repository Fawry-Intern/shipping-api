package com.fawry.shipping_api.util;

import com.fawry.shipping_api.entity.DeliveryPerson;
import com.fawry.shipping_api.entity.WorkDay;
import com.fawry.shipping_api.repository.DeliveryPersonRepository;
import com.fawry.shipping_api.repository.WorkDayRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;

@AllArgsConstructor
@Component
public class DeliveryPersonInitializer implements CommandLineRunner {

    private final DeliveryPersonRepository deliveryPersonRepository;

    private final WorkDayRepository workAreaDayRepository;
    @Override
    public void run(String... args) {
        if (deliveryPersonRepository.count() == 0) {

            List<DeliveryPerson> deliveryPersons = List.of(
                    DeliveryPerson.builder()
                            .name("mustafa1")
                            .email("mustafatarek660@gmail.com")
                            .phoneNumber("01021580267")
                            .build(),
                    DeliveryPerson.builder()
                            .name("mustafa2")
                            .email("mustafatarek500@gmail.com")
                            .phoneNumber("01021580289")
                            .build(),
                    DeliveryPerson.builder()
                            .name("mustafa3")
                            .email("mustafatarek700@gmail.com")
                            .phoneNumber("010215802100")
                            .build()

            );

            deliveryPersonRepository.saveAll(deliveryPersons);

            List<WorkDay>workAreaDays = List.of(
              WorkDay.builder()
                      .deliveryPerson(deliveryPersons.get(0))
                      .deliveryPerson(deliveryPersons.get(1))
                      .workDay(DayOfWeek.SATURDAY)
                      .build()
                    ,
                    WorkDay.builder()
                            .deliveryPerson(deliveryPersons.get(0))
                            .deliveryPerson(deliveryPersons.get(2))
                            .workDay(DayOfWeek.FRIDAY)
                            .build()
                    ,
                    WorkDay.builder()
                            .deliveryPerson(deliveryPersons.get(0))
                            .deliveryPerson(deliveryPersons.get(2))
                            .workDay(DayOfWeek.WEDNESDAY)
                            .build()
                    ,
                    WorkDay.builder()
                            .deliveryPerson(deliveryPersons.get(1))
                            .workDay(DayOfWeek.THURSDAY)
                            .build()
            );
            workAreaDayRepository.saveAll(workAreaDays);
        }
    }
}
