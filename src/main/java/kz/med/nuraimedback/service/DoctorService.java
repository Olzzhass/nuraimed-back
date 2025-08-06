package kz.med.nuraimedback.service;

import kz.med.nuraimedback.model.dto.DoctorRequestDto;
import kz.med.nuraimedback.model.dto.DoctorResponseDto;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface DoctorService {

    DoctorResponseDto createDoctor(DoctorRequestDto dto) throws IOException;

    DoctorResponseDto getDoctor(Long id);

    Page<DoctorResponseDto> getAllDoctors(int page, int size);

    DoctorResponseDto updateDoctor(Long id, DoctorRequestDto dto) throws IOException;

    void deleteDoctor(Long id);

}
