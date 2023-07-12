package com.sda.carrental.services;

import com.sda.carrental.entities.Status;
import com.sda.carrental.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAllStatus() {

        return statusRepository.findAll();
    }
}
