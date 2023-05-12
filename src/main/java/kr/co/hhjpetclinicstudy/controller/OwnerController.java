package kr.co.hhjpetclinicstudy.controller;

import jakarta.validation.Valid;
import kr.co.hhjpetclinicstudy.infrastructure.error.exception.DuplicatedException;
import kr.co.hhjpetclinicstudy.infrastructure.error.exception.NotFoundException;
import kr.co.hhjpetclinicstudy.infrastructure.error.model.ResponseFormat;
import kr.co.hhjpetclinicstudy.infrastructure.error.model.ResponseStatus;
import kr.co.hhjpetclinicstudy.service.service.OwnerService;
import kr.co.hhjpetclinicstudy.service.model.dtos.request.OwnerReqDTO;
import kr.co.hhjpetclinicstudy.service.model.dtos.response.OwnerResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseFormat<Void> createOwner(@RequestBody @Validated OwnerReqDTO.CREATE create) {

        try {
            ownerService.createOwner(create);
            return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
        } catch (DuplicatedException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_TELEPHONE_DUPLICATED);
        } catch (RuntimeException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseFormat<List<OwnerResDTO.READ>> getOwnersByIds(@RequestBody OwnerReqDTO.CONDITION condition) {

        try {
            return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, ownerService.getOwnersByIds(condition));
        } catch (NotFoundException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_NOT_FOUND);
        } catch (RuntimeException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_BAD_REQUEST);
        }
    }

    @PutMapping("/{owner_id}")
    public ResponseFormat<Void> updateOwnerById(@PathVariable(name = "owner_id") Long ownerId,
                                                @RequestBody @Valid OwnerReqDTO.UPDATE update) {

        try {
            ownerService.updateOwnerById(ownerId, update);
            return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
        } catch (DuplicatedException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_TELEPHONE_DUPLICATED);
        } catch (NotFoundException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_NOT_FOUND);
        } catch (RuntimeException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseFormat<Void> deleteOwnersByIds(@RequestBody OwnerReqDTO.CONDITION condition) {

        try {
            ownerService.deleteOwnersByIds(condition);
            return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
        } catch (NotFoundException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_NOT_FOUND);
        } catch (RuntimeException e) {
            return ResponseFormat.error(ResponseStatus.FAIL_BAD_REQUEST);
        }
    }
}
