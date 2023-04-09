package kr.co.hhjpetclinicstudy.owner.model;

import kr.co.hhjpetclinicstudy.service.model.dtos.request.OwnerReqDTO;

public class OwnerCreators {

    public static OwnerReqDTO.CREATE ownerReqDto_create_creators(){

        return OwnerReqDTO.CREATE.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .address("testAddress")
                .city("testCity")
                .telephone("01000000000")
                .build();
    }

    public static OwnerReqDTO.CREATE ownerReqDto_create_creators(String telephone){

        return OwnerReqDTO.CREATE.builder()
                .firstName("testFirstName")
                .lastName("testLastName")
                .address("testAddress")
                .city("testCity")
                .telephone("01000000000")
                .build();
    }

    public static OwnerReqDTO.UPDATE ownerReqDto_update_creators(String telephone){

        return OwnerReqDTO.UPDATE.builder()
                .ownerId(1L)
                .firstName("updateFirstName")
                .lastName("updateLastName")
                .address("updateAddress")
                .city("updateCity")
                .telephone(telephone)
                .build();
    }
}
