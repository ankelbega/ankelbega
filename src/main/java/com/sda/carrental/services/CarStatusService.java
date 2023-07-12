package com.sda.carrental.services;

import com.sda.carrental.entities.Car;
import com.sda.carrental.entities.CarStatus;
import com.sda.carrental.entities.Status;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.CarStatusRequestParams;
import com.sda.carrental.repositories.CarRepository;
import com.sda.carrental.repositories.CarStatusRepository;
import com.sda.carrental.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarStatusService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarStatusRepository carStatusRepository;

    @Autowired
    private StatusRepository statusRepository;

    public List<CarStatus> getCarStatuses(final Long carId) {
        return carStatusRepository.findByCarId(carId);
    }

    public CarStatus addCarStatus(final CarStatusRequestParams carStatusRequestParams, final Long carId) {
        Optional<CarStatus> carStatusOptional = carStatusRepository.findByCarIdAndDate(carId, carStatusRequestParams.getDate());
        if (carStatusOptional.isPresent()) {
            Status status = statusRepository.findById(carStatusRequestParams.getStatusId()).orElseThrow(() ->
                    new NotFoundException(("Status not found with such id!")));
            CarStatus existingCarStatus = carStatusOptional.get();
            existingCarStatus.setStatus(status);
            return carStatusRepository.save(existingCarStatus);
        } else {
            CarStatus carStatus = new CarStatus();
            carStatus.setDate(carStatusRequestParams.getDate());
            Status status = statusRepository.findById(carStatusRequestParams.getStatusId()).orElseThrow(() -> new NotFoundException("Status not found with such id!"));
            carStatus.setStatus(status);
            Car car = carRepository.findById(carId).orElseThrow(() -> new NotFoundException("Car not found with such id!"));
            carStatus.setCar(car);
            carStatusRepository.save(carStatus);
            return carStatus;
        }
    }

    public CarStatus updateCarStatus(final Long carStatusId, final Long statusId) {

        CarStatus carStatus = carStatusRepository.findById(carStatusId).orElseThrow(() -> new NotFoundException("Status not found with such id!"));
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Status not found with such id!"));
        carStatus.setStatus(status);
        carStatusRepository.save(carStatus);
        return carStatus;

    }

    public void deleteCarStatus(final Long carStatusId) {
        if (carStatusRepository.existsById(carStatusId)) {
            carStatusRepository.deleteById(carStatusId);
        } else {
            throw new NotFoundException("Car status not found with such id!");
        }
    }


}
