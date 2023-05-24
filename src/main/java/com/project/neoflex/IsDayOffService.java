package com.project.neoflex;

import com.project.neoflex.IsDayOffClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IsDayOffService {

    private final IsDayOffClient isDayOffClient;

    public ResponseEntity<String> isDayOff(LocalDate startDate) {
        return isDayOffClient.isDayOff(startDate);
    }
}