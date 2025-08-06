package kz.med.nuraimedback.service.impl;

import kz.med.nuraimedback.exception.DoctorNotFoundException;
import kz.med.nuraimedback.mapper.DoctorMapper;
import kz.med.nuraimedback.model.Doctor;
import kz.med.nuraimedback.model.DoctorTranslation;
import kz.med.nuraimedback.model.dto.DoctorRequestDto;
import kz.med.nuraimedback.model.dto.DoctorResponseDto;
import kz.med.nuraimedback.model.dto.DoctorTranslationDto;
import kz.med.nuraimedback.repository.DoctorRepository;
import kz.med.nuraimedback.repository.DoctorTranslationRepository;
import kz.med.nuraimedback.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorTranslationRepository translationRepository;
    private final DoctorMapper mapper;

    @Override
    @Transactional
    public DoctorResponseDto createDoctor(DoctorRequestDto dto) throws IOException {
        Doctor doctor = mapper.toEntity(dto);
        if (dto.getProfileImage() != null) {
            doctor.setProfileImage(dto.getProfileImage().getBytes());
        }

        Doctor savedDoctor = doctorRepository.save(doctor);

        List<DoctorTranslation> translations = dto.getTranslations().stream()
                .map(t -> new DoctorTranslation(null, savedDoctor, t.getLanguageCode(), t.getDescription(), t.getEducation(), t.getExperience(), t.getSpecialization()))
                .toList();

        translationRepository.saveAll(translations);
        savedDoctor.setTranslations(translations);

        return mapper.toDto(savedDoctor);
    }

    @Override
    public DoctorResponseDto getDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        return mapper.toDto(doctor);
    }

    @Override
    public Page<DoctorResponseDto> getAllDoctors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctorPage = doctorRepository.findAll(pageable);

        return doctorPage.map(mapper::toDto);
    }

    @Override
    @Transactional
    public DoctorResponseDto updateDoctor(Long id, DoctorRequestDto dto) throws IOException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Доктор не найден"));

        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setMiddleName(dto.getMiddleName());

        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            doctor.setProfileImage(dto.getProfileImage().getBytes());
        }

        Map<String, DoctorTranslation> existingTranslations = doctor.getTranslations().stream()
                .collect(Collectors.toMap(DoctorTranslation::getLanguageCode, t -> t));

        for (DoctorTranslationDto translationDto : dto.getTranslations()) {
            DoctorTranslation translation = existingTranslations.get(translationDto.getLanguageCode());

            if (translation != null) {
                // Обновляем существующий
                translation.setDescription(translationDto.getDescription());
                translation.setEducation(translationDto.getEducation());
                translation.setExperience(translationDto.getExperience());
                translation.setSpecialization(translationDto.getSpecialization());
            } else {
                translation = new DoctorTranslation();
                translation.setDoctor(doctor);
                translation.setLanguageCode(translationDto.getLanguageCode());
                translation.setDescription(translationDto.getDescription());
                translation.setEducation(translationDto.getEducation());
                translation.setExperience(translationDto.getExperience());
                translation.setSpecialization(translationDto.getSpecialization());
                doctor.getTranslations().add(translation);
            }
        }

        doctorRepository.save(doctor);
        return mapper.toDto(doctor);
    }


    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Доктор не найден");
        }
        doctorRepository.deleteById(id);
    }
}
