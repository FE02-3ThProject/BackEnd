package com.github.gather.service;

import com.github.gather.entity.Location;
import com.github.gather.repositroy.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // 위치 ID로 위치 정보 조회
    public Location getLocationById(Long locationId) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        return optionalLocation.orElse(null);
    }

    // 모든 위치 정보 조회
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // 위치 정보 저장
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    // 위치 ID로 위치 정보 삭제
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
