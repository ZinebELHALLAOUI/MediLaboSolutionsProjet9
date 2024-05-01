package io.zineb.repository;

import io.zineb.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAllByFirstNameOrLastName(String firstname, String lastname);

    Optional<Patient> findById(long id);
}