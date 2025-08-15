package kz.med.nuraimedback.service;

import kz.med.nuraimedback.model.dto.DirectionRequestDto;
import kz.med.nuraimedback.model.dto.DirectionResponseDto;
import kz.med.nuraimedback.model.dto.DirectionNamesResponseDto;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface DirectionService {

    DirectionResponseDto createDirection(DirectionRequestDto dto) throws IOException;

    DirectionResponseDto getDirection(Long id);

    Page<DirectionResponseDto> getAllDirections(int page, int size);

    DirectionResponseDto updateDirection(Long id, DirectionRequestDto dto) throws IOException;

    void deleteDirection(Long id);
    
    List<DirectionNamesResponseDto> getAllNamesForNavbar(String languageCode);
}
