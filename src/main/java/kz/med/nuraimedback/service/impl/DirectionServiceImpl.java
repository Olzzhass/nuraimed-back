package kz.med.nuraimedback.service.impl;

import kz.med.nuraimedback.exception.DirectionNotFoundException;
import kz.med.nuraimedback.mapper.DirectionMapper;
import kz.med.nuraimedback.model.Direction;
import kz.med.nuraimedback.model.DirectionTranslation;
import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.model.dto.DirectionTranslationDto;
import kz.med.nuraimedback.repository.DirectionRepository;
import kz.med.nuraimedback.repository.DirectionTranslationRepository;
import kz.med.nuraimedback.service.DirectionService;
import kz.med.nuraimedback.service.DirectionService;
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
public class DirectionServiceImpl implements DirectionService {

    private final DirectionRepository DirectionRepository;
    private final DirectionTranslationRepository translationRepository;
    private final DirectionMapper mapper;

    @Override
    @Transactional
    public DirectionResponseDto createDirection(DirectionRequestDto dto) throws IOException {
        Direction Direction = mapper.toEntity(dto);
        if (dto.getDirectionImage() != null) {
            Direction.setDirectionImage(dto.getDirectionImage().getBytes());
        }

        Direction savedDirection = DirectionRepository.save(Direction);

        List<DirectionTranslation> translations = dto.getTranslations().stream()
                .map(t -> new DirectionTranslation(null, savedDirection, t.getLanguageCode(), t.getTitle(), t.getDescription(), t.getOfferDetails()))
                .toList();

        translationRepository.saveAll(translations);
        savedDirection.setTranslations(translations);

        return mapper.toDto(savedDirection);
    }

    @Override
    public DirectionResponseDto getDirection(Long id) {
        Direction Direction = DirectionRepository.findById(id)
                .orElseThrow(() -> new DirectionNotFoundException("Direction not found"));
        return mapper.toDto(Direction);
    }

    @Override
    public Page<DirectionResponseDto> getAllDirections(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Direction> DirectionPage = DirectionRepository.findAll(pageable);

        return DirectionPage.map(mapper::toDto);
    }

    @Override
    @Transactional
    public DirectionResponseDto updateDirection(Long id, DirectionRequestDto dto) throws IOException {
        Direction Direction = DirectionRepository.findById(id)
                .orElseThrow(() -> new DirectionNotFoundException("Direction not found"));

        if (dto.getDirectionImage() != null && !dto.getDirectionImage().isEmpty()) {
            Direction.setDirectionImage(dto.getDirectionImage().getBytes());
        }

        Map<String, DirectionTranslation> existingTranslations = Direction.getTranslations().stream()
                .collect(Collectors.toMap(DirectionTranslation::getLanguageCode, t -> t));

        for (DirectionTranslationDto translationDto : dto.getTranslations()) {
            DirectionTranslation translation = existingTranslations.get(translationDto.getLanguageCode());

            if (translation != null) {
                // Обновляем существующий
                translation.setTitle(translationDto.getTitle());
                translation.setDescription(translationDto.getDescription());
                translation.setOfferDetails(translationDto.getOfferDetails());
            } else {
                translation = new DirectionTranslation();
                translation.setDirection(Direction);
                translation.setLanguageCode(translationDto.getLanguageCode());
                translation.setTitle(translationDto.getTitle());
                translation.setDescription(translationDto.getDescription());
                translation.setOfferDetails(translationDto.getOfferDetails());
                Direction.getTranslations().add(translation);
            }
        }

        DirectionRepository.save(Direction);
        return mapper.toDto(Direction);
    }


    @Override
    @Transactional
    public void deleteDirection(Long id) {
        if (!DirectionRepository.existsById(id)) {
            throw new DirectionNotFoundException("Direction not found");
        }
        DirectionRepository.deleteById(id);
    }
}
