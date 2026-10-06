package org.example.appointmentservice.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "doctor-service",
        url = "http://localhost:8082"
)
public interface DoctorClient {

    @GetMapping("/api/doctors/{id}/schedule")
    String getDoctorSchedule(@PathVariable Long id);

    @GetMapping("/api/doctors/{id}")
    String getDoctor(@PathVariable("id") Long doctorId);
}