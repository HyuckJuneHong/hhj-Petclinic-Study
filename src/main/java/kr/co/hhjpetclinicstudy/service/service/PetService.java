package kr.co.hhjpetclinicstudy.service.service;

import kr.co.hhjpetclinicstudy.infrastructure.error.exception.NotFoundException;
import kr.co.hhjpetclinicstudy.infrastructure.error.model.ResponseStatus;
import kr.co.hhjpetclinicstudy.persistence.entity.Owner;
import kr.co.hhjpetclinicstudy.persistence.entity.Pet;
import kr.co.hhjpetclinicstudy.persistence.repository.OwnerRepository;
import kr.co.hhjpetclinicstudy.persistence.repository.PetRepository;
import kr.co.hhjpetclinicstudy.persistence.repository.search.PetSearchRepository;
import kr.co.hhjpetclinicstudy.service.model.dtos.request.PetReqDTO;
import kr.co.hhjpetclinicstudy.service.model.dtos.response.PetResDTO;
import kr.co.hhjpetclinicstudy.service.model.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {

    private final PetRepository petRepository;

    private final OwnerRepository ownerRepository;

    private final PetSearchRepository petSearchRepository;

    private final PetMapper petMapper;

    /**
     * pet create service
     */
    @Transactional
    public void createPet(Long ownerId,
                          PetReqDTO.CREATE create) {

        final Owner owner = ownerRepository
                .findById(ownerId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND));

        final Pet pet = petMapper.toPetEntity(create, owner);

        petRepository.save(pet);
    }

    public List<PetResDTO.READ_DETAIL> getPetsByIds(PetReqDTO.CONDITION condition) {

        final List<Pet> pets = petSearchRepository.search(condition);

        return pets.stream()
                .map(petMapper::toReadDetailDto)
                .collect(Collectors.toList());
    }

    public List<PetResDTO.READ> getPetsByOwner(PetReqDTO.CONDITION condition) {

        return petSearchRepository
                .search(condition)
                .stream()
                .map(petMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * pet update service
     */
    @Transactional
    public void updatePet(Long petId,
                          PetReqDTO.UPDATE update) {

        Pet pet = petRepository
                .findById(petId)
                .orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_NOT_FOUND));

        pet.updatePetInfo(update);
    }

    /**
     * pet delete service
     */
    @Transactional
    public void deletePetsByIds(PetReqDTO.CONDITION condition) {

        final List<Pet> pets = petSearchRepository.search(condition);

        petRepository.deleteAll(pets);
    }
}
