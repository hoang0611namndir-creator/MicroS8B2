package org.example.appointmentservice.service;

import org.example.appointmentservice.client.DoctorClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.appointmentservice.dto.ApiResponseError;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final DoctorClient doctorClient;

    public AppointmentService(DoctorClient doctorClient) {
        this.doctorClient = doctorClient;
    }

    @CircuitBreaker(
            name = "doctorServiceCB",
            fallbackMethod = "doctorServiceFallback"
    )
    public String checkDoctorSchedule(Long doctorId) {

        System.out.println("Calling Doctor-Service...");

        return doctorClient.getDoctorSchedule(doctorId);
    }

    public String doctorServiceFallback(
            Long doctorId,
            Throwable throwable
    ) {

        System.out.println(
                "Doctor-Service is unavailable: "
                        + throwable.getMessage()
        );

        return "Doctor-Service đang tạm thời không khả dụng. "
                + "Vui lòng thử lại sau.";
    }

    @CircuitBreaker(
            name = "doctorServiceCB",
            fallbackMethod = "getDoctorFallback"
    )
    public String getDoctor(Long doctorId) {

        return doctorClient.getDoctor(doctorId);
    }

    public ApiResponseError getDoctorFallback(Exception e) {

        return new ApiResponseError(
                503,
                "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây"
        );
    }
}